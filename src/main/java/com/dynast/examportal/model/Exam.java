package com.dynast.examportal.model;

import com.dynast.examportal.util.AbstractTimestampEntity;
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
@Entity(name = "exam")
public class Exam extends AbstractTimestampEntity implements Serializable {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String examId;

    //    @ManyToOne
    @JoinColumn(name = "subjectId")
    private Subject subjectId;

    @JoinColumn(name = "examCategoryId")
    private ExamCategory examCategoryId;

    private String examTitle;
    private String examDescription;
    private String isNegativeAllowed;
    private int totalMark;
    private int examDuration;
    private Date examEndDate;

    @Column(name = "updated_by")
    private String updatedBy;
}