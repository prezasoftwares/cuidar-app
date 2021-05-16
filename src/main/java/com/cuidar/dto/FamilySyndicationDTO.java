package com.cuidar.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@NotNull
public class FamilySyndicationDTO {

    @NotEmpty
    @NotNull
    private String syndicationNotes;
}
