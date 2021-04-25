package com.cuidar.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.cuidar.model.MainFamilyMember;

import org.springframework.stereotype.Repository;

@Repository
public class FamilyMemberRepositoryCustomImpl implements FamilyMemberRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean existFamilyMemberByDocumentId(String documentId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<MainFamilyMember> cq = cb.createQuery(MainFamilyMember.class);
        
        Root<MainFamilyMember> mainMember = cq.from(MainFamilyMember.class);

        Predicate mainMemberDocumentId = cb.equal(mainMember.get("documentId"), documentId);

        cq.where(mainMemberDocumentId);

        var resultList = entityManager.createQuery(cq).getResultList();

        if (resultList.isEmpty()){
            return false;
        }

        return true;
    }

}
