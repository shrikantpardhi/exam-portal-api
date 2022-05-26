package com.dynast.examportal.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@JsonIgnoreProperties(ignoreUnknown = true)
@JsonIgnoreType(false)
@Component
public class SubjectDto implements Serializable {
    private String subjectId;
    @NotNull
    private String title;
    @NotNull
    private String description;
    @NotNull
    private String updatedBy;
    private Date created;
    private Date updated;
}
