package com.dynast.examportal.service;

import com.dynast.examportal.dto.EducatorCodeDto;
import com.dynast.examportal.model.User;

import java.util.List;

public interface EducatorCodeService {
    EducatorCodeDto create(EducatorCodeDto educatorCodeDto, User user);
    EducatorCodeDto update(EducatorCodeDto educatorCodeDto, User user);
    void delete(EducatorCodeDto educatorCodeDto);
    List<EducatorCodeDto> fetchAll();
    EducatorCodeDto fetchByCode(String code);
}
