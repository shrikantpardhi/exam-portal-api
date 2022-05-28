package com.dynast.examportal.model;

import com.dynast.examportal.util.AbstractTimestampEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "subject")
public class Subject extends AbstractTimestampEntity implements Serializable {
    //    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "subject_seq")
//    @GenericGenerator(name = "subject_seq", strategy = "com.dynast.examportal.configuration.StringPrefixedSequenceIdGenerator",
//            parameters = {
//                    @Parameter(name = StringPrefixedSequenceIdGenerator.INCREMENT_PARAM, value = "50"),
//                    @Parameter(name = StringPrefixedSequenceIdGenerator.VALUE_PREFIX_PARAMETER, value = "SUB_"),
//                    @Parameter(name = StringPrefixedSequenceIdGenerator.NUMBER_FORMAT_PARAMETER, value = "%03d")})
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String subjectId;

    @NotBlank(message = "Subject title is mandatory")
    @Column(unique = true)
    private String title;

    private String description;

    private String updatedBy;
}
