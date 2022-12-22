package com.dynast.examportal.controller;

import com.dynast.examportal.dto.EducatorCodeDto;
import com.dynast.examportal.service.EducatorCodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

@RestController
@Api(value = "Educator Code APIs", tags = {"Educator Controller"})
@RequestMapping(value = "/api/v1/educator-code/")
public class EducatorCodeController extends ApplicationController {
    private static final Logger LOGGER = LoggerFactory.getLogger(EducatorCodeController.class);

    private final EducatorCodeService educatorService;

    public EducatorCodeController(EducatorCodeService educatorService) {
        this.educatorService = educatorService;
    }


    @GetMapping(value = {"all"}, produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('Admin')")
//    @PreAuthorize("hasAnyRole('Admin', 'Educator')")
    Iterable<EducatorCodeDto> getAll() {
        LOGGER.info("inside getAll");
        return educatorService.fetchAll();
    }

    @GetMapping(value = {"get/{code}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public EducatorCodeDto getEducatorCode(@ApiParam(name = "code", required = true) @PathVariable String code) {
        LOGGER.info("inside getEducatorCode :{} ", code);
        return educatorService.fetchByCode(code);
    }

    @PostMapping(value = {"create"}, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public EducatorCodeDto createEducatorCode(@ApiParam(name = "educatorCode", required = true) @RequestBody EducatorCodeDto educatorCode) {
        LOGGER.info("inside createEducatorCode :{} ", educatorCode.getCode());
        return educatorService.create(educatorCode, getUser());
    }

    @PutMapping(value = {"update"}, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public EducatorCodeDto updateEducatorCode(@ApiParam(name = "educatorCode", required = true) @RequestBody EducatorCodeDto educatorCode) {
        LOGGER.info("inside updateEducatorCode :{} ", educatorCode.getCode());
        return educatorService.update(educatorCode, getUser());
    }

//    not working because of foreign key
    @DeleteMapping(value = {"delete"}, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public void deleteEducatorCode(@ApiParam(name = "educatorCode", required = true) @RequestBody EducatorCodeDto educatorCode) {
        LOGGER.info("inside deleteEducatorCode :{} ", educatorCode.getCode());
        educatorService.delete(educatorCode);
    }

}
