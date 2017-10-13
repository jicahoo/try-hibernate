package com.jichao.hibernate.query.mapping.onetoone;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.transform.Transformers;
import org.hsqldb.cmdline.SqlTool;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class OneToOneCases {


    @Test
    public void testOneToOne() throws SqlTool.SqlToolException, IOException {
        File f = new File(".");
        System.out.println(f.getAbsolutePath());
        String pkgPath = "com/jichao/hibernate/query/mapping/onetoone";

        //Prepare data.
        String relativePath = "src/test/java/" + pkgPath;
        String path = relativePath.replace("/", File.separator);
        File cwd = new File(".");
        String caseBasePath = cwd.getCanonicalPath() + File.separator + path;
        System.out.println(caseBasePath);

        String sqlFileName = "SchemaAndData.sql";
        String urlId = "personal";
        String sqlTooRcFile = caseBasePath + File.separator +"sqltool.rc";
        String sqlFile = caseBasePath + File.separator + "sql" + File.separator + sqlFileName;
        //java org.hsqldb.cmdline.SqlTool [--opt[=optval]...] urlid [file1.sql...]
        String[] sqlToolOptions = new String[]{"--rcFile", sqlTooRcFile, urlId, sqlFile};

        SqlTool.objectMain(sqlToolOptions);



        String hibernateConfigXml = pkgPath + "/hibernate.cfg.xml";

        SessionFactory sessionFactory = new Configuration().configure(hibernateConfigXml).buildSessionFactory();
        Session session = null;

        //Query: count(DISTINCT ...)
        try {
            session = sessionFactory.openSession();

            String hql = "SELECT user.id as user_id, address.country AS country FROM com.jichao.hibernate.query.mapping.onetoone.model.User user LEFT OUTER JOIN user.address AS address";
            hql = "FROM com.jichao.hibernate.query.mapping.onetoone.model.User user LEFT OUTER JOIN user.address AS address";

            Query query = session.createQuery(hql);
            // query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

            List objs = query.list();

            System.out.println("OK");

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
