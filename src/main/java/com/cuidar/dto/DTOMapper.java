package com.cuidar.dto;

import org.modelmapper.ModelMapper;

public class DTOMapper<A, B> {

    private final Class<A> aType;
    private final Class<B> bType;

    public DTOMapper(Class<A> aType, Class<B> bType) {
        super();
        this.aType = aType;
        this.bType = bType;
    }

    public B convertToEntity(ModelMapper modelMapper) {
        B entity = modelMapper.map(this, this.bType);

        return entity;
    }

    public A convertToDto(ModelMapper modelMapper, B entity) {
        A dto = modelMapper.map(entity, this.aType);

        return dto;
    }
}
