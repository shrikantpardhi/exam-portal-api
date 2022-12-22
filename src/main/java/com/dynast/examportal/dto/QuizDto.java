package com.dynast.examportal.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class QuizDto {
    private String quizId;
    private ExamDto exam;
    private UserDto user;
    private String duration;
    private Boolean submitted;
    private int correctCount;
    private int inCorrectCount;
    private String obtained;
    private String negativeObtained;
    private List<QAResponse> qaResponses;
    private Date created;
    private Date updated;
}
