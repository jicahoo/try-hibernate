package com.jichao.hibernate.query.mapping.onetoone.model;

import javax.persistence.*;

@Entity
@Table(name = "t_user")
public class User {

    @Id
    @Column(name = "id")
    private String id;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="address_id")
    private Address address;

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }


}
