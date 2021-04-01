package com.cuidar.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.cuidar.model.DependentFamilyMember;

import org.springframework.stereotype.Repository;

@Repository
public class FamilyMemberRepositoryCustomImpl implements FamilyMemberRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<DependentFamilyMember> findDependentFamlilyMembers(Long mainFamilyMemberId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<DependentFamilyMember> cq = cb.createQuery(DependentFamilyMember.class);

        Root<DependentFamilyMember> dependentMember = cq.from(DependentFamilyMember.class);

        Predicate mainMemberId = cb.equal(dependentMember.get("mainFamilyMember"), mainFamilyMemberId);
        
        cq.where(mainMemberId);

        return entityManager
            .createQuery(cq)
            .getResultList();
    }
    
}
