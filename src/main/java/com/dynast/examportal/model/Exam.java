package com.dynast.examportal.model;

import com.dynast.examportal.util.AbstractTimestampEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity(name = "exam")
public class Exam extends AbstractTimestampEntity implements Serializable {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String examId;

    @ManyToOne(fetch = FetchType.EAGER)
//    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "subjectId", referencedColumnName = "subjectId")
    private Subject subject;

    @ManyToOne(fetch = FetchType.EAGER)
//    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @JoinColumn(name = "examCategoryId", referencedColumnName = "examCategoryId")
    private ExamCategory examCategory;

    private String examTitle;
    private String examDescription;
    private Boolean isNegativeAllowed;
    private int totalMark;
    private int examDuration;
    private Date examEndDate;
    private Boolean isPaid;
    private String updatedBy;
}