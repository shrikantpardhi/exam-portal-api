package com.dynast.examportal.model;

import com.dynast.examportal.util.AbstractTimestampEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "user_result")
public class UserResult extends AbstractTimestampEntity implements Serializable {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String resultId;
    private String userId;
    private String examId;
    private String obtainedMark;
    private String negativeMark;
    private Date startAt;
    private Date EndAt;
    private String totalDuration;
    @Type(type = "text")
    private String resultJsonData;

}
