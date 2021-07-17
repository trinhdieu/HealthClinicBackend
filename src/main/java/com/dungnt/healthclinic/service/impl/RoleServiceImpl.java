package com.dungnt.healthclinic.service.impl;

import com.dungnt.healthclinic.model.Role;
import com.dungnt.healthclinic.repository.RoleRepository;
import com.dungnt.healthclinic.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class RoleServiceImpl implements RoleService {
    private RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Role findByName(String name) throws Exception {
        if (name == null) {
            throw new Exception("Gia tri name null");
        }
        return roleRepository.findByName(name);
    }
}
