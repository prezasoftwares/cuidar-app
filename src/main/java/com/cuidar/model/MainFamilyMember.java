package com.cuidar.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.cuidar.model.enums.FamilyMemberCivilStatus;
import com.cuidar.model.enums.FamilyMemberGender;
import com.cuidar.model.enums.FamilyMemberGeneralStatus;
import com.cuidar.model.enums.FamilyMemberHousingType;
import com.cuidar.model.enums.FamilyMemberNoYesFlag;
import com.cuidar.model.enums.FamilyMemberSchooling;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "main_familymember")
public class MainFamilyMember extends FamilyMember {

    private static final long serialVersionUID = 1L;

    // mandatory
    @Column(nullable = false, unique = true)
    private String documentId;
    @Column(nullable = false)
    private String addressPostalCode;
    @Column(nullable = false)
    private String addressStreetName;
    @Column(nullable = false)
    private String addressStreetNumber;
    @Column(nullable = false)
    private String addressCity;
    @Column(nullable = false)
    private String addressState;
    @Column(nullable = false)
    private FamilyMemberCivilStatus civilStatus;
    @Column(nullable = false)
    private FamilyMemberSchooling schooling;
    
    @Column(nullable = false)
    private FamilyMemberGeneralStatus generalStatus = FamilyMemberGeneralStatus.PendingApproval;

    // not-mandatory
    private String addressStreetComplement;
    private String contactPhoneNumber;
    private String contactEmail;
    private FamilyMemberHousingType housingType;
    private String housingTypeNotes;
    private String economicSituationNotes;
    private String religionNotes;
    private FamilyMemberNoYesFlag baptizedChildren;
    private String socialAssistenceNeedsNotes;

    private Date assistenceDueDate;

    // Dependents
    @OneToMany(mappedBy = "mainFamilyMember", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<DependentFamilyMember> dependents = new HashSet<>();

    @JsonIgnore
    public Set<DependentFamilyMember> getDependents() {
        return dependents;
    }

    // Status update history
    @OneToMany(mappedBy = "mainFamilyMember", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private Set<FamilyStatusUpdateRecord> statusUpdateRecords = new HashSet<>();

    @JsonIgnore
    public Set<FamilyStatusUpdateRecord> getStatusUpdateRecords() {
        return statusUpdateRecords;
    }

    public MainFamilyMember(String name, Date birthDate, FamilyMemberGender gender, String documentId,
            FamilyMemberCivilStatus civilStatus, String contactEmail) {
        super(name, birthDate, gender);

        this.documentId = documentId;
        this.civilStatus = civilStatus;
        this.contactEmail = contactEmail;
    }

}
