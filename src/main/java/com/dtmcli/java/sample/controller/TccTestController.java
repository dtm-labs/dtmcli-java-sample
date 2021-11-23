package com.dtmcli.java.sample.controller;

import client.DtmClient;
import com.dtmcli.java.sample.param.TransReq;
import common.constant.Constant;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tcc.Tcc;

import java.util.Objects;


@RestController
@RequestMapping(("api"))
public class TccTestController {
    
    private static final String svc = "http://localhost:8081/api";
    
    @Value("${dtm.ipPort}")
    private String ipPort;
    
    /**
     * 常规tcc demo
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("testTcc")
    public String testTcc() throws Exception {
        //创建dtm clinet
        DtmClient dtmClient = new DtmClient(ipPort);
        //创建tcc事务
        Tcc tcc = dtmClient.newTcc(dtmClient.genGid());
        // 执行事务
        return tcc.tccGlobalTransaction(TccTestController::tccTrans);
    }
    
    /**
     * 定义tcc事务函数，内部需要通过callBranch注册事务子分支
     *
     * @param tcc
     * @return
     * @see TransController
     */
    public static Boolean tccTrans(Tcc tcc) {
        try {
            Response outResponse = tcc
                    .callBranch("", svc + "/TransOutTry", svc + "/TransOutConfirm", svc + "/TransOutCancel");
            Response inResponse = tcc
                    .callBranch("", svc + "/TransInTry", svc + "/TransInConfirm", svc + "/TransInCancel");
            boolean outResult = false;
            boolean inResult = false;
            if (Objects.nonNull(outResponse) && Objects.nonNull(outResponse.body())) {
                if (outResponse.body().string().contains(Constant.SUCCESS_RESULT)) {
                    outResult = true;
                }
            }
            if (Objects.nonNull(inResponse) && Objects.nonNull(inResponse.body())) {
                if (inResponse.body().string().contains(Constant.SUCCESS_RESULT)) {
                    inResult = true;
                }
            }
            return outResult && inResult;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    /**
     * 具有子事务屏障功能的tcc demo
     *
     * @return
     * @throws Exception
     */
    @RequestMapping("tccBarrier")
    public String test() throws Exception {
        // 创建dmt client
        DtmClient dtmClient = new DtmClient(ipPort);
        // 创建tcc事务
        Tcc tcc = dtmClient.newTcc(dtmClient.genGid());
        // 执行事务
        return tcc.tccGlobalTransaction(TccTestController::tccBarrierTrans);
    }
    
    
    /**
     * 定义tcc事务函数，内部需要通过callBranch注册事务子分支
     *
     * @param tcc
     * @return
     * @see TransBarrierController
     */
    public static Boolean tccBarrierTrans(Tcc tcc) {
        try {
            // 用户1 转出30元
            Response outResponse = tcc
                    .callBranch(new TransReq(1, -30), svc + "/barrierTransOutTry", svc + "/barrierTransOutConfirm",
                            svc + "/barrierTransOutCancel");
            // 用户2 转入30元
            Response inResponse = tcc
                    .callBranch(new TransReq(2, 30), svc + "/barrierTransInTry", svc + "/barrierTransInConfirm",
                            svc + "/barrierTransInCancel");
            boolean outResult = false;
            boolean inResult = false;
            if (Objects.nonNull(outResponse) && Objects.nonNull(outResponse.body())) {
                if (outResponse.body().string().contains(Constant.SUCCESS_RESULT)) {
                    outResult = true;
                }
            }
            if (Objects.nonNull(inResponse) && Objects.nonNull(inResponse.body())) {
                if (inResponse.body().string().contains(Constant.SUCCESS_RESULT)) {
                    inResult = true;
                }
            }
            return outResult && inResult;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}