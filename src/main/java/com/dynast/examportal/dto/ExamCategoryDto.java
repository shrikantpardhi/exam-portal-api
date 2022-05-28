package com.dynast.examportal.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExamCategoryDto implements Serializable {
    private String examCategoryId;
//    @NotNull(message = "Exam Category is mandatory")
    private String examCategoryName;
    private String updatedBy;
    private Boolean isPremium;
}
