package com.dynast.examportal.controller;

import com.dynast.examportal.model.Role;
import com.dynast.examportal.service.RoleService;
import io.swagger.annotations.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Api(value = "Role APIs", tags = {"Role Controller"})
@RequestMapping(value = "/api/v1/role/")
public class RoleController extends ApplicationController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @ApiOperation(value = "This is used to create a Role")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully created"),
            @ApiResponse(code = 422, message = "failed to create")
    })
    @PostMapping({"/create"})
    @PreAuthorize("hasRole('Admin')")
    public Role createNewRole(@ApiParam(name = "Role", required = true) @Valid @RequestBody Role role) {
        role.setUpdatedBy(getUser());
        return roleService.createNewRole(role);
    }

    @ApiOperation(value = "This is used to get all Role")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully fetched"),
            @ApiResponse(code = 404, message = "No data found")
    })
    @GetMapping("all")
    public Iterable<Role> getAll() {
        return roleService.getAll();
    }

}
