package com.dynast.examportal.common;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@lombok.Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Question {
    private String id;
    private String title;
    private String description;
    private String answerDescription;
    private Boolean isNegativeAllowed;
    private int mark;
}
