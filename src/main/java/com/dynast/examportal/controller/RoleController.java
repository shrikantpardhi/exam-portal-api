package com.dynast.examportal.controller;

import com.dynast.examportal.model.Role;
import com.dynast.examportal.service.RoleService;
import io.swagger.annotations.*;
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

    @ApiOperation(value = "This is used to create a Role", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully created"),
            @ApiResponse(code = 422, message = "failed to create")
    })
    @PostMapping({"/create"})
    @PreAuthorize("hasRole('Admin')")
    public Role createNewRole(@ApiParam(name = "Role", required = true) @Valid @RequestBody Role role) {
        return roleService.createNewRole(role);
    }

    @ApiOperation(value = "This is used to get all Role", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully fetched"),
            @ApiResponse(code = 404, message = "No data found")
    })
    @GetMapping("all")
    public Iterable<Role> getAll() {
        return roleService.getAll();
    }

}
