package com.jichao.hibernate.query.usertype;

import com.jichao.hibernate.query.usertype.model.FileSystem;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hsqldb.cmdline.SqlTool;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class TryUserType {


    @Test
    public void testUserType() throws SqlTool.SqlToolException, IOException {
        String relativePath = "src/test/java/com/jichao/hibernate/query/usertype";
        String path = relativePath.replace("/", File.separator);

        File cwd = new File(".");
        String cwdPath = cwd.getCanonicalPath();
        String caseBasePath = cwdPath + File.separator + path;
        String sqlFileName = "TryUserType.sql";
        String urlId = "personal";
        String sqlTooRcFile = caseBasePath + File.separator +"sqltool.rc";
        String sqlFile = caseBasePath + File.separator + "sql" + File.separator + sqlFileName;
        //java org.hsqldb.cmdline.SqlTool [--opt[=optval]...] urlid [file1.sql...]
        String[] sqlToolOptions = new String[]{"--rcFile", sqlTooRcFile, urlId, sqlFile};

        SqlTool.objectMain(sqlToolOptions);

        //Resource path
        String hibernateConfigXml = "/com/jichao/hibernate/query/usertype/hibernate.cfg.xml";

        SessionFactory sessionFactory = new Configuration().configure(hibernateConfigXml).buildSessionFactory();
        Session session = null;
        Transaction tx = null;

        try {
            session = sessionFactory.openSession();

            String hql = "FROM com.jichao.hibernate.query.usertype.model.FileSystem fs";

            Query query = session.createQuery(hql);

            List objs = query.list();
            assertEquals(1, objs.size());
            assertEquals(160, ((FileSystem)objs.get(0)).getStartTime().intValue());
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
