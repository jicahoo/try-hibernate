package com.jichao.hibernate.query.hql.case_multiple_count.model;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import java.util.List;

/**
 * Created by zhangj52 on 4/28/2017.
 */
@Entity
@Table(name = "_host")
public class Host {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    @Id
    @Column(name = "_id")
    private String id;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "_name")
    private String name;


    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "_host_iscsiHostInitiators", joinColumns = {@JoinColumn(name = "_id")}, inverseJoinColumns = {@JoinColumn(name = "_elementvalue")})
    @Cascade({CascadeType.ALL})
    protected List<HostInitiator> iscsiHostInitiators;


    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "_host_fcHostInitiators", joinColumns = {@JoinColumn(name = "_id")}, inverseJoinColumns = {@JoinColumn(name = "_elementvalue")})
    @Cascade({CascadeType.ALL})
    protected List<HostInitiator> fcHostInitiators;


}
