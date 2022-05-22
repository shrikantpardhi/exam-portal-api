package com.dynast.examportal.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "answer")
public class Answer {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String answerId;

    @JoinColumn(name = "questionId")
    private Question questionId;

    @Type(type = "text")
    @NotNull(message = "Answer is Mandatory")
    private String answerText;

    @Lob
    @Column(name = "answer_image", columnDefinition = "BLOB")
    private byte[] answerImage;

    private Boolean isCorrect;
}
