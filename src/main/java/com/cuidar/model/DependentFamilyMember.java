package com.cuidar.model;

import java.sql.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.cuidar.model.enums.FamilyMemberGender;
import com.cuidar.model.enums.FamilyMemberLinkType;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(name = "dependent_familymember")
public class DependentFamilyMember extends FamilyMember {

    private static final long serialVersionUID = 1L;

    private String documentId;

    @Column(nullable = false)
    private FamilyMemberLinkType linkTypeToMainMember;

    @ManyToOne
    @JoinColumn(name = "FK_mainFamilyMemberId")
    private MainFamilyMember mainFamilyMember;

    public DependentFamilyMember() {

    }

    public DependentFamilyMember(String name, Date birthDate, FamilyMemberGender gender,
            FamilyMemberLinkType linkTypeToMainMember, MainFamilyMember mainFamilyMember) {
        super(name, birthDate, gender);
        this.linkTypeToMainMember = linkTypeToMainMember;
        this.mainFamilyMember = mainFamilyMember;
    }

    @JsonIgnore
    public MainFamilyMember getMainFamilyMember() {
        return mainFamilyMember;
    }

    public UUID getMainFamilyMember_Id() {
        return mainFamilyMember.getId();
    }
}