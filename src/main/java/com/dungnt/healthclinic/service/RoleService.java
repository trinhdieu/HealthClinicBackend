package com.dungnt.healthclinic.service;

import com.dungnt.healthclinic.model.Role;

public interface RoleService {
    Role findByName(String name) throws Exception;
}
