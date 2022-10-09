package com.dynast.examportal.service;

import com.dynast.examportal.dto.ResultPageDto;
import com.dynast.examportal.dto.UserResultDto;

public interface UserResultService {
    Iterable<UserResultDto> getAll();

    Iterable<UserResultDto> getAllResultByUser(String userName);

    ResultPageDto getResultPageByUser(String userName, String resultId);

    UserResultDto create(UserResultDto userResult);

    UserResultDto update(UserResultDto userResult);
}
