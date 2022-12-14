package com.dynast.examportal.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Role  implements Serializable {
    @Id
    @NotBlank(message = "Role is mandatory")
    private String roleName;

    @NotBlank(message = "Role description is mandatory")
    private String roleDescription;
}
