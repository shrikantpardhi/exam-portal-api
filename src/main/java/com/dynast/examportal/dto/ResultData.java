package com.dynast.examportal.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultData {
    private String seqId;
    private String questionTitle;
    private String questionDescription;
    private String questionAnswerDescription;
    private Boolean isNegativeAllowed;
    private byte[] questionImage;
    private byte[] questionAnswerDescriptionImage;
    private int questionMark;
//    private List<QuestionAnswer> questionAnswerList;
    private QuestionAnswer correctAnswer;
    private QuestionAnswer submittedAnswer;
}
