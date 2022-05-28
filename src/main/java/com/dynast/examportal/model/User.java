package com.dynast.examportal.model;

import com.dynast.examportal.util.AbstractTimestampEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity(name = "user")
public class User extends AbstractTimestampEntity implements Serializable {

    //	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String userName;
    private String userFirstName;
    private String userLastName;
    private String userPassword;
    @Column(unique = true)
    private String email;
    @Column(unique = true)
    private String userMobile;
    private String userAddress;
    @Lob
    @Column(name = "user_image", columnDefinition = "BLOB")
    private String userImage;
    private String userCity;
    private String userState;
    private String userEducation;
    private String userStatus;
    private String updatedBy;

    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "USER_ROLE",
            joinColumns = {
                    @JoinColumn(name = "USER_ID")
            },
            inverseJoinColumns = {
                    @JoinColumn(name = "ROLE_ID")
            }
    )
    private Set<Role> role;
}
