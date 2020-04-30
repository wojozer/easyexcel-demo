package com.zwj.easyexcel.dao;


import com.zwj.easyexcel.data.ConfigFilterExport;
import com.zwj.easyexcel.data.ConfigFilterImport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @Author: zhengwj
 * @Description:    导入导出实体的分页查询和批量新增方法
 *
 * @Date: 2020/4/1 16:43
 * @Version: 1.0
 */
@Repository
public class ConfigFilterDao {

    private static final Logger logger = LoggerFactory.getLogger(ConfigFilterDao.class);

    @Value("${spring.datasource.url}")
    private String url ;
    @Value("${spring.datasource.username}")
    private String user;
    @Value("${spring.datasource.password}")
    private String password ;

    /**
     * 批量增加
     * @param list     大批量数据使用原生jdbc
     */
    //TODO
    public void save(List<ConfigFilterImport> list) {
        Connection conn = null;
        PreparedStatement pstm = null;
        ResultSet rt = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(url, user, password);
            String sql = "INSERT INTO TBL_CONFIG_FILTER............";   //插入sql
            pstm = conn.prepareStatement(sql);
            conn.setAutoCommit(false);
            Long startTime = System.currentTimeMillis();
            for(ConfigFilterImport dr : list){
                pstm.setString(1, UUID.randomUUID().toString().replace("-", ""));
                pstm.setString(2,"");
                pstm.setString(2,"");
                //.........
                pstm.addBatch();
            }
            pstm.executeBatch();
            conn.commit();
            Long endTime = System.currentTimeMillis();
            logger.info("用时：" + (endTime - startTime));
        } catch (Exception e) {
            logger.error("执行出错{}",e.getMessage());
            throw new RuntimeException(e);
        } finally {
            if (pstm != null) {
                try {
                    pstm.close();
                } catch (SQLException e) {
                    logger.error("执行出错{}",e.getMessage());
                    throw new RuntimeException(e);
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                } catch (SQLException e) {
                    logger.error("执行出错{}",e.getMessage());
                    throw new RuntimeException(e);
                }
            }
        }
    }

    //数据导出使用的分页查询方法  直接调用mybatis相应实体方法（sql优化）
    public List<ConfigFilterExport> selectConfigFilterPage(Map<String, Object> param) {

        //TODO
        return null;
    }
}
