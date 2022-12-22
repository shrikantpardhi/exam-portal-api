package com.dynast.examportal.service;

public interface ResultService {
    Iterable<UserResultDto> getAll();

    Iterable<UserResultDto> getAllResultByUser(String userId);

    ResultPageDto getResultPageByUser(String userId, String resultId);

    UserResultDto create(UserResultDto userResult);

    UserResultDto update(UserResultDto userResult);
}
