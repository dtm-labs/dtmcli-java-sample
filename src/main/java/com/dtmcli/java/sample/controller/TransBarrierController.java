package com.dtmcli.java.sample.controller;

import barrier.BranchBarrier;
import com.mysql.cj.jdbc.MysqlDataSource;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api")
@Slf4j
public class TransBarrierController {
    
    Logger logger = LoggerFactory.getLogger(TransBarrierController.class);
    
    @RequestMapping("barrierTransOutTry")
    public Map<String, String> TransOutTry(@RequestBody(required = false) BranchBarrier branchBarrier)
            throws SQLException {
        logger.info("barrierTransOutTry branchBarrier:{}", branchBarrier);
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUser("root");
        mysqlDataSource
                .setURL("jdbc:mysql://localhost:3306/dtm_barrier?useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=UTC");
        mysqlDataSource.setPassword("12345678");
        Connection connection = mysqlDataSource.getConnection();
        branchBarrier.call(connection, () -> {
            System.out.println("转出事务");
            return true;
        });
        Map<String, String> result = new HashMap<>();
        result.put("dtm_result", "SUCCESS");
        return result;
    }
    
    @RequestMapping("barrierTransOutConfirm")
    public Map<String, String> TransOutConfirm(@RequestBody(required = false) BranchBarrier branchBarrier)
            throws SQLException {
        logger.info("barrierTransOutConfirm branchBarrier:{}", branchBarrier);
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUser("root");
        mysqlDataSource
                .setURL("jdbc:mysql://localhost:3306/dtm_barrier?useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=UTC");
        mysqlDataSource.setPassword("12345678");
        Connection connection = mysqlDataSource.getConnection();
        branchBarrier.call(connection, () -> {
            System.out.println("转出事务提交");
            return true;
        });
        Map<String, String> result = new HashMap<>();
        result.put("dtm_result", "SUCCESS");
        return result;
    }
    
    @RequestMapping("barrierTransOutCancel")
    public Map<String, String> TransOutCancel(@RequestBody(required = false) BranchBarrier branchBarrier)
            throws SQLException {
        logger.info("barrierTransOutCancel branchBarrier:{}", branchBarrier);
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUser("root");
        mysqlDataSource
                .setURL("jdbc:mysql://localhost:3306/dtm_barrier?useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=UTC");
        mysqlDataSource.setPassword("12345678");
        Connection connection = mysqlDataSource.getConnection();
        branchBarrier.call(connection, () -> {
            System.out.println("转出事务回滚");
            return true;
        });
        Map<String, String> result = new HashMap<>();
        result.put("dtm_result", "SUCCESS");
        return result;
    }
    
    @RequestMapping("barrierTransInTry")
    public Map<String, String> TransInTry(@RequestBody(required = false) BranchBarrier branchBarrier)
            throws SQLException {
        logger.info("barrierTransInTry branchBarrier:{}", branchBarrier);
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUser("root");
        mysqlDataSource
                .setURL("jdbc:mysql://localhost:3306/dtm_barrier?useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=UTC");
        mysqlDataSource.setPassword("12345678");
        Connection connection = mysqlDataSource.getConnection();
        branchBarrier.call(connection, () -> {
            System.out.println("转入事务");
            return true;
        });
        Map<String, String> result = new HashMap<>();
        result.put("dtm_result", "SUCCESS");
        return result;
    }
    
    @RequestMapping("barrierTransInConfirm")
    public Map<String, String> TransInConfirm(@RequestBody BranchBarrier branchBarrier) throws SQLException {
        logger.info("barrierTransInConfirm bTransInCancelranchBarrier:{}", branchBarrier);
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUser("root");
        mysqlDataSource
                .setURL("jdbc:mysql://localhost:3306/dtm_barrier?useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=UTC");
        mysqlDataSource.setPassword("12345678");
        Connection connection = mysqlDataSource.getConnection();
        branchBarrier.call(connection, () -> {
            System.out.println("转入事务提交");
            return true;
        });
        Map<String, String> result = new HashMap<>();
        result.put("dtm_result", "SUCCESS");
        return result;
    }
    
    @RequestMapping("barrierTransInCancel")
    public Map<String, String> TransInCancel(@RequestBody(required = false) BranchBarrier branchBarrier)
            throws SQLException {
        logger.info("barrierTransInCancel branchBarrier:{}", branchBarrier);
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUser("root");
        mysqlDataSource
                .setURL("jdbc:mysql://localhost:3306/dtm_barrier?useUnicode=true&characterEncoding=utf-8&useSSL=true&serverTimezone=UTC");
        mysqlDataSource.setPassword("12345678");
        Connection connection = mysqlDataSource.getConnection();
        branchBarrier.call(connection, () -> {
            System.out.println("转入事务回滚");
            return true;
        });
        Map<String, String> result = new HashMap<>();
        result.put("dtm_result", "SUCCESS");
        return result;
    }
}