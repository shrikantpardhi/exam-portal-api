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

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "subjectId", referencedColumnName = "subjectId")
    private Subject subject;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "examCategoryId", referencedColumnName = "examCategoryId")
    private ExamCategory examCategory;

    private String examTitle;
    private String examDescription;
    private Boolean isNegativeAllowed;
    private int totalMark;
    private int examDuration;
    private Date examEndDate;
    private Boolean isPaid;

    @Column(name = "updated_by")
    private String updatedBy;
}