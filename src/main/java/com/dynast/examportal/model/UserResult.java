package com.dynast.examportal.model;

import com.dynast.examportal.util.AbstractTimestampEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "user_result")
public class UserResult extends AbstractTimestampEntity implements Serializable {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String resultId;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userName", referencedColumnName = "userName")
    private User user;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "examId", referencedColumnName = "examId")
    private Exam exam;
    private String obtainedMark;
    private String negativeMark;
    private Date startAt;
    private Date EndAt;
    private String totalDuration;
    @Type(type = "text")
    private String resultJsonData;

}
