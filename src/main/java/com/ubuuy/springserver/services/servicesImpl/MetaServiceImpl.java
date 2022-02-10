package com.ubuuy.springserver.services.servicesImpl;

import com.ubuuy.springserver.models.entities.MetaEntity;
import com.ubuuy.springserver.models.enums.MetaActionEnum;
import com.ubuuy.springserver.services.AuthService;
import com.ubuuy.springserver.services.MetaService;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;

@Service
public class MetaServiceImpl implements MetaService {

    private final AuthService authService;

    public MetaServiceImpl(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public MetaEntity createMetaData(MetaActionEnum action) {
        return new MetaEntity()
                .setDateTime(ZonedDateTime.now())
                .setSystemUser(this.authService.getPrincipalUsername())
                .setAction(action);
    }
}
