package com.cuidar.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.cuidar.model.enums.FamilyMemberGender;
import com.cuidar.model.enums.FamilyMemberLinkType;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "dependent_familymember")
public class DependentFamilyMember extends FamilyMember {

    private static final long serialVersionUID = 1L;

    @Column(nullable = false)
    private FamilyMemberLinkType linkTypeToMainMember;

    @ManyToOne
    @JoinColumn(name = "mainFamilyMember_id")
    private MainFamilyMember mainFamilyMember;

    public DependentFamilyMember(){

    }

    public DependentFamilyMember(String name, Date birthDate, FamilyMemberGender gender, FamilyMemberLinkType linkTypeToMainMember, MainFamilyMember mainFamilyMember) {
        super(name, birthDate, gender);
        this.linkTypeToMainMember = linkTypeToMainMember;
        this.mainFamilyMember = mainFamilyMember;
    }

    @JsonIgnore
    public MainFamilyMember getMainFamilyMember() {
        return mainFamilyMember;
    }

    public Long getMainFamilyMember_Id(){
        return mainFamilyMember.getId();
    }

    public void setMainFamilyMember(MainFamilyMember mainFamilymember){
        this.mainFamilyMember = mainFamilymember;
    }

    public FamilyMemberLinkType getLinkTypeToMainMember() {
        return linkTypeToMainMember;
    }

    public void setLinkTypeToMainMember(FamilyMemberLinkType linkTypeToMainMember) {
        this.linkTypeToMainMember = linkTypeToMainMember;
    }
}