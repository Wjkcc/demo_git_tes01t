package com.demo.utils;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;


/**
 * @author Joe
 * @version 1.0
 * @date 2019/11/27 13:47
 */
@Component
public class ConnectionUtils {
    // 当前线程上的连接
    private ThreadLocal<Connection> tl = new ThreadLocal<Connection>();
    @Autowired
    @Qualifier("dataSource")
    private DataSource dataSource;



    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * 获取当前线程上的数据库连接
     * @return
     */
    public Connection getThreadConnection(){
        try{
            // 对当前线程连接中获得连接
            Connection conn = tl.get();
            if(conn == null){
                // 数据源获取连接
                conn = dataSource.getConnection();
                // 存入当前线程中
                tl.set(conn);
            }
            // 返回当前线程连接
            return conn;
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
    // 移除本地连接
    public void removeConnection(){
        tl.remove();
    }
}
