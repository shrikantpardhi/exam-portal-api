package com.dynast.examportal.dto;

import com.dynast.examportal.model.Exam;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResultPageDto {
    private List<ResultData> resultDataList;
    private Exam exam;
    private String obtainedMark;
    private String negativeMark;
    private Date startAt;
    private Date EndAt;
    private int totalMark;
}
