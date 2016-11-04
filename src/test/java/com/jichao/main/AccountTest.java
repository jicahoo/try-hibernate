package com.jichao.main;

import com.jichao.entity.Account;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by zhangj52 on 11/4/2016.
 */
public class AccountTest {

    @Test
    public void createAndQuery() {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = null;
        Transaction tx = null;
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            Account account = new Account();
            account.setName("First");
            account.setId("account_1");
            session.persist(account);
            session.flush();
            Query query = session.createQuery("from Account");
            List accounts = query.list();
            System.err.println(accounts.size());
            for (Object obj : accounts) {
                Account oneAccount = (Account) obj;
                assertEquals("First", account.getName());
                assertEquals("account_1", account.getId());
            }
            //tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }finally {
            session.close();
            tx.rollback();
        }
    }

    @Test
    public void queryIdAsString() {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = null;
        Transaction tx = null;
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            Account account = new Account();
            account.setName("First");
            account.setId("account_2");
            session.persist(account);
            session.flush();
            Query query = session.createQuery("select account.id from Account account");
            List accounts = query.list();
            System.err.println(accounts.size());
            assertTrue(accounts.contains("account_2"));
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            session.close();
            tx.rollback();
        }
    }


    public void testResultMapTransformer() {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = null;
        Transaction tx = null;
        try {
            session = sessionFactory.openSession();
            tx = session.beginTransaction();
            Account account = new Account();
            account.setName("First");
            account.setId("account_2");
            session.persist(account);
            session.flush();
            Query query = session.createQuery("select account.id from Account account");
            List accounts = query.list();
            System.err.println(accounts.size());
            assertTrue(accounts.contains("account_2"));
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            session.close();
            tx.rollback();
        }
    }



}
