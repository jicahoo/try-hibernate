package com.jichao.hibernate.query.hql.case_multiple_count;

import com.jichao.hibernate.query.hql.case_multiple_count.model.Host;
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
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by zhangj52 on 4/28/2017.
 */
public class MultipleCount {

    @Test
    public void testTwoCount() throws SqlTool.SqlToolException, IOException {
//        File f = new File(".");
//        System.out.println(f.getAbsolutePath());

        File f = new File(".");
        System.out.println(f.getAbsolutePath());

        //Prepare data.
        String relativePath = "src/test/java/com/jichao/hibernate/query/hql/case_multiple_count";
        String path = relativePath.replace("/", File.separator);
        File cwd = new File(".");
        String caseBasePath = cwd.getCanonicalPath() + File.separator + path;
        System.out.println(caseBasePath);

        String sqlFileName = "testTwoCount.sql";
        String urlId = "personal";
        String sqlTooRcFile = caseBasePath + File.separator +"sqltoolTestTwoCount.rc";
        String sqlFile = caseBasePath + File.separator + "sql" + File.separator + sqlFileName;
        //java org.hsqldb.cmdline.SqlTool [--opt[=optval]...] urlid [file1.sql...]
        String[] sqlToolOptions = new String[]{"--rcFile", sqlTooRcFile, urlId, sqlFile};

        SqlTool.objectMain(sqlToolOptions);


        String hibernateConfigXml = "com/jichao/hibernate/query/hql/case_multiple_count/hibernateTestTwoCount.cfg.xml";

        SessionFactory sessionFactory = new Configuration().configure(hibernateConfigXml).buildSessionFactory();
        Session session = null;

        //Query: count(DISTINCT ...)
        try {
            session = sessionFactory.openSession();

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

        //Query: count(DISTINCT ...) Without DISTINCT
        try {
            session = sessionFactory.openSession();

            String hql = "SELECT host.id AS host_id, host.name AS host_name, count(iscsi.id) AS iscsi_count, " +
                    "count(fc.id) AS fc_count FROM com.jichao.hibernate.query.hql.case_multiple_count.model.Host host " +
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
                    assertEquals(3L, iscsiCount);
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

    @Test
    public void testComplexCase() throws SqlTool.SqlToolException, IOException {
        String relativePath = "src/test/java/com/jichao/hibernate/query/hql/case_multiple_count";
        String path = relativePath.replace("/", File.separator);
        File cwd = new File(".");
        String cwdPath = cwd.getCanonicalPath();
        System.err.println(cwdPath);
        String caseBasePath = cwd.getCanonicalPath() + File.separator + path;
        System.out.println(caseBasePath);
        System.out.println("CASE BASE PATH.");

        String sqlFileName = "testComplexCase.sql";
        String urlId = "personal";
        String sqlTooRcFile = caseBasePath + File.separator +"sqltool.rc";
        String sqlFile = caseBasePath + File.separator + "sql" + File.separator + sqlFileName;
        //java org.hsqldb.cmdline.SqlTool [--opt[=optval]...] urlid [file1.sql...]
        String[] sqlToolOptions = new String[]{"--rcFile", sqlTooRcFile, urlId, sqlFile};

        SqlTool.objectMain(sqlToolOptions);

        //Resource path
        String hibernateConfigXml = "/com/jichao/hibernate/query/hql/case_multiple_count/hibernate.cfg.xml";
        System.out.println("Before GET SessionFactory.");
        SessionFactory sessionFactory = new Configuration().configure(hibernateConfigXml).buildSessionFactory();
        Session session = null;
        System.out.println("Get SessionFactory");

        //Query:
        try {
            session = sessionFactory.openSession();


            String hql = "SELECT host.id AS host_id, host.name AS host_name, " +
                    "CASE WHEN host.type = 2 THEN (count(DISTINCT iscsi.id)+count(DISTINCT fc.id)) ELSE null END AS total_count " +
                    " FROM com.jichao.hibernate.query.hql.case_multiple_count.model.Host host " +
                    "LEFT OUTER JOIN host.iscsiHostInitiators AS iscsi " +
                    "LEFT OUTER JOIN host.fcHostInitiators fc " +
                    "GROUP BY host.id,host.name,host.type " +
                    "HAVING ((CASE WHEN host.type = 2 THEN (count(DISTINCT iscsi.id)+count(DISTINCT fc.id)) ELSE null END) > 1) " +
                    "ORDER BY total_count DESC";

            Query query = session.createQuery(hql);
            query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
            query.setFirstResult(1);
            query.setMaxResults(2);

            List objs = query.list();
            assertEquals(2, objs.size());


            for (Object obj : objs) {
                Map<String, Object> propMap = (Map<String, Object>)obj;
                String objId = (String)propMap.get("host_id");
                Object totalCount = propMap.get("total_count");
                assertTrue("host_3".equals(objId) || "host_4".equals(objId));

                System.out.println(propMap);
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
