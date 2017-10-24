package com.jichao.hibernate.query.hql.case_single_count.model;

import com.jichao.entity.Address;
import com.jichao.entity.CifsShareBean;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;
import java.util.Set;

/**
 * Created by zhangj52 on 4/28/2017.
 */
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


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "name")
    @Type(type="abc", parameters = {@Parameter(name="a",value="hello")})
    private String name;
}
