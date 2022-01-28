package com.ubuuy.springserver.utils.mapper;

import java.util.List;

public interface Mapper {

    <E, M> List<M> toModel(List<E> entityList, Class<M> destinationType);
    <E, M> M toModel(E entity, Class<M> destinationType);


}
