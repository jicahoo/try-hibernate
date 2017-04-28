package com.jichao.hibernate.query.hql.case_single_count.model;

import com.jichao.hibernate.CustomCollectionPersister;
import org.hibernate.annotations.Persister;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangj52 on 4/28/2017.
 */
@Entity
@Table(name = "storageResource")
public class StorageResource {


    public FileSystem getFileSystemBean() {
        return fileSystemBean;
    }

    public void setFileSystemBean(FileSystem fileSystemBean) {
        this.fileSystemBean = fileSystemBean;
    }

    @OneToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "filesystem")
    private FileSystem fileSystemBean;

    public String getFileSystemId() {
        return fileSystemId;
    }

    public void setFileSystemId(String fileSystemId) {
        this.fileSystemId = fileSystemId;
    }

    @Column(name = "filesystem", insertable = false, updatable = false)
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

    @Column(name = "name")
    private String name;

    @OneToMany(fetch = FetchType.LAZY)
    //@BatchSize(size=5)
    @Persister(impl = CustomCollectionPersister.class)
    @JoinTable(name = "storageResource_pools", joinColumns = {@JoinColumn(name = "id")},
            inverseJoinColumns = {@JoinColumn(name = "elementvalue")})
    protected List<Pool> pools = new ArrayList<>();

    public List<Pool> getPools() {
        return pools;
    }

    public void setPools(List<Pool> storageResource_pools) {
        this.pools = storageResource_pools;
    }
}

