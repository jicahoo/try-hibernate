package com.jichao.entity;

import javax.persistence.*;
import java.util.List;

/**
 * Created by zhangj52 on 11/7/2016.
 */
@Entity
@Table(name="cifsShare")
public class CifsShareBean {

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

    @Column(name="name")
    private String name;


    public FileSystemBean getFilesystem() {
        return filesystem;
    }

    public void setFilesystem(FileSystemBean filesystem) {
        this.filesystem = filesystem;
    }

    @ManyToOne
    @JoinColumn(name="filesystem")
    private FileSystemBean filesystem;

}
