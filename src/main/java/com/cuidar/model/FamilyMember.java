package com.cuidar.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import com.cuidar.model.enums.FamilyMemberGender;

@Entity
@Table(name="familymember")
@Inheritance(strategy = InheritanceType.JOINED)
public class FamilyMember extends BaseModel{

    private static final long serialVersionUID = 1L;

    @Column(nullable = false)
    private String name;
    
    private Date birthDate;
    private FamilyMemberGender gender;

    public FamilyMember()
    {    
    }

    public FamilyMemberGender getGender() {
        return gender;
    }

    public void setGender(FamilyMemberGender gender) {
        this.gender = gender;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public FamilyMember(Long id) {

        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FamilyMember(String name, Date birthDate, FamilyMemberGender gender) {
        this.name = name;
        this.birthDate = birthDate;
        this.gender = gender;
    }
}
