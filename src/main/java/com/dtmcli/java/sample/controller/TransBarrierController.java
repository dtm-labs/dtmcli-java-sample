package com.dtmcli.java.sample.controller;

import barrier.BranchBarrier;
import com.mysql.cj.jdbc.MysqlDataSource;
import common.constant.Constant;
import common.model.TransResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.sql.Connection;

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
    public Object TransOutTry(HttpServletRequest request) throws Exception {
        
        BranchBarrier branchBarrier = new BranchBarrier(request.getInputStream());
        logger.info("barrierTransOutTry branchBarrier:{}", branchBarrier);
        
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUser(userName);
        mysqlDataSource.setURL(url);
        mysqlDataSource.setPassword(password);
        Connection connection = mysqlDataSource.getConnection();
        
        branchBarrier.call(connection, (barrier) -> {
            System.out.println("转出准备");
        });
        
        connection.close();
        return TransResponse.buildTransResponse(Constant.SUCCESS_RESULT);
    }
    
    @RequestMapping("barrierTransOutConfirm")
    public Object TransOutConfirm(HttpServletRequest request) throws Exception {
        BranchBarrier branchBarrier = new BranchBarrier(request.getInputStream());
        logger.info("barrierTransOutConfirm branchBarrier:{}", branchBarrier);
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUser(userName);
        mysqlDataSource.setURL(url);
        mysqlDataSource.setPassword(password);
        Connection connection = mysqlDataSource.getConnection();
        branchBarrier.call(connection, (barrier) -> {
            System.out.println("转出提交");
        });
        return TransResponse.buildTransResponse(Constant.SUCCESS_RESULT);
    }
    
    @RequestMapping("barrierTransOutCancel")
    public Object TransOutCancel(HttpServletRequest request) throws Exception {
        BranchBarrier branchBarrier = new BranchBarrier(request.getInputStream());
        logger.info("barrierTransOutCancel branchBarrier:{}", branchBarrier);
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUser(userName);
        mysqlDataSource.setURL(url);
        mysqlDataSource.setPassword(password);
        Connection connection = mysqlDataSource.getConnection();
        branchBarrier.call(connection, (barrier) -> {
            System.out.println("转出回滚");
        });
        return TransResponse.buildTransResponse(Constant.SUCCESS_RESULT);
    }
    
    @RequestMapping("barrierTransInTry")
    public Object TransInTry(HttpServletRequest request) throws Exception {
        BranchBarrier branchBarrier = new BranchBarrier(request.getInputStream());
        logger.info("barrierTransInTry branchBarrier:{}", branchBarrier);
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUser(userName);
        mysqlDataSource.setURL(url);
        mysqlDataSource.setPassword(password);
        Connection connection = mysqlDataSource.getConnection();
        branchBarrier.call(connection, (barrier) -> {
            System.out.println("转入准备");
        });
        return TransResponse.buildTransResponse(Constant.SUCCESS_RESULT);
    }
    
    @RequestMapping("barrierTransInConfirm")
    public Object TransInConfirm(HttpServletRequest request) throws Exception {
        BranchBarrier branchBarrier = new BranchBarrier(request.getInputStream());
        logger.info("barrierTransInConfirm TransInCancel branchBarrier:{}", branchBarrier);
        
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUser(userName);
        mysqlDataSource.setURL(url);
        mysqlDataSource.setPassword(password);
        Connection connection = mysqlDataSource.getConnection();
        branchBarrier.call(connection, (barrier) -> {
            System.out.println("转入提交");
        });
        return TransResponse.buildTransResponse(Constant.SUCCESS_RESULT);
    }
    
    @RequestMapping("barrierTransInCancel")
    public Object TransInCancel(HttpServletRequest request) throws Exception {
        BranchBarrier branchBarrier = new BranchBarrier(request.getInputStream());
        logger.info("barrierTransInCancel branchBarrier:{}", branchBarrier);
        MysqlDataSource mysqlDataSource = new MysqlDataSource();
        mysqlDataSource.setUser(userName);
        mysqlDataSource.setURL(url);
        mysqlDataSource.setPassword(password);
        Connection connection = mysqlDataSource.getConnection();
        branchBarrier.call(connection, (barrier) -> {
            System.out.println("转入回滚");
        });
        return TransResponse.buildTransResponse(Constant.SUCCESS_RESULT);
    }
}