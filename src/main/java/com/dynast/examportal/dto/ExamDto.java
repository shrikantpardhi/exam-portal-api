package com.dynast.examportal.dto;

import com.dynast.examportal.model.EducatorCode;
import com.dynast.examportal.model.Tag;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ExamDto implements Serializable {
    private String examId;
    private Set<Tag> tags;
    private String examTitle;
    private String examDescription;
    private Boolean isNegativeAllowed;
    private int totalMark;
    private int examDuration;
    private Date examStartDate;
    private Date examEndDate;
    private Boolean isPaid;
    private EducatorCode educatorCode;
    private int totalQuestions;
    @JsonProperty("user")
    private UserDto userDto;
}
