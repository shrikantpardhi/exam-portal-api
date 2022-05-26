package com.dynast.examportal.service;

import com.dynast.examportal.exception.NotFoundException;
import com.dynast.examportal.exception.UnprocessableEntityException;
import com.dynast.examportal.model.UserResult;
import com.dynast.examportal.repository.UserResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserResultService {

    @Autowired
    private UserResultRepository userResultRepository;

    public Iterable<UserResult> getAll() {
        return userResultRepository.findAll();
    }

    public Iterable<UserResult> getAllResultByUser(String userName) {
        return userResultRepository.findByUserId(userName);
    }

    public UserResult getResultByUser(String userName, String resultId) {
        return userResultRepository.findByUserIdAndResultId(userName, resultId)
                .orElseThrow(
                        () -> new NotFoundException("Could not find result!")
                );
    }

    public UserResult create(UserResult userResult) {
        return userResultRepository.save(userResult);
    }

    public UserResult update(UserResult userResult) {
        return userResultRepository.findById(userResult.getResultId())
                .map(
                        userResult1 -> {
                            return userResultRepository.save(userResult1);
                        }
                ).orElseThrow(
                        () -> new UnprocessableEntityException("Could not able to process request!")
                );
    }
}
