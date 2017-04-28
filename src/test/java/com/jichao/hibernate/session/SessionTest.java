package com.jichao.hibernate.session;

import com.jichao.entity.FileSystemBean;
import com.jichao.entity.StorageResourceBean;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

import java.util.List;

/**
 * Created by zhangj52 on 12/15/2016.
 */
public class SessionTest {
    @Test
    public void testSessionEvict() {


        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = null;
        Transaction tx = null;
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();

            //Create 10 file system.

            for (int i = 0; i < 10; i++) {
                FileSystemBean fileSystemBean = new FileSystemBean();
                fileSystemBean.setName("First" + i);
                fileSystemBean.setId("fs_" + i);
                session.persist(fileSystemBean);
                session.flush();
            }

            //Create 10 storageResource
            for (int i = 0; i < 10; i++) {
                StorageResourceBean storageResourceBean = new StorageResourceBean();
                storageResourceBean.setName("StorageResource_Name_" + i);
                //query filesystem
                FileSystemBean fileSystemBean = (FileSystemBean) session.get(FileSystemBean.class, "fs_"+i);
                storageResourceBean.setFileSystemBean(fileSystemBean);
                storageResourceBean.setId("sr_" + i);
                session.persist(storageResourceBean);
                session.flush();
            }
            tx.commit();

        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            session.close();
        }

        //Query:
        try {
            session = sessionFactory.openSession();
            Criteria criteria = session.createCriteria(StorageResourceBean.class);
            //ResultTransformer resultTransformer = Transformers.ALIAS_TO_ENTITY_MAP;
            //query.setResultTransformer(resultTransformer);
            criteria.setFetchMode("fileSystemBean", FetchMode.JOIN);
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);

            List objs = criteria.list();
            for (Object obj : objs) {
                StorageResourceBean storageResourceBean = (StorageResourceBean)obj;
                FileSystemBean bean = storageResourceBean.getFileSystemBean();
                bean.getName();
            }

            int size = objs.size();
            Object obj = null;
            for(int i = 0; i < size; i ++) {
                obj = objs.get(0);
                System.out.println(obj);
                if(i != 0 && i%2 == 0){
                    //Reset as null [i-100, i)
                    for(int j = i-2; j < i; j++) {
                        session.evict(objs.get(j));
                    }
                }
            }

        } catch (Exception e) {
            session.close();
        }


    }
}
