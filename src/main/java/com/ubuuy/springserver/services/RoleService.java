package com.ubuuy.springserver.services;

import com.ubuuy.springserver.models.entities.RoleEntity;
import com.ubuuy.springserver.models.enums.UserRole;

import java.sql.SQLException;

public interface RoleService {

    RoleEntity findExistingOrSaveNew(UserRole userRole) throws SQLException;
}
