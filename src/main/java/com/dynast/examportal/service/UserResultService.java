package com.dynast.examportal.service;

import com.dynast.examportal.dto.ExamDto;
import com.dynast.examportal.dto.UserDto;
import com.dynast.examportal.dto.UserResultDto;
import com.dynast.examportal.exception.NotFoundException;
import com.dynast.examportal.exception.UnprocessableEntityException;
import com.dynast.examportal.model.Exam;
import com.dynast.examportal.model.User;
import com.dynast.examportal.model.UserResult;
import com.dynast.examportal.repository.ExamRepository;
import com.dynast.examportal.repository.UserRepository;
import com.dynast.examportal.repository.UserResultRepository;
import com.dynast.examportal.util.ObjectMapperSingleton;
import com.dynast.examportal.util.ResultJsonData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class UserResultService {

    private final UserResultRepository userResultRepository;

    private final UserRepository userRepository;

    private final ExamRepository examRepository;

    ObjectMapper mapper = ObjectMapperSingleton.getInstance();

    public UserResultService(UserResultRepository userResultRepository, UserRepository userRepository, ExamRepository examRepository) {
        this.userResultRepository = userResultRepository;
        this.userRepository = userRepository;
        this.examRepository = examRepository;
    }

    public Iterable<UserResultDto> getAll() {
        Iterable<UserResult> userResults = userResultRepository.findAll();
        return getUserResultDtoList(userResults);
    }

    public Iterable<UserResultDto> getAllResultByUser(String userName) {
        User user = userRepository.findByUserName(userName).orElseThrow(
                () -> new NotFoundException("Could not find User")
        );
        Iterable<UserResult> userResults = userResultRepository.findByUser(user);
        return getUserResultDtoList(userResults);
    }

    public UserResultDto getResultByUser(String userName, String resultId) {
        User user = userRepository.findByUserName(userName).orElseThrow(
                () -> new NotFoundException("Could not find User")
        );
        UserResult userResult = userResultRepository.findByUserAndResultId(user, resultId)
                .orElseThrow(
                        () -> new NotFoundException("Could not find result!")
                );
        return toUserResultDto(userResult);
    }

    public UserResultDto create(UserResultDto userResult) {
        UserResult userResult1 = mapper.convertValue(userResult, UserResult.class);
        Exam exam = getExam(userResult);
        User user = getUser(userResult);
        String resultJsonData = getResultDataToJsonString(userResult);
        createResultData(userResult, exam, user, resultJsonData, userResult1);
        return toUserResultDto(userResultRepository.save(userResult1));
    }

    public UserResultDto update(UserResultDto userResult) {
        Exam exam = getExam(userResult);
        User user = getUser(userResult);
        String resultJsonData = getResultDataToJsonString(userResult);
        return userResultRepository.findById(userResult.getResultId())
                .map(
                        userResult1 -> {
                            createResultData(userResult, exam, user, resultJsonData, userResult1);
                            return toUserResultDto(userResultRepository.save(userResult1));
                        }
                ).orElseThrow(
                        () -> new UnprocessableEntityException("Could not able to process request!")
                );
    }

    private void createResultData(UserResultDto userResult, Exam exam, User user, String resultJsonData, UserResult userResult1) {
        userResult1.setUser(user);
        userResult1.setExam(exam);
        userResult1.setResultJsonData(resultJsonData);
        userResult1.setStartAt(userResult.getStartAt());
        userResult1.setEndAt(userResult.getEndAt());
        userResult1.setNegativeMark(userResult.getNegativeMark());
        userResult1.setObtainedMark(userResult.getObtainedMark());
        userResult1.setTotalDuration(userResult.getTotalDuration());
    }

    private String getResultDataToJsonString(UserResultDto userResult) {
        String resultJsonData = null;
        try {
            resultJsonData = mapper.writeValueAsString(userResult.getResultJsonDataList());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return resultJsonData;
    }

    private User getUser(UserResultDto userResult) {
        return userRepository.findByUserName(userResult.getUserDto().getUserName()).orElseThrow(
                () -> new NotFoundException("Could not find User")
        );
    }

    private Exam getExam(UserResultDto userResult) {
        return examRepository.findById(userResult.getExamDto().getExamId()).orElseThrow(
                () -> new NotFoundException("Could not find Exam")
        );
    }

    private UserResultDto toUserResultDto(UserResult userResult) {
        UserResultDto userResultDto = mapper.convertValue(userResult, UserResultDto.class);
        List<ResultJsonData> resultJsonData = null;
        try {
            resultJsonData = mapper.readValue(userResult.getResultJsonData(), new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        ExamDto examDto = mapper.convertValue(userResult.getExam(), ExamDto.class);
        UserDto userDto = mapper.convertValue(userResult.getUser(), UserDto.class);
        userResultDto.setUserDto(userDto);
        userResultDto.setExamDto(examDto);
        assert resultJsonData != null;
        userResultDto.setResultJsonDataList(Collections.unmodifiableList(resultJsonData));
        return userResultDto;
    }

    private Iterable<UserResultDto> getUserResultDtoList(Iterable<UserResult> userResults) {
        List<UserResultDto> userResultDtos = new ArrayList<>();
        userResults.forEach(
                userResult -> userResultDtos.add(toUserResultDto(userResult))
        );
        return userResultDtos;
    }
}
