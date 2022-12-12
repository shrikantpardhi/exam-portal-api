package com.dynast.examportal.model;

import com.dynast.examportal.util.AbstractTimestampEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity(name = "exam")
public class Exam extends AbstractTimestampEntity implements Serializable {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String examId;

    @ManyToMany(cascade= CascadeType.ALL)
    @JoinColumn(name = "tagId", referencedColumnName = "tagId", foreignKey = @ForeignKey(name="FK_EXAM_TAG"))
    private Set<Tag> tags;

    private String examTitle;
    private String examDescription;
    private Boolean isNegativeAllowed;
    private int totalMark;
    private int examDuration;
    private Date examStartDate;
    private Date examEndDate;
    private Boolean isPaid;

    @ManyToOne(cascade= CascadeType.ALL)
    @JoinColumn(name = "codeId", referencedColumnName = "codeId", foreignKey = @ForeignKey(name="FK_EXAM_EDUCATOR_CODE"))
    private EducatorCode educatorCode;

//    allow educator or admin to create exam
    @OneToOne(cascade= CascadeType.MERGE)
    @JoinColumn(name="userId", referencedColumnName = "userId", foreignKey = @ForeignKey(name="FK_EXAM_USER"))
    private User user;
    private Boolean status = true;
}