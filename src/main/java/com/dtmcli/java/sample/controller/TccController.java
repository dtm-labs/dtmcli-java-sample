package com.dtmcli.java.sample.controller;

import client.DtmClient;
import common.constant.Constant;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tcc.Tcc;

import java.util.Objects;


@RestController
@RequestMapping(("api"))
public class TccController {
    
    private static final String svc = "http://localhost:8081/api";
    
    @Value("${dtm.ipPort}")
    private String ipPort;
    
    
    @RequestMapping("testTcc")
    public String testTcc() throws Exception {
        DtmClient dtmClient = new DtmClient(ipPort);
        Tcc tcc = dtmClient.newTcc(dtmClient.genGid());
        return tcc.tccGlobalTransaction(TccController::tccTrans);
    }
    
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
    
    @RequestMapping("tccBarrier")
    public String test() throws Exception {
        DtmClient dtmClient = new DtmClient(ipPort);
        Tcc tcc = dtmClient.newTcc(dtmClient.genGid());
        return tcc.tccGlobalTransaction(TccController::tccBarrierTrans);
    }
    
    public static Boolean tccBarrierTrans(Tcc tcc) {
        try {
            Response outResponse = tcc.callBranch("", svc + "/barrierTransOutTry?lxs=123", svc + "/barrierTransOutConfirm",
                    svc + "/barrierTransOutCancel");
            Response inResponse = tcc.callBranch("", svc + "/barrierTransInTry", svc + "/barrierTransInConfirm",
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