package com.dynast.examportal.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "answer")
public class Answer {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String answerId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "questionId", referencedColumnName = "questionId", foreignKey = @ForeignKey(name="FK_ANSWER_QUESTION"))
    private Question question;

    @Type(type = "text")
    @NotNull(message = "Answer is Mandatory")
    private String answerText;

    @Lob
    @Column(name = "answer_image", columnDefinition = "BLOB")
    private byte[] answerImage;

    private Boolean isCorrect;
}
