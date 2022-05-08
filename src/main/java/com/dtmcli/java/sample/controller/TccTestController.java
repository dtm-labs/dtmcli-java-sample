package com.dtmcli.java.sample.controller;

import com.dtmcli.java.sample.param.TransReq;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pub.dtm.client.DtmClient;
import pub.dtm.client.tcc.Tcc;

import java.util.UUID;


@RestController
@RequestMapping(("api"))
@Slf4j
public class TccTestController {
    
    private static final String svc = "http://localhost:8081/api";
    
    @Value("${dtm.ipport}")
    private String endpoint;
    
    /**
     * 常规tcc demo
     *
     * @return
     */
    @RequestMapping("testTcc")
    public String testTcc() {
        //创建dtm clinet
        DtmClient dtmClient = new DtmClient(endpoint);
        //创建tcc事务
        String customGid = UUID.randomUUID().toString();
        try {
            dtmClient.tccGlobalTransaction(customGid, TccTestController::tccTrans);
        } catch (Exception e) {
            log.error("tccGlobalTransaction error", e);
            return "fail";
        }
        return "success";
    }
    
    
    /**
     * 具有子事务屏障功能的tcc demo (转账成功)
     *
     * @return
     */
    @RequestMapping("tccBarrier")
    public String tccBarrier() {
        // 创建dmt client
        DtmClient dtmClient = new DtmClient(endpoint);
        //创建tcc事务
        String customGid = UUID.randomUUID().toString();
        try {
            dtmClient.tccGlobalTransaction(customGid, TccTestController::tccBarrierTrans);
        } catch (Exception e) {
            log.error("tccGlobalTransaction error", e);
            return "fail";
        }
        return "success";
    }
    
    /**
     * 具有子事务屏障功能的tcc demo (转账失败)
     *
     * @return
     */
    @RequestMapping("tccBarrierError")
    public String tccBarrierError() {
        // 创建dmt client
        DtmClient dtmClient = new DtmClient(endpoint);
        //创建tcc事务
        String customGid = UUID.randomUUID().toString();
        try {
            dtmClient.tccGlobalTransaction(customGid, TccTestController::tccBarrierTransError);
        } catch (Exception e) {
            log.error("tccGlobalTransaction error", e);
            return "fail";
        }
        return "success";
    }
    
    
    /**
     * 定义tcc事务函数，内部需要通过callBranch注册事务子分支
     *
     * @param tcc
     * @return
     * @see TransController
     */
    public static void tccTrans(Tcc tcc) throws Exception {
        Response outResponse = tcc
                .callBranch("", svc + "/TransOutTry", svc + "/TransOutConfirm", svc + "/TransOutCancel");
        log.info("outResponse:{}", outResponse);
        Response inResponse = tcc.callBranch("", svc + "/TransInTry", svc + "/TransInConfirm", svc + "/TransInCancel");
        log.info("inResponse:{}", inResponse);
    }
    
    
    /**
     * 定义tcc事务函数，内部需要通过callBranch注册事务子分支
     *
     * @param tcc
     * @return
     * @see TransBarrierController
     */
    public static void tccBarrierTrans(Tcc tcc) throws Exception {
        // 用户1 转出30元
        Response outResponse = tcc
                .callBranch(new TransReq(1, -30), svc + "/barrierTransOutTry", svc + "/barrierTransOutConfirm",
                        svc + "/barrierTransOutCancel");
        log.info("outResponse:{}", outResponse);
        
        // 用户2 转入30元
        Response inResponse = tcc
                .callBranch(new TransReq(2, 30), svc + "/barrierTransInTry", svc + "/barrierTransInConfirm",
                        svc + "/barrierTransInCancel");
        log.info("inResponse:{}", inResponse);
        
    }
    
    /**
     * 定义tcc事务函数，内部需要通过callBranch注册事务子分支， 转账金额大于余额 转账失败
     *
     * @param tcc
     * @return
     * @see TransBarrierController
     */
    public static void tccBarrierTransError(Tcc tcc) throws Exception {
        // 用户1 转出100000元
        Response outResponse = tcc
                .callBranch(new TransReq(1, -100000), svc + "/barrierTransOutTry", svc + "/barrierTransOutConfirm",
                        svc + "/barrierTransOutCancel");
        log.info("outResponse:{}", outResponse);
        
        // 用户2 转入100000元
        Response inResponse = tcc
                .callBranch(new TransReq(2, 100000), svc + "/barrierTransInTry", svc + "/barrierTransInConfirm",
                        svc + "/barrierTransInCancel");
        log.info("inResponse:{}", inResponse);
        
    }
}