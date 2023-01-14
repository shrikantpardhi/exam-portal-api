package com.dynast.examportal.model;

import com.dynast.examportal.util.AbstractTimestampEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.io.Serializable;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Quiz extends AbstractTimestampEntity implements Serializable {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String quizId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "examId", referencedColumnName = "examId")
    private Exam exam;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId", referencedColumnName = "userId")
    private User user;

    private String duration;
    private Boolean submitted = false;
    private int correctCount = 0;
    private int inCorrectCount = 0;

    private String obtained;
    private String negativeObtained;

    /*
     * 0 < length <=      255  -->  `TINYBLOB`
     * 255 < length <=    65535  -->  `BLOB`
     * 65535 < length <= 16777215  -->  `MEDIUMBLOB`
     * 16777215 < length <=    2³¹-1  -->  `LONGBLOB`*/
    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private byte[] response;
}
