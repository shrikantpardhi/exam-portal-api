package com.dynast.examportal.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExamDto implements Serializable {
    private String examId;

    private SubjectDto subjectDto;
    private ExamCategoryDto examCategoryDto;
    private String examTitle;
    private String examDescription;
    private Boolean isNegativeAllowed;
    private int totalMark;
    private int examDuration;
    private Date examEndDate;
    private Boolean isPaid;

    private String updatedBy;
}
