package com.cuidar.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.cuidar.model.MainFamilyMember;
import com.cuidar.model.enums.FamilyMemberHousingType;

public class MainFamilyMemberCreatorValidator
    implements ConstraintValidator<MainFamilyMemberHousingTypeOtherConstraint, MainFamilyMember> {

  @Override
  public void initialize(MainFamilyMemberHousingTypeOtherConstraint mainFamilyMember) {
  }

  @Override
  public boolean isValid(MainFamilyMember mainFamilyMember, ConstraintValidatorContext cxt) {
    if (mainFamilyMember.getHousingType() != null
        && mainFamilyMember.getHousingType().equals(FamilyMemberHousingType.Other)
        && (mainFamilyMember.getHousingTypeNotes() == null || mainFamilyMember.getHousingTypeNotes().isEmpty())) {

      return false;
    }

    return true;
  }

}