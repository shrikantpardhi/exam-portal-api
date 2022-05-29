package com.dynast.examportal.dto;

import com.dynast.examportal.util.ResultJsonData;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResultDto implements Serializable {
    private String resultId;
    private UserDto userDto;
    private ExamDto examDto;
    private String obtainedMark;
    private String negativeMark;
    private Date startAt;
    private Date EndAt;
    private String totalDuration;
    private List<ResultJsonData> resultJsonDataList;
}
