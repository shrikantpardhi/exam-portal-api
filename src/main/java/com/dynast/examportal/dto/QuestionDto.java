package com.dynast.examportal.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class QuestionDto implements Serializable {
    private String questionId;
    @JsonProperty("exam")
    private ExamDto exam;
    @JsonProperty("tag")
    private TagDto tag;
    private String questionType;
    private String questionTitle;
    private String questionDescription;
    private String questionAnswerDescription;
    private Boolean isNegativeAllowed;
    private byte[] questionImage;
    private byte[] answerDescriptionImage;
    private int questionMark;
    @JsonProperty("answers")
    private List<AnswerDto> answers;
}
