package com.ubuuy.springserver.services.servicesImpl;

import com.ubuuy.springserver.models.enums.MetaActionEnum;
import com.ubuuy.springserver.models.meta_data.MetaData;
import com.ubuuy.springserver.services.AuthService;
import com.ubuuy.springserver.services.MetaService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
public class MetaServiceImpl implements MetaService {

    private final AuthService authService;

    public MetaServiceImpl(@Lazy AuthService authService) {
        this.authService = authService;
    }

    @Override
    public MetaData create() {
        return createNewMetaEntity().setAction(MetaActionEnum.CREATE);
    }

    @Override
    public MetaData update() {
        return createNewMetaEntity().setAction(MetaActionEnum.UPDATE);
    }

    @Override
    public MetaData delete() {
        return createNewMetaEntity().setAction(MetaActionEnum.DELETE);
    }

    @Override
    public MetaData actionNotApplicable() {
        return createNewMetaEntity().setAction(MetaActionEnum.NOT_APPLICABLE);
    }


    private MetaData createNewMetaEntity() {
        return new MetaData()
                .setDateTime(ZonedDateTime.now())
                .setSystemUser(this.authService.getPrincipalUsername());
    }
}
