package com.ubuuy.springserver.services;

import com.ubuuy.springserver.models.meta_data.MetaData;

public interface MetaService {

    MetaData create();

    MetaData update();

    MetaData delete();

    MetaData actionNotApplicable();

}
