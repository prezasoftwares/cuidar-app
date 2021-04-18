package com.cuidar.model;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.cuidar.constraints.MainFamilyMemberHousingTypeOtherConstraint;
import com.cuidar.model.enums.FamilyMemberCivilStatus;
import com.cuidar.model.enums.FamilyMemberGender;
import com.cuidar.model.enums.FamilyMemberHousingType;
import com.cuidar.model.enums.FamilyMemberNoYesFlag;
import com.cuidar.model.enums.FamilyMemberSchooling;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@MainFamilyMemberHousingTypeOtherConstraint
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "main_familymember")
public class MainFamilyMember extends FamilyMember {

    private static final long serialVersionUID = 1L;

    // mandatory
    @NotNull(message = "Número de documento deve ser preenchido")
    private String documentId;
    @NotNull(message = "Código postal deve ser preenchido")
    private String addressPostalCode;
    @NotNull(message = "Nome do logradouro deve ser preenchido")
    private String addressStreetName;
    @NotNull(message = "Número do logradouro deve ser preenchido")
    private String addressStreetNumber;
    @NotNull(message = "Cidade deve ser preenchido")
    private String addressCity;
    @NotNull(message = "Estado deve ser preenchido")
    private String addressState;
    @NotNull(message = "Estado civil deve ser preenchido")
    private FamilyMemberCivilStatus civilStatus;
    @NotNull(message = "Escolaridade deve ser preenchido")
    private FamilyMemberSchooling schooling;

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

    // Dependents
    @OneToMany(mappedBy = "mainFamilyMember", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<DependentFamilyMember> dependents = new HashSet<>();

    @JsonIgnore
    public Set<DependentFamilyMember> getDependents() {
        return dependents;
    }

    public MainFamilyMember(String name, Date birthDate, FamilyMemberGender gender, String documentId,
            FamilyMemberCivilStatus civilStatus, String contactEmail) {
        super(name, birthDate, gender);

        this.documentId = documentId;
        this.civilStatus = civilStatus;
        this.contactEmail = contactEmail;
    }

}
