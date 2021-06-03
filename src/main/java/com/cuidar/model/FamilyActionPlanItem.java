package com.cuidar.model;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.cuidar.model.enums.FamilyMemberNoYesFlag;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "family_actionPlanItem")
public class FamilyActionPlanItem extends BaseModel {

    private static final long serialVersionUID = 1L;

    @Column(nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "FK_mainFamilyMemberId")
    private MainFamilyMember mainFamilyMember;

    @ManyToOne
    @JoinColumn(name = "FK_referencedFamilyMemberId")
    private FamilyMember referencedFamilyMember;    

    private FamilyMemberNoYesFlag isAssistentTask;
    private Date dueDate;
    private FamilyMemberNoYesFlag done;
    
    @Column(name ="createdDateTime", columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private Date systemRegistrationDate;

    @JsonIgnore
    public MainFamilyMember getMainFamilyMember() {
        return mainFamilyMember;
    }

    public UUID getMainFamilyMemberId() {
        return mainFamilyMember.getId();
    }    

    @JsonIgnore
    public FamilyMember getReferencedFamilyMember() {
        return referencedFamilyMember;
    }

    public UUID getReferencedFamilyMember_Id() {
        if (referencedFamilyMember != null){
            return referencedFamilyMember.getId();
        }

        return null;
    } 

    public String getReferencedFamilyMember_Name() {
        if (referencedFamilyMember != null){
            return referencedFamilyMember.getFullName();
        }

        return "";
    }
}
