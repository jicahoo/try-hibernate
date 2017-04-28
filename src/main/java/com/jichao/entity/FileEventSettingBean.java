package com.jichao.entity;

import javax.persistence.*;

/**
 * Created by zhangj52 on 11/8/2016.
 */
@Entity
@Table(name="file_event_setting")
public class FileEventSettingBean {


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


    public FileSystemBean getFileSystemBean() {
        return fileSystemBean;
    }

    public void setFileSystemBean(FileSystemBean fileSystemBean) {
        this.fileSystemBean = fileSystemBean;
    }

    @OneToOne
    @JoinColumn(name="fs_id")
    private FileSystemBean fileSystemBean;

}
