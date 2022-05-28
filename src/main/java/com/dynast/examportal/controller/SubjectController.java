package com.dynast.examportal.controller;

import com.dynast.examportal.dto.SubjectDto;
import com.dynast.examportal.exception.DataBaseException;
import com.dynast.examportal.service.SubjectService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public SubjectDto oneSubject(@ApiParam(name = "sujectId", required = true) @PathVariable String sujectId) {
        return subjectService.getSubjectById(sujectId);
    }


    @ApiOperation(value = "This is used to create a subject", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully created"),
            @ApiResponse(code = 422, message = "failed to create")
    })
    @PostMapping({"create"})
//    @PreAuthorize("hasRole('Admin')")
    public SubjectDto createNewSubject(@ApiParam(name = "subject", required = true) @RequestBody SubjectDto subject) {
        return subjectService.createNewSubject(subject);
    }

    @ApiOperation(value = "This is used to update a subject", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully created"),
            @ApiResponse(code = 422, message = "failed to create")
    })
    @PutMapping({"update"})
//    @PreAuthorize("hasRole('Admin')")
    public Optional<SubjectDto> updateSubject(@ApiParam(name = "subject", required = true) @RequestBody SubjectDto subject) throws DataBaseException {
        return subjectService.updateSubject(subject);
    }

    @ApiOperation(value = "This is used to get all subject", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully fetched"),
            @ApiResponse(code = 404, message = "Not Found")
    })
    @GetMapping("all")
//    @PreAuthorize("hasRole('Admin')")
    public Iterable<SubjectDto> allSubject() {
        return subjectService.getAllSubject();
    }

    @ApiOperation(value = "This is used to delete a subject", notes = "")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully deleted"),
            @ApiResponse(code = 422, message = "failed to delete")
    })
    @DeleteMapping("{subjectId}")
//    @PreAuthorize("hasRole('Admin')")
    public void deleteSubject(@ApiParam(name = "subjectId", required = true) @PathVariable String subjectId) throws DataBaseException {
        subjectService.deleteSubjectById(subjectId);
    }
}
