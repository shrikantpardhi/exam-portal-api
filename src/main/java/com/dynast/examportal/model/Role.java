package com.dynast.examportal.model;

import com.dynast.examportal.util.AbstractTimestampEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Role extends AbstractTimestampEntity implements Serializable {

    @Id
    @NotBlank(message = "Role is mandatory")
    private String roleName;

    @NotBlank(message = "Role description is mandatory")

    private String roleDescription;

    private String updatedBy;
}
