package com.dynast.examportal.dto;

import com.dynast.examportal.model.Role;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto implements Serializable {
    private String userId;
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
    private Boolean status;
    @JsonProperty("roles")
    private Set<Role> roles;
    @JsonProperty("educatorCodes")
    private Set<EducatorCodeDto> educatorCodes;
    private Date created;
    private Date updated;
}
