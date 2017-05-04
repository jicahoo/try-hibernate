package com.jichao.hibernate.query.hql.case_multiple_count.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by zhangj52 on 4/28/2017.
 */
@Entity
@Table(name = "host_initiator")
public class HostInitiator {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    @Id
    @Column(name = "id")
    private String id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "name")
    private String name;

}
