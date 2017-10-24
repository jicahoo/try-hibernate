package com.jichao.hibernate.query.usertype.model;

import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "filesystem")
public class FileSystem {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Id
    private String id;

    @Column(name = "start_time")
    @Type(type="com.jichao.hibernate.query.usertype.model.PlusOneUserType",
            parameters = {@Parameter(name="unit",value="minutes")})
    private Integer startTime;

    public Integer getStartTime() {
        return startTime;
    }

    public void setStartTime(Integer startTime) {
        this.startTime = startTime;
    }
}
