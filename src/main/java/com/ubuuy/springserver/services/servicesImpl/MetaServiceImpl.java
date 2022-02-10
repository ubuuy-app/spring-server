package com.ubuuy.springserver.services.servicesImpl;

import com.ubuuy.springserver.models.entities.MetaEntity;
import com.ubuuy.springserver.models.enums.MetaActionEnum;
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
    public MetaEntity create() {
        return createNewMetaEntity().setAction(MetaActionEnum.CREATE);
    }

    @Override
    public MetaEntity update() {
        return createNewMetaEntity().setAction(MetaActionEnum.UPDATE);
    }

    @Override
    public MetaEntity delete() {
        return createNewMetaEntity().setAction(MetaActionEnum.DELETE);
    }

    @Override
    public MetaEntity actionNotApplicable() {
        return createNewMetaEntity().setAction(MetaActionEnum.NOT_APPLICABLE);
    }


    private MetaEntity createNewMetaEntity(){
        return new MetaEntity()
                .setDateTime(ZonedDateTime.now())
                .setSystemUser(this.authService.getPrincipalUsername());
    }
}
