package com.dynast.examportal.controller;

import com.dynast.examportal.exception.DataBaseException;
import com.dynast.examportal.model.Subject;
import com.dynast.examportal.service.SubjectService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@Api(value = "Subject APIs", tags = {"Subject Controller"})
@RequestMapping(value = "/api/v1/subject/")
public class SubjectController {
    @Autowired
    private SubjectService subjectService;

    @ApiOperation(value = "This is used to get a subject", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully fetched"),
            @ApiResponse(code = 404, message = "Not found")
    })
    @GetMapping("{sujectId}")
//    @PreAuthorize("hasRole('Admin')")
    Subject oneSubject(@ApiParam(name = "Subject Id", required = true) @PathVariable String sujectId) {
        return subjectService.getSubjectById(sujectId);
    }


    @ApiOperation(value = "This is used to create a subject", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully created"),
            @ApiResponse(code = 422, message = "failed to create")
    })
    @PostMapping({"create"})
//    @PreAuthorize("hasRole('Admin')")
    public Subject createNewSubject(@ApiParam(name = "Subject", required = true) @RequestBody Subject subject) {
        return subjectService.createNewSubject(subject);
    }

    @ApiOperation(value = "This is used to update a subject", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully created"),
            @ApiResponse(code = 422, message = "failed to create")
    })
    @PutMapping({"update"})
//    @PreAuthorize("hasRole('Admin')")
    public Optional<Subject> updateSubject(@ApiParam(name = "Subject", required = true) @RequestBody Subject subject) throws DataBaseException {
        return subjectService.updateSubject(subject);
    }

    @ApiOperation(value = "This is used to get all subject", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully fetched"),
            @ApiResponse(code = 404, message = "Not Found")
    })
    @GetMapping("all")
//    @PreAuthorize("hasRole('Admin')")
    Iterable<Subject> allSubject() {
        return subjectService.getAllSubject();
    }

    @ApiOperation(value = "This is used to delete a subject", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted"),
            @ApiResponse(code = 422, message = "failed to delete")
    })
    @DeleteMapping("{subjectId}")
//    @PreAuthorize("hasRole('Admin')")
    Subject deleteSubject(@ApiParam(name = "Subject id", required = true) @PathVariable String subjectId) throws DataBaseException {
        return subjectService.deleteSubjectById(subjectId);
    }
}
