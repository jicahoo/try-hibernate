package com.jichao.hibernate.query.mapping.onetoone.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "t_address")
public class Address {

    @Id
    @Column(name = "id")
    private String id;
    public String getId() {
        return id;
    }

    @Column(name="country")
    private String country;


    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
