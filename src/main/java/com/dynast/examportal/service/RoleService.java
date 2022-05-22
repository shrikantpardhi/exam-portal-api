package com.dynast.examportal.service;

import com.dynast.examportal.model.Role;
import com.dynast.examportal.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Role createNewRole(Role role) {
        return roleRepository.save(role);
    }

    public Iterable<Role> getAll() {
        return roleRepository.findAll();
    }
}
