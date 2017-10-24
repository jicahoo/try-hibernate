package com.jichao.hibernate.query.usertype.model;

import com.jichao.hibernate.query.mapping.onetoone.model.User;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.usertype.ParameterizedType;
import org.hibernate.usertype.UserType;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Date;
import java.util.Properties;


public class PlusOneUserType implements ParameterizedType, UserType {
    private static final int[] SQL_TYPES = {Types.OTHER};

    @Override
    public Object assemble(Serializable arg0, Object arg1)
            throws HibernateException {
        return arg0;
    }

    @Override
    public Object deepCopy(Object arg0) throws HibernateException {
        return arg0;
    }

    @Override
    public Serializable disassemble(Object arg0) throws HibernateException {
        return (Serializable) arg0;
    }

    @Override
    public boolean equals(Object arg0, Object arg1) throws HibernateException {
        return arg0 == arg1;
    }

    @Override
    public int hashCode(Object arg0) throws HibernateException {
        return arg0.hashCode();
    }

    @Override
    public boolean isMutable() {
        return false;
    }

    @Override
    public Object nullSafeGet(ResultSet resultSet, String[] names,
                              SessionImplementor session, Object owner) throws HibernateException,
            SQLException {
        Integer actualValue = resultSet.getInt(names[0]);
        int increase = 1;
        if ("minutes".equals(unitOfTime)) {
            increase = 60;
        }
        return actualValue + increase;
    }

    @Override
    public void nullSafeSet(PreparedStatement statement, Object value, int index,
                            SessionImplementor session) throws HibernateException, SQLException {
        if (value == null) {
            statement.setNull(index, Types.OTHER);
        } else {
            Date date = (Date) value;
//            statement.setObject(index, CIMDateTimeUtil.convertToCIMDateString(date), Types.OTHER);
        }
    }

    @Override
    public Object replace(Object arg0, Object arg1, Object arg2)
            throws HibernateException {
        return arg0;
    }

    @Override
    public Class<Integer> returnedClass() {
        return Integer.class;
    }

    @Override
    public int[] sqlTypes() {
        return SQL_TYPES;
    }

    private String unitOfTime;

    @Override
    public void setParameterValues(Properties parameters) {
        String property = parameters.getProperty("unit");
        unitOfTime = property;
    }
}
