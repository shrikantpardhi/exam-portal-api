package com.dynast.examportal.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonProperty("tags")
    private Set<TagDto> tags;
    private String examTitle;
    private String examDescription;
    private Boolean isNegativeAllowed;
    private int totalMark;
    private int examDuration;
    private Date examStartDate;
    private Date examEndDate;
    private Boolean isPaid;
    @JsonProperty("educatorCode")
    private EducatorCodeDto educatorCode;
    private int totalQuestions;
//    @JsonProperty("user")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JsonIgnore
    private UserDto user;
    private Boolean status;
    private Date created;
    private Date updated;
}
