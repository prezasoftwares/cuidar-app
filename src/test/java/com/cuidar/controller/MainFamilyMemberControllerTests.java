package com.cuidar.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Calendar;

import com.cuidar.model.MainFamilyMember;
import com.cuidar.model.enums.FamilyMemberCivilStatus;
import com.cuidar.model.enums.FamilyMemberGender;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
public class MainFamilyMemberControllerTests {
    
    @Autowired
	private MockMvc mockMvc;
    
    @Test
    void getAllMainFamilyMembers() throws Exception {
        RequestBuilder request = get("/mainfamilymembers");
		
		MvcResult result = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
		
	    assertNotNull(result);     
    }

    @Test
    void getSpecificMainFamilyMember() throws Exception{
        RequestBuilder request = get("/mainfamilymembers/1");

        MvcResult result = mockMvc.perform(request).andExpect(status().isOk()).andReturn();

        assertNotNull(result);
    }


    @Test
    void createMainFamilyMember() throws Exception{
        var mainFamilyMember = new MainFamilyMember("Principal 1", Calendar.getInstance().getTime(), FamilyMemberGender.Male, "99-999999", FamilyMemberCivilStatus.Single, "single@test123.com");

        mockMvc.perform( MockMvcRequestBuilders
                .post("/mainfamilymembers")
                .content(asJsonString(mainFamilyMember))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

    @Test
    public void updateMainFamilyMember_Name() throws Exception {
        RequestBuilder requestGetMainFamilyMember = get("/mainfamilymembers/1");

        MvcResult getResult = mockMvc.perform(requestGetMainFamilyMember).andExpect(status().isOk()).andReturn();

        String json = getResult.getResponse().getContentAsString();
        MainFamilyMember foundMainFamilyMember = new ObjectMapper().readValue(json, MainFamilyMember.class);

        foundMainFamilyMember.setName("Novo nome");

        mockMvc.perform( MockMvcRequestBuilders
                .put("/mainfamilymembers/{id}", 1)
                .content(asJsonString(foundMainFamilyMember))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Novo nome"));
}


    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}