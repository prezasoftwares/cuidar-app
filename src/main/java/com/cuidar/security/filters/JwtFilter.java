package com.cuidar.security.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cuidar.service.CustomUserDetailsService;
import com.cuidar.util.JwtUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;

@Service
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private CustomUserDetailsService customUserSvc;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            var authHeader = request.getHeader("Authorization");
            if (authHeader != null && authHeader.startsWith("JWT")) {
                var token = authHeader.replaceFirst("JWT", "");
                var username = jwtUtils.extractUsername(token);

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    var user = customUserSvc.loadUserByUsername(username);
                    if (Boolean.TRUE.equals(jwtUtils.validateToken(token, user))) {
                        var userPassToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                        userPassToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(userPassToken);
                    }
                }
            }

            filterChain.doFilter(request, response);

        } catch (ExpiredJwtException ex) {
            //request.setAttribute("expired", ex.getMessage());
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Expired JWT token");
        }
        catch (SignatureException ex){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid JWT signature");
        }
    }
}
