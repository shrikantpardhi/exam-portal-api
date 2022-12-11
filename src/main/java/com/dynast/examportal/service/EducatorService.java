package com.dynast.examportal.service;

import com.dynast.examportal.model.EducatorCode;
import com.dynast.examportal.model.User;

import java.util.List;

public interface EducatorService {
    EducatorCode create(EducatorCode educatorCode, User user);
    EducatorCode update(EducatorCode educatorCode, User user);
    void delete(EducatorCode educatorCode);
    List<EducatorCode> fetchAll();
    EducatorCode fetchByCode(String code);
}
