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
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "main_familymember")
public class MainFamilyMember extends FamilyMember{

    private static final long serialVersionUID = 1L;

    @Column(nullable = false)
    private String documentId;

    //Address fields
    private String addressPostalCode;
    private String addressStreetName;
    private String addressStreetNumber;
    private String addressStreetComplement;
    private String addressCity;
    private String addressState;

    // Civil status info
    private FamilyMemberCivilStatus civilStatus;

    //Contact info
    private String contactPhoneNumber;
    private String contactEmail;

    //Dependents
    @OneToMany(mappedBy = "mainFamilyMember", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<DependentFamilyMember> dependents = new HashSet<>();

    public MainFamilyMember(){

    }

    @JsonIgnore
    public Set<DependentFamilyMember> getDependents(){
        return dependents;
    }

    public String getAddressPostalCode() {
        return addressPostalCode;
    }
    public String getContactEmail() {
        return contactEmail;
    }
    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }
    public String getContactPhoneNumber() {
        return contactPhoneNumber;
    }
    public void setContactPhoneNumber(String contactPhoneNumber) {
        this.contactPhoneNumber = contactPhoneNumber;
    }
    public FamilyMemberCivilStatus getCivilStatus() {
        return civilStatus;
    }
    public void setCivilStatus(FamilyMemberCivilStatus civilStatus) {
        this.civilStatus = civilStatus;
    }
    public String getAddressState() {
        return addressState;
    }
    public void setAddressState(String addressState) {
        this.addressState = addressState;
    }
    public String getAddressCity() {
        return addressCity;
    }
    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }
    public String getAddressStreetComplement() {
        return addressStreetComplement;
    }
    public void setAddressStreetComplement(String addressStreetComplement) {
        this.addressStreetComplement = addressStreetComplement;
    }
    public String getAddressStreetNumber() {
        return addressStreetNumber;
    }
    public void setAddressStreetNumber(String addressStreetNumber) {
        this.addressStreetNumber = addressStreetNumber;
    }
    public String getAddressStreetName() {
        return addressStreetName;
    }
    public void setAddressStreetName(String addressStreetName) {
        this.addressStreetName = addressStreetName;
    }
    public void setAddressPostalCode(String addressPostalCode) {
        this.addressPostalCode = addressPostalCode;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }
    
    public MainFamilyMember(String name, Date birthDate, FamilyMemberGender gender, String documentId,
            FamilyMemberCivilStatus civilStatus, String contactEmail) {
        super(name, birthDate, gender);
        this.documentId = documentId;
        this.civilStatus = civilStatus;
        this.contactEmail = contactEmail;
    }
    
}
