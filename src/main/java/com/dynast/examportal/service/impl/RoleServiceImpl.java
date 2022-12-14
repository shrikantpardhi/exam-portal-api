package com.dynast.examportal.service.impl;

import com.dynast.examportal.model.Role;
import com.dynast.examportal.service.RoleService;
import com.dynast.examportal.util.Roles;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class RoleServiceImpl implements RoleService {
    @Override
    public Set<Role> getUserRole() {
        HashSet<Role> roles = new HashSet<>();
        roles.add(new Role(Roles.USER.getLabel(), Roles.USER.getDescription()));
        return roles;
    }

    @Override
    public Set<Role> getEducatorRole() {
        HashSet<Role> roles = new HashSet<>();
        roles.add(new Role(Roles.EDUCATOR.getLabel(), Roles.EDUCATOR.getDescription()));
        return roles;
    }

    @Override
    public Set<Role> getEducatorAndUserRole() {
        HashSet<Role> roles = new HashSet<>();
        roles.add(new Role(Roles.USER.getLabel(), Roles.USER.getDescription()));
        roles.add(new Role(Roles.EDUCATOR.getLabel(), Roles.EDUCATOR.getDescription()));
        return roles;
    }
}
