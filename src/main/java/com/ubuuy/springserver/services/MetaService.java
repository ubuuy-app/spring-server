package com.ubuuy.springserver.services;

import com.ubuuy.springserver.models.entities.MetaEntity;
import com.ubuuy.springserver.models.enums.MetaActionEnum;

public interface MetaService {

    MetaEntity createMetaData(MetaActionEnum action);
}
