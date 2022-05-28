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
public class UserResultDto implements Serializable {
    private String resultId;
    private String userId;
    private String examId;
    private String obtainedMark;
    private String negativeMark;
    private Date startAt;
    private Date EndAt;
    private String totalDuration;
    private String resultJsonData;
}
