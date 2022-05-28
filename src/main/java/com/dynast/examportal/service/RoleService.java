package com.dynast.examportal.service;

import com.dynast.examportal.model.Role;
import com.dynast.examportal.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role createNewRole(Role role) {
        return roleRepository.save(role);
    }

    public Iterable<Role> getAll() {
        return roleRepository.findAll();
    }
}
