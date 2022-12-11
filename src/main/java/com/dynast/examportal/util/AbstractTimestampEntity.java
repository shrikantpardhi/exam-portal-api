package com.dynast.examportal.util;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@MappedSuperclass
public abstract class AbstractTimestampEntity {
    //DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created", nullable = true)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated", nullable = true)
    private Date updatedAt;

    @PrePersist
    protected void onCreate() {
        updatedAt = new Date();
        createdAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }


}
