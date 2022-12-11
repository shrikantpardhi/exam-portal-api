package com.dynast.examportal.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Role {
    @Id
    @NotBlank(message = "Role is mandatory")
    private String roleName;

    @NotBlank(message = "Role description is mandatory")
    private String roleDescription;
}
