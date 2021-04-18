package com.cuidar.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Table;

import com.cuidar.model.enums.FamilyMemberGender;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "familymember")
@Inheritance(strategy = InheritanceType.JOINED)
public class FamilyMember extends BaseModel {

    private static final long serialVersionUID = 1L;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private Date birthDate;

    @Column(nullable = false)
    private FamilyMemberGender gender;

    @Column(nullable = false)
    private String occupation;

    public FamilyMember(String fullName, Date birthDate, FamilyMemberGender gender) {
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.gender = gender;
    }
}
