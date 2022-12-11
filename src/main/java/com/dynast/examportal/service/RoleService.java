package com.dynast.examportal.service;

import com.dynast.examportal.model.Role;

import java.util.Set;

public interface RoleService {
    Set<Role> getUserRole();
    Set<Role> getEducatorRole();
    Set<Role> getEducatorAndUserRole();
}
