package com.jichao.entity;

import javax.persistence.*;

/**
 * Created by zhangj52 on 11/4/2016.
 */
@Entity
@Table(name="storageResource")
public class StorageResourceBean {

    public FileSystemBean getFileSystemBean() {
        return fileSystemBean;
    }

    public void setFileSystemBean(FileSystemBean fileSystemBean) {
        this.fileSystemBean = fileSystemBean;
    }

    @OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="filesystem")
    private FileSystemBean fileSystemBean;

    public String getFileSystemId() {
        return fileSystemId;
    }

    public void setFileSystemId(String fileSystemId) {
        this.fileSystemId = fileSystemId;
    }

    @Column(name="filesystem", insertable = false, updatable = false)
    private String fileSystemId;

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


}
