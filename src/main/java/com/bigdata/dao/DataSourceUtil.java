package com.bigdata.dao;

import com.bigdata.domian.Record;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.io.Serializable;
import java.util.Date;


/**
 * Created with IDEA by User1071324110@qq.com
 *
 * @author 10713
 * @date 2018/7/10 15:03
 */
public class DataSourceUtil implements Serializable {

    private static final long serialVersionUID = 2171472216249633806L;
    private static Logger logger = Logger.getLogger(DataSourceUtil.class);
    private static DataSource dataSource;

    static {
        dataSource = new ComboPooledDataSource("logMonitor");
    }
    public static synchronized DataSource getDataSource() {
        if (dataSource == null) {
            dataSource = new ComboPooledDataSource();
        }
        return dataSource;
    }

    public static void main(String[] args) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        Record record = new Record();
        record.setAppId(22223);
        record.setIsEmail(222);
        record.setIsPhone(222);
        record.setIsColse(22);
        String sql = "INSERT INTO `log_monitor`.`log_monitor_rule_record`" +
                " (`appId`,`ruleId`,`isEmail`,`isPhone`,`isColse`,`noticeInfo`,`updataDate`) " +
                "VALUES ( ?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql, record.getAppId(), record.getRuleId(), record.getIsEmail(), record.getIsPhone(), 0, record.getLine(),new Date());
    }


}
