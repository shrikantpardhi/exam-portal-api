package com.dynast.examportal.model;

import com.dynast.examportal.util.AbstractTimestampEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "question")
public class Question extends AbstractTimestampEntity implements Serializable {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String questionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "examId", referencedColumnName = "examId", foreignKey = @ForeignKey(name="FK_QUESTION_EXAM"))
    private Exam exam;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "tagId", referencedColumnName = "tagId", foreignKey = @ForeignKey(name="FK_QUESTION_TAG"))
    private Tag tag;

    private String questionType;

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
    private byte[] answerDescriptionImage;

    @Column(name = "question_mark", length = 2)
    private int questionMark;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(
            name = "QUESTION_ANSWERS",
            joinColumns = {
                    @JoinColumn(name = "QUESTION_ID")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "ANSWER_ID")
            }
    )
    private List<Answer> answers;

}
