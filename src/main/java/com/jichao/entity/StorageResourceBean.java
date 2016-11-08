package com.jichao.entity;

import com.jichao.hibernate.CustomCollectionPersister;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.Persister;

import javax.persistence.*;
import java.util.List;

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

    @OneToOne(fetch=FetchType.LAZY, optional = true)
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

    @OneToMany(fetch=FetchType.LAZY)
    //@BatchSize(size=5)
    @Persister(impl= CustomCollectionPersister.class)
    @JoinTable(name="storageResource_pools", joinColumns= {@JoinColumn(name="id")},
            inverseJoinColumns={@JoinColumn(name="elementvalue")})
    protected List<PoolBean> pools;

    public List<PoolBean> getPools() {
        return pools;
    }
    public void setPools(List<PoolBean> storageResource_pools) {
        this.pools = storageResource_pools;
    }


}
