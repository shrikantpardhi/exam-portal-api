package com.dynast.examportal.service;

import com.dynast.examportal.dto.ResultPageDto;
import com.dynast.examportal.dto.UserResultDto;

public interface ResultService {
    Iterable<UserResultDto> getAll();

    Iterable<UserResultDto> getAllResultByUser(String userId);

    ResultPageDto getResultPageByUser(String userId, String resultId);

    UserResultDto create(UserResultDto userResult);

    UserResultDto update(UserResultDto userResult);
}
