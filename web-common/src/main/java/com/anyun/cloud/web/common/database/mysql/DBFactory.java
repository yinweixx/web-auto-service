package com.anyun.cloud.web.common.database.mysql;

import com.iciql.Db;
import org.apache.commons.dbcp2.BasicDataSource;

public class DBFactory {
    private static final String DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";
    public static final String VALIDATAION_SQL = "select 1=1";
    public static final int DEFAULT_INIT_SIZE = 10;
    public static final int DEFAULT_MAX_SIZE = 50;
    public static final int DEFAULT_VALIDATAION_TIMEOUT = 60 * 10;
    private static DBFactory factory;
    private Db db;

    public DBFactory(){}

    public static DBFactory getFactory(){
        synchronized (DBFactory.class){
            if(factory == null)
                factory = new DBFactory();
        }
        return factory;
    }

    public DBFactory init(String url,String user,String passwd){
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName(DRIVER_CLASS_NAME);
        ds.setValidationQuery(VALIDATAION_SQL);
        ds.setValidationQueryTimeout(DEFAULT_VALIDATAION_TIMEOUT);
        ds.setInitialSize(DEFAULT_INIT_SIZE);
        ds.setMaxTotal(DEFAULT_MAX_SIZE);
        ds.setUrl(url);
        ds.setUsername(user);
        ds.setPassword(passwd);
        db = Db.open(ds);
        return this;
    }

    public Db getDb(){
        return db;
    }
}
