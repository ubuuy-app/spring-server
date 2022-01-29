package com.ubuuy.springserver.services.servicesImpl;

import com.ubuuy.springserver.config.constants.ExceptionMessages;
import com.ubuuy.springserver.models.entities.RoleEntity;
import com.ubuuy.springserver.models.enums.UserRole;
import com.ubuuy.springserver.repositories.RoleRepository;
import com.ubuuy.springserver.services.RoleService;
import org.springframework.stereotype.Service;

import java.sql.SQLException;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public RoleEntity findExistingOrSaveNew(UserRole userRole) throws SQLException {
        try {
            RoleEntity roleEntity =
                    this.roleRepository
                            .findFirstByRole(userRole)
                            .orElse(new RoleEntity(userRole));

            return this.roleRepository.save(roleEntity);
        } catch (Exception ex) {
            throw new SQLException(ExceptionMessages.ENTITY_DATABASE_SAVE_FAIL);
        }
    }
}
