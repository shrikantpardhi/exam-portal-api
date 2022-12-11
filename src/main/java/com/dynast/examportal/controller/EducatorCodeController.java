package com.dynast.examportal.controller;

import com.dynast.examportal.model.EducatorCode;
import com.dynast.examportal.service.EducatorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;

@RestController
@Api(value = "Educator Code APIs", tags = {"Educator Controller"})
@RequestMapping(value = "/api/v1/educator-code/")
public class EducatorCodeController extends ApplicationController {
    private static final Logger LOGGER = Logger.getLogger(EducatorCodeController.class.getName());

    private final EducatorService educatorService;

    public EducatorCodeController(EducatorService educatorService) {
        this.educatorService = educatorService;
    }


    @GetMapping("all")
    @PreAuthorize("hasRole('Admin')")
//    @PreAuthorize("hasAnyRole('Admin', 'Educator')")
    Iterable<EducatorCode> getAll() {
        LOGGER.info("inside getUsers{}");
        return educatorService.fetchAll();
    }

    @GetMapping({"get/{code}"})
    public EducatorCode getEducatorCode(@ApiParam(name = "code", required = true) @PathVariable String code) {
        LOGGER.info("inside getEducatorCode :" + code);
        return educatorService.fetchByCode(code);
    }

    @PostMapping(value = {"create"}, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public EducatorCode createEducatorCode(@ApiParam(name = "educatorCode", required = true) @RequestBody EducatorCode educatorCode) {
        LOGGER.info("inside createEducatorCode :" + educatorCode.getCode());
        return educatorService.create(educatorCode, getUser());
    }

    @PutMapping(value = {"update"}, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public EducatorCode updateEducatorCode(@ApiParam(name = "educatorCode", required = true) @RequestBody EducatorCode educatorCode) {
        LOGGER.info("inside updateEducatorCode :" + educatorCode.getCode());
        return educatorService.update(educatorCode, getUser());
    }

    @DeleteMapping(value = {"delete"}, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteEducatorCode(@ApiParam(name = "educatorCode", required = true) @RequestBody EducatorCode educatorCode) {
        LOGGER.info("inside deleteEducatorCode :" + educatorCode.getCode());
        educatorService.delete(educatorCode);
    }

}
