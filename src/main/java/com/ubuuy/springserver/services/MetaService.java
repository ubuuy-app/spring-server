package com.ubuuy.springserver.services;

import com.ubuuy.springserver.models.entities.MetaEntity;

public interface MetaService {

    MetaEntity create();

    MetaEntity update();

    MetaEntity delete();

    MetaEntity actionNotApplicable();

}
