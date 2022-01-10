package com.aviobrief.springserver.utils.mapper;

import java.util.List;

public interface Mapper {

    <E, M> List<M> toModel(List<E> entityList, Class<M> destinationType);

}
