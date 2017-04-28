package com.jichao.hibernate.query;

import com.jichao.entity.FileEventSettingBean;
import com.jichao.entity.FileSystemBean;
import com.jichao.entity.StorageResourceBean;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.DetachedCriteria;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * Reference: http://what-when-how.com/hibernate/advanced-query-options-hibernate/
 * Created by zhangj52 on 11/4/2016.
 */
public class CriteriaTest {

    @Test
    public void testCriteriaBasics() {


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
            criteria.setFetchMode("fileSystemBean", FetchMode.SELECT);
            criteria.setFetchSize(5);

            List objs = criteria.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
            for (Object obj : objs) {
                Map<String, Object> map = (Map<String, Object>)obj;
                System.out.println(obj);
                StorageResourceBean storageResourceBean = (StorageResourceBean)map.get("this");
                FileSystemBean bean = storageResourceBean.getFileSystemBean();
                bean.getName();
            }

        } catch (Exception e) {
            session.close();
        }


    }

    @Test
    public void testDetachedCriteria() {

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
            DetachedCriteria detachedCriteria = DetachedCriteria.forClass(StorageResourceBean.class);
            session = sessionFactory.openSession();
            Criteria criteria = detachedCriteria.getExecutableCriteria(session);
            List objs = criteria.list();
            for (Object obj : objs) {
                System.out.println(obj);
            }

        } catch (Exception e) {
            session.close();
        }
    }






}
