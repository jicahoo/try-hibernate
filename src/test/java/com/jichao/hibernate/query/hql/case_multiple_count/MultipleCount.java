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
import java.util.Map;

import static org.junit.Assert.assertEquals;
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

            String hql = "SELECT host.id AS host_id, host.name AS host_name, count(DISTINCT iscsi.id) AS iscsi_count, " +
                    "count(DISTINCT fc.id) AS fc_count FROM com.jichao.hibernate.query.hql.case_multiple_count.model.Host host " +
                    "LEFT OUTER JOIN host.iscsiHostInitiators AS iscsi " +
                    "LEFT OUTER JOIN host.fcHostInitiators fc " +
                    "GROUP BY host.id,host.name";

            Query query = session.createQuery(hql);
            query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

            List objs = query.list();
            for (Object obj : objs) {
                Map<String, Object> propMap = (Map<String, Object>)obj;
                String objId = (String)propMap.get("host_id");
                Object fcCount = propMap.get("fc_count");
                Object iscsiCount = propMap.get("iscsi_count");

                if (objId.equals("host_1")) {
                    assertEquals(1L, fcCount);
                    assertEquals(1L, iscsiCount);

                }
                if (objId.equals("host_2")) {
                    assertEquals(3L, fcCount);
                    assertEquals(1L, iscsiCount);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
            fail("Failed to query.");
        } finally {
            if (session != null) {
                session.close();
            }
        }

    }


}
