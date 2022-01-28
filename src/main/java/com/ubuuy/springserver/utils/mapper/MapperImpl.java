package com.ubuuy.springserver.utils.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MapperImpl implements Mapper {

    private final ModelMapper modelMapper = new ModelMapper();

    public MapperImpl() {
    }

    @Override
    public <E, M> List<M> toModel(List<E> entityList, Class<M> destinationType) {
        return entityList
                .stream()
                .map(e -> (M) modelMapper.map(e, destinationType))
                .toList();
    }

    @Override
    public <E, M> M toModel(E entity, Class<M> destinationType) {
        return (M) modelMapper.map(entity, destinationType);
    }
}
