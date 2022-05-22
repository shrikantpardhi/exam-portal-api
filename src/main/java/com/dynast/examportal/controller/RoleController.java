package com.dynast.examportal.controller;

import com.dynast.examportal.model.Role;
import com.dynast.examportal.service.RoleService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Api(value = "Role APIs", tags = {"Role Controller"})
@RequestMapping(value = "/api/v1/role/")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @PostMapping({"/create"})
    @PreAuthorize("hasRole('Admin')")
    public Role createNewRole(@Valid @RequestBody Role role) {
        return roleService.createNewRole(role);
    }

    @GetMapping("all")
    public Iterable<Role> getAll() {
        return roleService.getAll();
    }

}
