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
    private String userFirstName;
    private String userLastName;
    private String userPassword;
    private String email;
    private String userMobile;
    private String userAddress;
    private String userImage;
    private String userCity;
    private String userState;
    private String userEducation;
    private String userStatus;
    private String updatedBy;
    private Set<Role> role;
}
