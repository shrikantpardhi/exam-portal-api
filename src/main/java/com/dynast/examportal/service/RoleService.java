package com.dynast.examportal.service;

import com.dynast.examportal.model.Role;

public interface RoleService {
    Role createNewRole(Role role);

    Iterable<Role> getAll();
}
