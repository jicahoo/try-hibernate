package com.jichao.hibernate.query.hql.case_multiple_count;

import org.hsqldb.cmdline.SqlTool;
import org.junit.Test;

import java.io.File;

/**
 * Created by zhangj52 on 4/28/2017.
 */
public class MultipleCount {

    @Test
    public void prePrepareData(){


        File f = new File(".");
        System.out.println(f.getAbsolutePath());

        String urlId = "mem";
        String baseDir = "com/jichao/hibernate/query/hql/case_multiple_count";
        String sqlTooRcFile = "C:\\Users\\zhangj52\\Documents\\GitHub\\try-hibernate\\src\\test\\java\\com\\jichao\\hibernate\\query\\hql\\case_multiple_count\\sqltool.rc";
        String sqlFile = "C:\\Users\\zhangj52\\Documents\\GitHub\\try-hibernate\\src\\test\\java\\com\\jichao\\hibernate\\query\\hql\\case_multiple_count\\sql\\create_table.sql";
        //java org.hsqldb.cmdline.SqlTool [--opt[=optval]...] urlid [file1.sql...]
        String cmdParas = "--rcFile " + sqlTooRcFile + " " + urlId + " " + sqlFile;
        String[] parss = new String[]{"--rcFile", sqlTooRcFile, urlId, sqlFile};
        SqlTool.main(parss);

    }
}
