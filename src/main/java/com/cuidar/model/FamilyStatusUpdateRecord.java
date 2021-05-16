package com.cuidar.model;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.cuidar.model.enums.FamilyMemberGeneralStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "family_statusUpdateRecord")
public class FamilyStatusUpdateRecord extends BaseModel{
    
    private static final long serialVersionUID = 2L;

    @Column(nullable = false)
    private FamilyMemberGeneralStatus currentStatus;

    @Column(nullable = false)
    private String statusUpdateDescription;

    @Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable = false, updatable = false)
    private Date updateDateTime;

    @ManyToOne
    @JoinColumn(name = "FK_mainFamilyMemberId")
    private MainFamilyMember mainFamilyMember;

    public FamilyStatusUpdateRecord(MainFamilyMember mainFamilyMember, String statusUpdateDescription, FamilyMemberGeneralStatus currentStatus) {
        this.statusUpdateDescription = statusUpdateDescription;
        this.mainFamilyMember = mainFamilyMember;
        this.currentStatus = currentStatus;
    }

    @JsonIgnore
    public MainFamilyMember getMainFamilyMember() {
        return mainFamilyMember;
    }

    public UUID getMainFamilyMember_Id() {
        return mainFamilyMember.getId();
    }
}
