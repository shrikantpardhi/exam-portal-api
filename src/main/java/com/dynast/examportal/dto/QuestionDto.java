package com.dynast.examportal.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class QuestionDto implements Serializable {
    private String questionId;
    private ExamDto examDto;
    private SubjectDto subjectDto;
    private QuestionTypeDto questionTypeDto;
    private String questionTitle;

    private String questionDescription;

    private String questionAnswerDescription;
    private Boolean isNegativeAllowed;

    private byte[] questionImage;

    private byte[] questionAnswerDescriptionImage;
    private int questionMark;

    private String updatedBy;
}
