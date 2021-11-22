package com.dtmcli.java.sample.controller;

import barrier.BarrierParam;
import barrier.BranchBarrier;
import com.mysql.cj.jdbc.MysqlDataSource;
import common.constant.Constant;
import common.model.TransResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.SQLException;

@RestController
@RequestMapping("api")
@Slf4j
public class TransBarrierController {
    
    Logger logger = LoggerFactory.getLogger(TransBarrierController.class);
    
    @Value("${spring.datasource.url}")
    private String url;
    
    @Value("${spring.datasource.username}")
    private String userName;
    
    @Value("${spring.datasource.password}")
    private String password;
    
    
    @RequestMapping("barrierTransOutTry")
    public TransResponse TransOutTry(@RequestBody BarrierParam barrierParam) throws SQLException {
        BranchBarrier branchBarrier = new BranchBarrier(barrierParam);
        logger.info("barrierTransOutTry branchBarrier:{}", branchBarrier);
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUser(userName);
        mysqlDataSource.setURL(url);
        mysqlDataSource.setPassword(password);
        Connection connection = mysqlDataSource.getConnection();
        branchBarrier.call(connection, () -> {
            System.out.println("转出准备");
            return true;
        });
        return TransResponse.buildTransResponse(Constant.SUCCESS_RESULT);
    }
    
    @RequestMapping("barrierTransOutConfirm")
    public TransResponse TransOutConfirm(@RequestBody BarrierParam barrierParam) throws SQLException {
        BranchBarrier branchBarrier = new BranchBarrier(barrierParam);
        logger.info("barrierTransOutConfirm branchBarrier:{}", branchBarrier);
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUser(userName);
        mysqlDataSource.setURL(url);
        mysqlDataSource.setPassword(password);
        Connection connection = mysqlDataSource.getConnection();
        branchBarrier.call(connection, () -> {
            System.out.println("转出提交");
            return true;
        });
        return TransResponse.buildTransResponse(Constant.SUCCESS_RESULT);
    }
    
    @RequestMapping("barrierTransOutCancel")
    public TransResponse TransOutCancel(@RequestBody BarrierParam barrierParam) throws SQLException {
        BranchBarrier branchBarrier = new BranchBarrier(barrierParam);
        logger.info("barrierTransOutCancel branchBarrier:{}", branchBarrier);
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUser(userName);
        mysqlDataSource.setURL(url);
        mysqlDataSource.setPassword(password);
        Connection connection = mysqlDataSource.getConnection();
        branchBarrier.call(connection, () -> {
            System.out.println("转出回滚");
            return true;
        });
        return TransResponse.buildTransResponse(Constant.SUCCESS_RESULT);
    }
    
    @RequestMapping("barrierTransInTry")
    public TransResponse TransInTry(@RequestBody BarrierParam barrierParam) throws SQLException {
        BranchBarrier branchBarrier = new BranchBarrier(barrierParam);
        logger.info("barrierTransInTry branchBarrier:{}", branchBarrier);
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUser(userName);
        mysqlDataSource.setURL(url);
        mysqlDataSource.setPassword(password);
        Connection connection = mysqlDataSource.getConnection();
        branchBarrier.call(connection, () -> {
            System.out.println("转入准备");
            return true;
        });
        return TransResponse.buildTransResponse(Constant.SUCCESS_RESULT);
    }
    
    @RequestMapping("barrierTransInConfirm")
    public TransResponse TransInConfirm(@RequestBody BarrierParam barrierParam) throws SQLException {
        BranchBarrier branchBarrier = new BranchBarrier(barrierParam);
        logger.info("barrierTransInConfirm TransInCancel branchBarrier:{}", branchBarrier);
        
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUser(userName);
        mysqlDataSource.setURL(url);
        mysqlDataSource.setPassword(password);
        Connection connection = mysqlDataSource.getConnection();
        branchBarrier.call(connection, () -> {
            System.out.println("转入提交");
            return true;
        });
        return TransResponse.buildTransResponse(Constant.SUCCESS_RESULT);
    }
    
    @RequestMapping("barrierTransInCancel")
    public TransResponse TransInCancel(@RequestBody BarrierParam barrierParam) throws SQLException {
        BranchBarrier branchBarrier = new BranchBarrier(barrierParam);
        logger.info("barrierTransInCancel branchBarrier:{}", branchBarrier);
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUser(userName);
        mysqlDataSource.setURL(url);
        mysqlDataSource.setPassword(password);
        Connection connection = mysqlDataSource.getConnection();
        branchBarrier.call(connection, () -> {
            System.out.println("转入回滚");
            return true;
        });
        return TransResponse.buildTransResponse(Constant.SUCCESS_RESULT);
    }
}