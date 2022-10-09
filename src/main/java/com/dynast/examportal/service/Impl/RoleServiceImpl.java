package com.dynast.examportal.service.Impl;

import com.dynast.examportal.model.Role;
import com.dynast.examportal.repository.RoleRepository;
import com.dynast.examportal.service.RoleService;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role createNewRole(Role role) {
        return roleRepository.save(role);
    }

    public Iterable<Role> getAll() {
        return roleRepository.findAll();
    }
}
