package com.jichao.hibernate.query;

import com.jichao.entity.*;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.transform.Transformers;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by zhangj52 on 11/4/2016.
 */
public class QueryTest {

    private static void prepareData() {
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

            //Create Pool
            List<PoolBean> pools = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                PoolBean poolBean = new PoolBean();
                pools.add(poolBean);
                poolBean.setName("StorageResource_Name_" + i);
                poolBean.setId("pool_" + i);
                session.persist(poolBean);
                session.flush();
            }

            //Create 10 storageResource
            for (int i = 0; i < 10; i++) {
                StorageResourceBean storageResourceBean = new StorageResourceBean();
                storageResourceBean.setName("StorageResource_Name_" + i);
                //query filesystem
                FileSystemBean fileSystemBean = (FileSystemBean) session.get(FileSystemBean.class, "fs_" + i);
                storageResourceBean.setFileSystemBean(fileSystemBean);
                storageResourceBean.setId("sr_" + i);
                if (i == 0) {
                    storageResourceBean.setPools(pools);
                }
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
    }

    private static void prepareData(List<Object> entities) {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = null;
        Transaction tx = null;
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            for(Object obj : entities){
                session.persist(obj);
            }
            session.flush();
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            session.close();
        }
    }

    @Test
    public void testScrollResult() {
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
                FileSystemBean fileSystemBean = (FileSystemBean) session.get(FileSystemBean.class, "fs_" + i);
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
                StorageResourceBean storageResourceBean = (StorageResourceBean) obj;
                String name = storageResourceBean.getName();
                FileSystemBean fileSystemBean = storageResourceBean.getFileSystemBean();
                fileSystemBean.getName();//This call will trigger lazy fetch.
                System.err.println(name);
            }

        } catch (Exception e) {
            session.close();
        }
    }

    @Test
    public void testOneToMany() {

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

            //Create Pool
            List<PoolBean> pools = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                PoolBean poolBean = new PoolBean();
                pools.add(poolBean);
                poolBean.setName("StorageResource_Name_" + i);
                poolBean.setId("pool_" + i);
                session.persist(poolBean);
                session.flush();
            }

            //Create 10 storageResource
            for (int i = 0; i < 10; i++) {
                StorageResourceBean storageResourceBean = new StorageResourceBean();
                storageResourceBean.setName("StorageResource_Name_" + i);
                //query filesystem
                FileSystemBean fileSystemBean = (FileSystemBean) session.get(FileSystemBean.class, "fs_" + i);
                storageResourceBean.setFileSystemBean(fileSystemBean);
                storageResourceBean.setId("sr_" + i);
                if (i == 0) {
                    storageResourceBean.setPools(pools);
                }
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
            session.setDefaultReadOnly(true);
            List objs = query.list();
            for (Object obj : objs) {
                StorageResourceBean storageResourceBean = (StorageResourceBean) obj;
                String name = storageResourceBean.getName();
                FileSystemBean fileSystemBean = storageResourceBean.getFileSystemBean();
                fileSystemBean.getName();//This call will trigger lazy fetch.
                List<PoolBean> pools = storageResourceBean.getPools();
                System.err.println(pools);
                System.err.println(name);
            }

        } catch (Exception e) {
            session.close();
        }
    }

    @Test
    public void testOneToManyVersion2() {
        List<Object> objects = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            FileSystemBean fileSystemBean = new FileSystemBean();
            fileSystemBean.setName("First" + i);
            fileSystemBean.setId("fs_" + i);
            Address address = new Address("street", "city", "state", "zipcode");
            fileSystemBean.setAddress(address);
            objects.add(fileSystemBean);
        }

        for (int i = 0; i < 10; i++) {
            CifsShareBean cifsShareBean = new CifsShareBean();
            cifsShareBean.setName("CifsShare" + i);
            cifsShareBean.setId("cifs_" + i);
            FileSystemBean fileSystemBean = new FileSystemBean();
            fileSystemBean.setId("fs_"+i);
            cifsShareBean.setFilesystem(fileSystemBean);

            objects.add(cifsShareBean);
        }

        prepareData(objects);

        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("from FileSystemBean fs");
        List objs = query.list();
        try {
            for (Object obj : objs) {
                FileSystemBean fileSystemBean = (FileSystemBean) obj;
                String name = fileSystemBean.getName();
                System.err.println(name);
            }
        } catch (Exception e) {
            session.close();
        }
    }


    @Test
    public void testCollectionOnStatlessSessionNetgativeCase() {
        prepareData();
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        StatelessSession session = sessionFactory.openStatelessSession();
        Query query = session.createQuery("from StorageResourceBean sr");
        List objs = query.list();
        try {
            for (Object obj : objs) {
                StorageResourceBean storageResourceBean = (StorageResourceBean) obj;
                String name = storageResourceBean.getName();
                FileSystemBean fileSystemBean = storageResourceBean.getFileSystemBean();
                fileSystemBean.getName();//This trigger exception
                List<PoolBean> pools = storageResourceBean.getPools();
                System.err.println(pools);
                System.err.println(name);
            }
            fail("There should be exception: stateless session cannot support proxy!");
        } catch (Exception e) {
            session.close();
        }
    }

    @Test
    public void testScrollableAndCollection() {

        prepareData();
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("from StorageResourceBean sr");
        query.setMaxResults(3);
        query.setFirstResult(3);
        ScrollableResults scrollableResults = query.scroll();

        while (scrollableResults.next()) {
            Object[] objs = scrollableResults.get();
            System.out.println(objs.length);
            for (Object obj : objs) {
                System.out.println(obj);
                StorageResourceBean storageResourceBean = (StorageResourceBean)obj;
                //Scrollable Result lead to disabling of batch fetch.
                System.out.println(((StorageResourceBean) obj).getFileSystemBean());
            }
        }
    }


    @Test
    public void testPaging(){
        prepareData();
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("from StorageResourceBean sr");
        query.setMaxResults(3);
        query.setFirstResult(3);
        List objs = query.list();
        try {
            for (Object obj : objs) {
                StorageResourceBean storageResourceBean = (StorageResourceBean) obj;
                String name = storageResourceBean.getName();
                FileSystemBean fileSystemBean = storageResourceBean.getFileSystemBean();
                fileSystemBean.getName();//This trigger exception
                List<PoolBean> pools = storageResourceBean.getPools();
                System.err.println(pools);
                System.err.println(name);
            }
        } catch (Exception e) {
            session.close();
        }
    }

    @Test
    public void testEmbeddable() {
        List<Object> objects = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            FileSystemBean fileSystemBean = new FileSystemBean();
            fileSystemBean.setName("First" + i);
            fileSystemBean.setId("fs_" + i);
            Address address = new Address("street", "city", "state", "zipcode");
            fileSystemBean.setAddress(address);
            objects.add(fileSystemBean);
        }

        for (int i = 0; i < 10; i++) {
            CifsShareBean cifsShareBean = new CifsShareBean();
            cifsShareBean.setName("CifsShare" + i);
            cifsShareBean.setId("cifs_" + i);
            FileSystemBean fileSystemBean = new FileSystemBean();
            fileSystemBean.setId("fs_"+i);
            cifsShareBean.setFilesystem(fileSystemBean);

            objects.add(cifsShareBean);
        }

        prepareData(objects);

        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("from FileSystemBean fs");
        List objs = query.list();
        try {
            for (Object obj : objs) {
                FileSystemBean fileSystemBean = (FileSystemBean) obj;
                String name = fileSystemBean.getName();
                Address address = fileSystemBean.getAddress();
                System.err.println(name);
            }
        } catch (Exception e) {
            session.close();
        }
    }

    @Test
    public void testEmbeddableList() {
        List<Object> objects = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            FileSystemBean fileSystemBean = new FileSystemBean();
            fileSystemBean.setName("First" + i);
            fileSystemBean.setId("fs_" + i);
            Address address = new Address("street", "city", "state", "zipcode");
            fileSystemBean.setAddress(address);
            Address addressList = new Address("streetList", "cityList", "stateList", "zipcode");
            List<Address> addresses = new ArrayList<>();
            addresses.add(addressList);
            fileSystemBean.setAddressList(new HashSet<>(addresses));
            objects.add(fileSystemBean);
        }

        for (int i = 0; i < 10; i++) {
            CifsShareBean cifsShareBean = new CifsShareBean();
            cifsShareBean.setName("CifsShare" + i);
            cifsShareBean.setId("cifs_" + i);
            FileSystemBean fileSystemBean = new FileSystemBean();
            fileSystemBean.setId("fs_"+i);
            cifsShareBean.setFilesystem(fileSystemBean);
            objects.add(cifsShareBean);
        }

        prepareData(objects);

        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("from FileSystemBean fs");
        List objs = query.list();
        try {
            for (Object obj : objs) {
                FileSystemBean fileSystemBean = (FileSystemBean) obj;
                String name = fileSystemBean.getName();
                fileSystemBean.getCifsShares();
                fileSystemBean.getAddressList();
                Address address = fileSystemBean.getAddress();
                System.err.println(name);
            }
        } catch (Exception e) {
            session.close();
        }
    }

    @Test
    public void testEvict() {
        List<Object> objects = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            FileSystemBean fileSystemBean = new FileSystemBean();
            fileSystemBean.setName("First" + i);
            fileSystemBean.setId("fs_" + i);
            Address address = new Address("street", "city", "state", "zipcode");
            fileSystemBean.setAddress(address);
            Address addressList = new Address("streetList", "cityList", "stateList", "zipcode");
            List<Address> addresses = new ArrayList<>();
            addresses.add(addressList);
            fileSystemBean.setAddressList(new HashSet<>(addresses));
            objects.add(fileSystemBean);
        }

        for (int i = 0; i < 10; i++) {
            CifsShareBean cifsShareBean = new CifsShareBean();
            cifsShareBean.setName("CifsShare" + i);
            cifsShareBean.setId("cifs_" + i);
            FileSystemBean fileSystemBean = new FileSystemBean();
            fileSystemBean.setId("fs_"+i);
            cifsShareBean.setFilesystem(fileSystemBean);
            objects.add(cifsShareBean);
        }

        prepareData(objects);

        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        Query query = session.createQuery("from FileSystemBean fs");
        List objs = query.list();
        try {
            for (Object obj : objs) {
                FileSystemBean fileSystemBean = (FileSystemBean) obj;
                String name = fileSystemBean.getName();
                fileSystemBean.getCifsShares();
                fileSystemBean.getAddressList();
                Address address = fileSystemBean.getAddress();
                System.err.println(name);
                session.evict(fileSystemBean);
                System.err.println(name);
            }
        } catch (Exception e) {
            session.close();
        }
    }

}
