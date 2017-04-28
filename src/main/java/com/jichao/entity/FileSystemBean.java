package com.jichao.entity;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by zhangj52 on 11/4/2016.
 */
@Entity
@Table(name="filesystem")
public class FileSystemBean {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }




    private String id;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name="name")
    @Id
    private String name;

    @Transient
    public List<CifsShareBean> getCifsShares() {
        return cifsShares;
    }

    public void setCifsShares(List<CifsShareBean> cifsShares) {
        this.cifsShares = cifsShares;
    }

    //@OneToMany(fetch=FetchType.LAZY)
    //@JoinColumn(name="filesystem")
    @Transient
    private List<CifsShareBean> cifsShares;


    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    //@Embedded
    @Transient
    private Address address;


    @Transient
    public Set<Address> getAddressList() {
        return addressList;
    }

    public void setAddressList(Set<Address> addressList) {
        this.addressList = addressList;
    }

    @Transient
    private Set<Address> addressList;






}
