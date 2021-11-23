package com.dtmcli.java.sample.controller;

import barrier.BranchBarrier;
import com.alibaba.fastjson.JSON;
import com.dtmcli.java.sample.param.TransReq;
import com.dtmcli.java.sample.util.DataSourceUtil;
import common.constant.Constant;
import common.model.TransResponse;
import common.utils.StreamUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@RestController
@RequestMapping("api")
@Slf4j
public class TransBarrierController {
    
    Logger logger = LoggerFactory.getLogger(TransBarrierController.class);
    
    @Autowired
    private DataSourceUtil dataSourceUtil;
    
    
    @RequestMapping("barrierTransOutTry")
    public Object TransOutTry(HttpServletRequest request) throws Exception {
        
        BranchBarrier branchBarrier = new BranchBarrier(request.getParameterMap());
        logger.info("barrierTransOutTry branchBarrier:{}", branchBarrier);
        
        TransReq transReq = extracted(request);
        Connection connection = dataSourceUtil.getConnecion();
        branchBarrier.call(connection, (barrier) -> {
            System.out.println("用户: +" + transReq.getUserId() + ",转出" + Math.abs(transReq.getAmount()) + "元准备");
            this.adjustTrading(connection, transReq);
        });
        connection.close();
        return TransResponse.buildTransResponse(Constant.SUCCESS_RESULT);
    }
    
    
    @RequestMapping("barrierTransOutConfirm")
    public Object TransOutConfirm(HttpServletRequest request) throws Exception {
        BranchBarrier branchBarrier = new BranchBarrier(request.getParameterMap());
        logger.info("barrierTransOutConfirm branchBarrier:{}", branchBarrier);
        Connection connection = dataSourceUtil.getConnecion();
        TransReq transReq = extracted(request);
        branchBarrier.call(connection, (barrier) -> {
            System.out.println("用户: +" + transReq.getUserId() + ",转出" + Math.abs(transReq.getAmount()) + "元提交");
            adjustBalance(connection, transReq);
        });
        connection.close();
        return TransResponse.buildTransResponse(Constant.SUCCESS_RESULT);
    }
    
    @RequestMapping("barrierTransOutCancel")
    public Object TransOutCancel(HttpServletRequest request) throws Exception {
        BranchBarrier branchBarrier = new BranchBarrier(request.getParameterMap());
        logger.info("barrierTransOutCancel branchBarrier:{}", branchBarrier);
        TransReq transReq = extracted(request);
        Connection connection = dataSourceUtil.getConnecion();
        branchBarrier.call(connection, (barrier) -> {
            System.out.println("用户: +" + transReq.getUserId() + ",转出" + Math.abs(transReq.getAmount()) + "元回滚");
            this.adjustTrading(connection, transReq);
        });
        connection.close();
        return TransResponse.buildTransResponse(Constant.SUCCESS_RESULT);
    }
    
    @RequestMapping("barrierTransInTry")
    public Object TransInTry(HttpServletRequest request) throws Exception {
        BranchBarrier branchBarrier = new BranchBarrier(request.getParameterMap());
        logger.info("barrierTransInTry branchBarrier:{}", branchBarrier);
        Connection connection = dataSourceUtil.getConnecion();
        TransReq transReq = extracted(request);
        branchBarrier.call(connection, (barrier) -> {
            System.out.println("用户: +" + transReq.getUserId() + ",转入" + Math.abs(transReq.getAmount()) + "元准备");
            this.adjustTrading(connection, transReq);
        });
        connection.close();
        return TransResponse.buildTransResponse(Constant.SUCCESS_RESULT);
    }
    
    @RequestMapping("barrierTransInConfirm")
    public Object TransInConfirm(HttpServletRequest request) throws Exception {
        BranchBarrier branchBarrier = new BranchBarrier(request.getParameterMap());
        logger.info("barrierTransInConfirm TransInCancel branchBarrier:{}", branchBarrier);
        Connection connection = dataSourceUtil.getConnecion();
        TransReq transReq = extracted(request);
        branchBarrier.call(connection, (barrier) -> {
            System.out.println("用户: +" + transReq.getUserId() + ",转出" + Math.abs(transReq.getAmount()) + "元提交");
            adjustBalance(connection, transReq);
        });
        connection.close();
        return TransResponse.buildTransResponse(Constant.SUCCESS_RESULT);
    }
    
    @RequestMapping("barrierTransInCancel")
    public Object TransInCancel(HttpServletRequest request) throws Exception {
        BranchBarrier branchBarrier = new BranchBarrier(request.getParameterMap());
        logger.info("barrierTransInCancel branchBarrier:{}", branchBarrier);
        Connection connection = dataSourceUtil.getConnecion();
        TransReq transReq = extracted(request);
        branchBarrier.call(connection, (barrier) -> {
            System.out.println("用户: +" + transReq.getUserId() + ",转入" + Math.abs(transReq.getAmount()) + "回滚");
            this.adjustTrading(connection, transReq);
        });
        connection.close();
        return TransResponse.buildTransResponse(Constant.SUCCESS_RESULT);
    }
    
    /**
     * 提取body中参数
     *
     * @param request
     * @return
     * @throws IOException
     */
    private TransReq extracted(HttpServletRequest request) throws IOException {
        byte[] bytes = StreamUtil.copyToByteArray(request.getInputStream());
        return JSON.parseObject(bytes, TransReq.class);
    }
    
    /**
     * 更新交易金额
     *
     * @param connection
     * @param transReq
     * @throws SQLException
     */
    public void adjustTrading(Connection connection, TransReq transReq) {
        String sql = "update dtm_busi.user_account set trading_balance=trading_balance+?"
                + " where user_id=? and trading_balance + ? + balance >= 0";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, transReq.getAmount());
            preparedStatement.setInt(2, transReq.getUserId());
            preparedStatement.setInt(3, transReq.getAmount());
            if (preparedStatement.executeUpdate() > 0) {
                System.out.println("交易金额更新成功");
            }
            preparedStatement.close();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        
    }
    
    /**
     * 更新余额
     */
    public void adjustBalance(Connection connection, TransReq transReq) {
        try {
            String sql = "update dtm_busi.user_account set trading_balance=trading_balance-?,balance=balance+? where user_id=?";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, transReq.getAmount());
            preparedStatement.setInt(2, transReq.getAmount());
            preparedStatement.setInt(3, transReq.getUserId());
            if (preparedStatement.executeUpdate() > 0) {
                System.out.println("余额更新成功");
            }
            preparedStatement.close();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
    
}