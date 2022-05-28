package com.dynast.examportal.service;

import com.dynast.examportal.dto.UserResultDto;
import com.dynast.examportal.exception.NotFoundException;
import com.dynast.examportal.exception.UnprocessableEntityException;
import com.dynast.examportal.model.UserResult;
import com.dynast.examportal.repository.UserResultRepository;
import com.dynast.examportal.util.ObjectMapperSingleton;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserResultService {

    private final UserResultRepository userResultRepository;

    ObjectMapper mapper = ObjectMapperSingleton.getInstance();

    public UserResultService(UserResultRepository userResultRepository) {
        this.userResultRepository = userResultRepository;
    }

    public Iterable<UserResultDto> getAll() {
        Iterable<UserResult> userResults = userResultRepository.findAll();
        return getUserResultDtoList(userResults);
    }

    public Iterable<UserResultDto> getAllResultByUser(String userName) {
        Iterable<UserResult> userResults = userResultRepository.findByUserId(userName);
        return getUserResultDtoList(userResults);
    }

    public UserResultDto getResultByUser(String userName, String resultId) {
        UserResult userResult = userResultRepository.findByUserIdAndResultId(userName, resultId)
                .orElseThrow(
                        () -> new NotFoundException("Could not find result!")
                );
        return mapper.convertValue(userResult, UserResultDto.class);
    }

    public UserResultDto create(UserResultDto userResult) {
        UserResult userResult1 = mapper.convertValue(userResult, UserResult.class);
        return mapper.convertValue(userResultRepository.save(userResult1), UserResultDto.class);
    }

    public UserResultDto update(UserResultDto userResult) {
        return userResultRepository.findById(userResult.getResultId())
                .map(
                        userResult1 -> {
                            userResult1.setExamId(userResult.getExamId());
                            userResult1.setResultJsonData(userResult.getResultJsonData());
                            userResult1.setStartAt(userResult.getStartAt());
                            userResult1.setEndAt(userResult.getEndAt());
                            userResult1.setNegativeMark(userResult.getNegativeMark());
                            userResult1.setObtainedMark(userResult.getObtainedMark());
                            userResult1.setTotalDuration(userResult.getTotalDuration());
                            return mapper.convertValue(userResultRepository.save(userResult1), UserResultDto.class);
                        }
                ).orElseThrow(
                        () -> new UnprocessableEntityException("Could not able to process request!")
                );
    }

    private Iterable<UserResultDto> getUserResultDtoList(Iterable<UserResult> userResults) {
        List<UserResultDto> userResultDtos = new ArrayList<>();
        userResults.forEach(
                userResult -> userResultDtos.add(mapper.convertValue(userResult, UserResultDto.class))
        );
        return userResultDtos;
    }
}
