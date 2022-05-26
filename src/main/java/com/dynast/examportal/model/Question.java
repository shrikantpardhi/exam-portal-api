package com.dynast.examportal.model;

import com.dynast.examportal.util.AbstractTimestampEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "question")
public class Question extends AbstractTimestampEntity implements Serializable {

    //    @NotBlank(message = "Name is mandatory")
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
//    @Column(name="QUE_ID")
    private String questionId;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "questionExamId", referencedColumnName = "examId")
    private Exam exam;

    @NotNull(message = "Subject is Mandatory")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "subjectId", referencedColumnName = "subjectId")
    private Subject subject;

    @NotNull(message = "Question Type is Mandatory")
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "questionTypeId", referencedColumnName = "questionTypeId")
    private QuestionType questionType;

    @Type(type = "text")
    @NotNull(message = "Question Name is Mandatory")
    private String questionTitle;

    @Type(type = "text")
    private String questionDescription;

    @Type(type = "text")
    private String questionAnswerDescription;

    private Boolean isNegativeAllowed;

    @Lob
    @Column(name = "question_image", columnDefinition = "BLOB")
    private byte[] questionImage;

    @Lob
    @Column(name = "answer_desc_image", columnDefinition = "BLOB")
    private byte[] questionAnswerDescriptionImage;

    @Column(name = "question_mark", length = 2)
    private int questionMark;

    @Column(name = "updated_by", nullable = true)
    private String updatedBy;

}
