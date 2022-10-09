package com.dynast.examportal.dto;

import com.dynast.examportal.model.Role;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto implements Serializable {
    private String userName;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String mobile;
    private String address;
    private String image;
    private String city;
    private String state;
    private String education;
    private String status;
    private String updatedBy;
    private Set<Role> role;
}
