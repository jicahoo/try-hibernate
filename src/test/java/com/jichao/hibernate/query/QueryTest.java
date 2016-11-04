package com.jichao.hibernate.query;

import com.jichao.entity.Account;
import com.jichao.entity.FileSystemBean;
import com.jichao.entity.StorageResourceBean;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.transform.Transformers;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by zhangj52 on 11/4/2016.
 */
public class QueryTest {

    @Test public void testScrollResult() {
        //Prepare 1000 data
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = null;
        Transaction tx = null;
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            for (int i = 0; i < 1000; i++) {
                Account account = new Account();
                account.setName("First" + i);
                account.setId("account_" + i);
                session.persist(account);
                session.flush();
            }
            //tx.commit();
            tx.commit();

        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            session.close();
//            tx.rollback();
        }

        session = sessionFactory.openSession();
        Query query = session.createQuery("from Account");
        ScrollableResults scrollableResults = query.scroll();

        while (scrollableResults.next()) {
            Object[] objs = scrollableResults.get();
            System.out.println(objs);
        }
    }


    @Test
    public void testMapResultsStatelessSession() {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        StatelessSession statelessSession = sessionFactory.openStatelessSession();
        Query query = statelessSession.createQuery("from Account account");

        List results = query.list();
        System.err.println(results.size());
        for (Object obj : results) {
            System.out.println(obj);
        }
    }

    @Test
    public void testOneToOne() {

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
            Query query = session.createQuery("from StorageResourceBean sr");
            List objs = query.list();
            for (Object obj : objs) {
                StorageResourceBean storageResourceBean = (StorageResourceBean)obj;
                String name = storageResourceBean.getName();
                FileSystemBean fileSystemBean = storageResourceBean.getFileSystemBean();
                System.err.println(name);
            }

        } catch (Exception e) {
            session.close();
        }




    }

}
