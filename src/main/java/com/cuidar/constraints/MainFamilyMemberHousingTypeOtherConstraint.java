package com.cuidar.constraints;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

@Documented
@Constraint(validatedBy = MainFamilyMemberCreatorValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface MainFamilyMemberHousingTypeOtherConstraint {
    String message() default "Deve preencher complemento para 'Outros' (Tipo de moradia)";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
