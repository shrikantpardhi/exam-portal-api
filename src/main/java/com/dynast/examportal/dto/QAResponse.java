package com.dynast.examportal.dto;

import com.dynast.examportal.common.CorrectAnswer;
import com.dynast.examportal.common.Question;
import com.dynast.examportal.common.SelectedAnswer;

@lombok.Data
public class QAResponse {
    private Question question;
    private CorrectAnswer correctAnswer;
    private SelectedAnswer selectedAnswer;
    private Boolean correct;
}
