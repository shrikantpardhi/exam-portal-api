package com.dynast.examportal.service.Impl;

import com.dynast.examportal.dto.ExamDto;
import com.dynast.examportal.dto.QuestionAnswer;
import com.dynast.examportal.dto.ResultData;
import com.dynast.examportal.dto.ResultPageDto;
import com.dynast.examportal.dto.UserDto;
import com.dynast.examportal.dto.UserResultDto;
import com.dynast.examportal.exception.NotFoundException;
import com.dynast.examportal.exception.UnprocessableEntityException;
import com.dynast.examportal.model.Answer;
import com.dynast.examportal.model.Exam;
import com.dynast.examportal.model.Question;
import com.dynast.examportal.model.User;
import com.dynast.examportal.model.UserResult;
import com.dynast.examportal.repository.AnswerRepository;
import com.dynast.examportal.repository.ExamRepository;
import com.dynast.examportal.repository.QuestionRepository;
import com.dynast.examportal.repository.UserRepository;
import com.dynast.examportal.repository.UserResultRepository;
import com.dynast.examportal.service.UserResultService;
import com.dynast.examportal.util.ObjectMapperSingleton;
import com.dynast.examportal.util.ResultJsonData;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserResultServiceImpl implements UserResultService {

    private final UserResultRepository userResultRepository;

    private final UserRepository userRepository;

    private final ExamRepository examRepository;

    private final QuestionRepository questionRepository;

    private final AnswerRepository answerRepository;

    ObjectMapper mapper = ObjectMapperSingleton.getInstance();

    public UserResultServiceImpl(UserResultRepository userResultRepository, UserRepository userRepository, ExamRepository examRepository, QuestionRepository questionRepository, AnswerRepository answerRepository) {
        this.userResultRepository = userResultRepository;
        this.userRepository = userRepository;
        this.examRepository = examRepository;
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    @Override
    public Iterable<UserResultDto> getAll() {
        Iterable<UserResult> userResults = userResultRepository.findAll();
        return getUserResultDtoList(userResults);
    }

    @Override
    public Iterable<UserResultDto> getAllResultByUser(String userName) {
        User user = userRepository.findByUserName(userName).orElseThrow(
                () -> new NotFoundException("Could not find User")
        );
        Iterable<UserResult> userResults = userResultRepository.findByUser(user);
        return getUserResultDtoList(userResults);
    }

    @Override
    public UserResultDto create(UserResultDto userResult) {
        UserResult userResult1 = mapper.convertValue(userResult, UserResult.class);
        Exam exam = getExam(userResult);
        User user = getUser(userResult);
        String resultJsonData = getResultDataToJsonString(userResult);
        createResultData(userResult, exam, user, resultJsonData, userResult1);
        return toUserResultDto(userResultRepository.save(userResult1));
    }

    @Override
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

    @Override
    public ResultPageDto getResultPageByUser(String userName, String resultId) {
        ResultPageDto resultPageDto = new ResultPageDto();
        List<ResultData> resultDataList = new ArrayList<>();
        User user = userRepository.findByUserName(userName).orElseThrow(
                () -> new NotFoundException("Could not find User")
        );
        UserResult userResult = userResultRepository.findByUserAndResultId(user, resultId)
                .orElseThrow(
                        () -> new NotFoundException("Could not find result!")
                );

        List<ResultJsonData> resultJsonDataList = getResultJsonDataList(userResult.getResultJsonData());
        if (!resultJsonDataList.isEmpty()) {
            resultJsonDataList.forEach(
                    resultJsonData -> resultDataList.add(getResultDetails(resultJsonData))
            );
        }
        resultPageDto.setResultDataList(resultDataList);
        resultPageDto.setStartAt(userResult.getStartAt());
        resultPageDto.setEndAt(userResult.getEndAt());
        resultPageDto.setExam(userResult.getExam());
        resultPageDto.setNegativeMark(userResult.getNegativeMark());
        resultPageDto.setObtainedMark(userResult.getObtainedMark());
        resultPageDto.setTotalMark(userResult.getExam().getTotalMark());
        return resultPageDto;
    }

    private ResultData getResultDetails(ResultJsonData resultJsonData) {
        ResultData resultData = new ResultData();
        resultData.setSeqId(resultJsonData.getSeqId());
        Question question = getQuestionById(resultJsonData.getQuestionId());
        Answer submittedAnswer = answerRepository.findById(resultJsonData.getSubmittedAnswerId()).orElseThrow(
                () -> new NotFoundException("Could not find Answer")
        );
        Answer correctAnswer = answerRepository.findByQuestionAndIsCorrect(question, true);
        resultData.setQuestionTitle(question.getQuestionTitle());
        resultData.setQuestionDescription(question.getQuestionDescription());
        resultData.setQuestionImage(question.getQuestionImage());
        resultData.setQuestionAnswerDescriptionImage(question.getQuestionAnswerDescriptionImage());
        resultData.setIsNegativeAllowed(question.getIsNegativeAllowed());
        resultData.setQuestionMark(question.getQuestionMark());
        resultData.setCorrectAnswer(mapper.convertValue(correctAnswer, QuestionAnswer.class));
        resultData.setSubmittedAnswer(mapper.convertValue(submittedAnswer, QuestionAnswer.class));
        return resultData;
    }


    private Question getQuestionById(String questionId) {
        return questionRepository.findById(questionId).orElseThrow(
                () -> new NotFoundException("Could not find question")
        );
    }

    private void createResultData(UserResultDto userResult, Exam exam, User user, String resultJsonData, UserResult userResult1) {
        float totalObtainMark = 0;
        float negativeMark = 0;
        Map<String, String> map = getTotalObtainedMark(userResult, negativeMark, totalObtainMark);
        userResult1.setUser(user);
        userResult1.setExam(exam);
        userResult1.setResultJsonData(resultJsonData);
        userResult1.setStartAt(userResult.getStartAt());
        userResult1.setEndAt(userResult.getEndAt());
        userResult1.setNegativeMark(map.get("negativeMark"));
        userResult1.setObtainedMark(map.get("obtainedMark"));
        userResult1.setTotalDuration(userResult.getTotalDuration());
    }

    private Map<String, String> getTotalObtainedMark(UserResultDto userResultDto, float negativeMark, float totalObtainMark) {
        Map<String, String> map = new HashMap<>();
        List<ResultJsonData> resultJsonDataList = userResultDto.getResultJsonDataList();
        float finalTotalObtainMark = totalObtainMark;
        resultJsonDataList.forEach(
                resultJsonData -> {
                    Question question = getQuestionById(resultJsonData.getQuestionId());
                    Answer submittedAnswer = answerRepository.findById(resultJsonData.getSubmittedAnswerId()).orElseThrow(
                            () -> new NotFoundException("Could not find Answer")
                    );
                    Answer correctAnswer = answerRepository.findByQuestionAndIsCorrect(question, true);
                    if (submittedAnswer.getIsCorrect() == correctAnswer.getIsCorrect()) {
                        updateObtainedMark(finalTotalObtainMark, question.getQuestionMark());
                    } else if (question.getIsNegativeAllowed()) {
                        updateNegativeMark(negativeMark, question.getQuestionMark());
                    }
                }
        );
        totalObtainMark -= negativeMark;
        map.put("obtainedMark", Float.toString(totalObtainMark));
        map.put("negativeMark", Float.toString(negativeMark));
        return map;
    }

    private void updateNegativeMark(float negativeMark, int questionMark) {
        negativeMark += (questionMark / 3);
    }

    private void updateObtainedMark(float totalObtainMark, int questionMark) {
        totalObtainMark += questionMark;
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
        List<ResultJsonData> resultJsonData = getResultJsonDataList(userResult.getResultJsonData());
        ExamDto examDto = mapper.convertValue(userResult.getExam(), ExamDto.class);
        UserDto userDto = mapper.convertValue(userResult.getUser(), UserDto.class);
        userResultDto.setUserDto(userDto);
        userResultDto.setExamDto(examDto);
        assert resultJsonData != null;
        userResultDto.setResultJsonDataList(Collections.unmodifiableList(resultJsonData));
        return userResultDto;
    }

    private List<ResultJsonData> getResultJsonDataList(String resultJsonData) {
        List<ResultJsonData> resultJsonDataList = null;
        try {
            resultJsonDataList = mapper.readValue(resultJsonData, new TypeReference<>() {
            });
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return resultJsonDataList;
    }

    private Iterable<UserResultDto> getUserResultDtoList(Iterable<UserResult> userResults) {
        List<UserResultDto> userResultDtoList = new ArrayList<>();
        userResults.forEach(
                userResult -> userResultDtoList.add(toUserResultDto(userResult))
        );
        return userResultDtoList;
    }
}
