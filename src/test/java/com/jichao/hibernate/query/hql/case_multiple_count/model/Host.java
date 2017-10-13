package com.jichao.hibernate.query.hql.case_multiple_count.model;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.util.List;

/**
 * Created by zhangj52 on 4/28/2017.
 */
@Entity
@Table(name = "host")
public class Host {
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

    @Column(name = "type")
    private Integer type;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "host_iscsiHostInitiators", joinColumns = {@JoinColumn(name = "id")}, inverseJoinColumns = {@JoinColumn(name = "elementvalue")})
    @Cascade({CascadeType.ALL})
    protected List<HostInitiator> iscsiHostInitiators;


    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "host_fcHostInitiators", joinColumns = {@JoinColumn(name = "id")}, inverseJoinColumns = {@JoinColumn(name = "elementvalue")})
    @Cascade({CascadeType.ALL})
    protected List<HostInitiator> fcHostInitiators;


    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
