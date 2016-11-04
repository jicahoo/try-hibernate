package com.jichao.main;

import com.jichao.entity.Account;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;
import org.hibernate.transform.Transformers;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by zhangj52 on 11/4/2016.
 */
public class QueryBasics {
    private static Session session = null;
    private static StatelessSession statelessSession = null;
    private static SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();



    @BeforeClass
    public static void prepareData() {
        session = sessionFactory.openSession();
        Transaction tx = session.beginTransaction();

        statelessSession = sessionFactory.openStatelessSession();
        Account account = new Account();
        account.setName("First");
        account.setId("account_1");
        session.persist(account);
        session.flush();
        tx.commit();
        session.close();
    }


    @Test
    public void testMapResultsNormalSession() {

        session = sessionFactory.openSession();
        Query query = session.createQuery("select account.id as id from Account account");
        query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
        List<Map<String, Object>> results = query.list();
        System.err.println(results.size());
        for (Map<String, Object> obj : results) {
            assertEquals(null, obj.get("name"));
            assertEquals("account_1", obj.get("id"));
        }
        session.close();
    }


    @AfterClass
    public static void cleanData() {

    }

}
