package com.jichao.main;

import com.jichao.entity.Account;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.Test;

import java.util.List;

/**
 * Created by zhangj52 on 11/4/2016.
 */
public class AccountTest {

    @Test
    public void testHibernate() {
        SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
        Session session = sessionFactory.openSession();
        Account account = new Account();
        account.setName("First");
        account.setId("account_1");
        session.persist(account);
        session.flush();
        Query query = session.createQuery("from Account");
        List accounts = query.list();
        System.err.println(accounts.size());
        for (Object obj : accounts) {
            Account oneAccount = (Account)obj;
            System.err.println(oneAccount.getId());
            System.err.println(oneAccount.getName());
        }
        session.close();
    }
}
