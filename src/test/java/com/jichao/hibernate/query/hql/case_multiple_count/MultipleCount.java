package com.jichao.hibernate.query.hql.case_multiple_count;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.transform.Transformers;
import org.hsqldb.cmdline.SqlTool;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.junit.Assert.fail;

/**
 * Created by zhangj52 on 4/28/2017.
 */
public class MultipleCount {

    @Before
    public void prePrepareData() throws SqlTool.SqlToolException {


        File f = new File(".");
        System.out.println(f.getAbsolutePath());

        String urlId = "personal";
        String baseDir = "com/jichao/hibernate/query/hql/case_multiple_count";
        String sqlTooRcFile = "C:\\Users\\zhangj52\\Documents\\GitHub\\try-hibernate\\src\\test\\java\\com\\jichao\\hibernate\\query\\hql\\case_multiple_count\\sqltool.rc";
        String sqlFile = "C:\\Users\\zhangj52\\Documents\\GitHub\\try-hibernate\\src\\test\\java\\com\\jichao\\hibernate\\query\\hql\\case_multiple_count\\sql\\create_table.sql";
        //java org.hsqldb.cmdline.SqlTool [--opt[=optval]...] urlid [file1.sql...]
        String cmdParas = "--rcFile " + sqlTooRcFile + " " + urlId + " " + sqlFile;
        String[] parss = new String[]{"--rcFile", sqlTooRcFile, urlId, sqlFile};
        System.out.println("Here");

        SqlTool.objectMain(parss);

    }

    @Test
    public void test() throws InterruptedException {
        String hibernateConfigXml = "com/jichao/hibernate/query/hql/case_multiple_count/hibernate.cfg.xml";

        SessionFactory sessionFactory = new Configuration().configure(hibernateConfigXml).buildSessionFactory();
        Session session = null;
        String val = System.getProperty("user.home");
        System.out.println("Hello: " + val);

        //Query:
        try {
            session = sessionFactory.openSession();
            //String hql = "select sr.id as id, cast((count(pools_1)) as int) as poolcount from StorageResourceBean sr " +
            //       "left join sr.pools pools_1 group by sr.id order by id";
            //String hql = "select count(*) from StorageResourceBean sr";

            String hql = "select host.id, host.name from com.jichao.hibernate.query.hql.case_multiple_count.model.Host host";

            Query query = session.createQuery(hql);
            query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

            List objs = query.list();
            for (Object obj : objs) {
                System.out.println(obj.getClass());
            }

            System.out.println("Hello " + objs);

        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed to query.");
        } finally {
            session.close();
        }

    }


}
