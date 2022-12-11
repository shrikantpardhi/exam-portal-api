package com.dynast.examportal.model;

import com.dynast.examportal.util.AbstractTimestampEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "user_result")
public class Result extends AbstractTimestampEntity {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String resultId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId", referencedColumnName = "userId", foreignKey = @ForeignKey(name="FK_RESULT_USER"))
    private User user;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "examId", referencedColumnName = "examId", foreignKey = @ForeignKey(name="FK_RESULT_EXAM"))
    private Exam exam;
    private String obtainedMark;
    private String negativeMark;
    private Date startAt;
    private Date EndAt;
    private String totalDuration;
    private int answeredQuestion;
    private int correctAnswer;
    private int inCorrectAnswer;
    @Type(type = "text")
    private String resultJsonData;
}