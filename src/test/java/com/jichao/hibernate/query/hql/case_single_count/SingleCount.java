package com.jichao.hibernate.query.hql.case_single_count;

import com.jichao.hibernate.query.hql.case_single_count.model.FileSystem;
import com.jichao.hibernate.query.hql.case_single_count.model.Pool;
import com.jichao.hibernate.query.hql.case_single_count.model.StorageResource;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.transform.Transformers;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.fail;


/**
 * Created by zhangj52 on 4/28/2017.
 */
public class SingleCount {

    private String hibernateConfigXml = "com/jichao/hibernate/query/hql/case_single_count/hibernate.cfg.xml";


    @Before
    public void before() throws IOException {
        String dataPath = "mock_data/com/jichao/hibernate/query/hql/case_single_count";

        Files.list(Paths.get(dataPath)).forEach(f -> {
            try {
                Files.delete(f);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    @Test
    public void testCount() {

        SessionFactory sessionFactory = new Configuration().configure(hibernateConfigXml).buildSessionFactory();
        Session session = null;
        Transaction tx = null;
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();

            //Create 10 file system.

            for (int i = 0; i < 10; i++) {
                FileSystem fileSystemBean = new FileSystem();
                fileSystemBean.setName("First" + i);
                fileSystemBean.setId("fs_" + i);
                session.persist(fileSystemBean);
                session.flush();
            }

            //Create Pool
            List<Pool> pools = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                Pool poolBean = new Pool();
                pools.add(poolBean);
                poolBean.setName("StorageResource_Name_" + i);
                poolBean.setId("pool_" + i);
                session.persist(poolBean);
                session.flush();
            }

            //Create 10 storageResource
            for (int i = 0; i < 10; i++) {
                StorageResource storageResourceBean = new StorageResource();
                storageResourceBean.setName("StorageResource_Name_" + i);
                //query filesystem
                FileSystem fileSystemBean = (FileSystem) session.get(FileSystem.class, "fs_" + i);
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
            fail("Failed to query");
        } finally {
            session.close();
        }

        //Query:
        try {
            session = sessionFactory.openSession();
            //String hql = "select sr.id as id, cast((count(pools_1)) as int) as poolcount from StorageResourceBean sr " +
            //       "left join sr.pools pools_1 group by sr.id order by id";
            //String hql = "select count(*) from StorageResourceBean sr";

            String hql = "select sr.id as id, cast(count(poolalias) as int) as test from StorageResource sr left outer join sr.pools as poolalias where id in ('sr_1', 'sr_2') group by sr.id";


            Query query = session.createQuery(hql);
            query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
            List objs = query.list();
            for (Object obj : objs) {
                System.out.println(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed to query.");
        } finally {
            session.close();
        }
    }
}
