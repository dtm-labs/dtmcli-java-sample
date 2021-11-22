package com.dtmcli.java.sample.controller;

import client.DtmClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tcc.Tcc;


@RestController
@RequestMapping(("api"))
public class TccController {
    
    private static final String svc = "http://localhost:8081/api";
    
    @Value("${dtm.ipPort}")
    private String ipPort;
    
    
    @RequestMapping("testTcc")
    public String testTcc() throws Exception {
        DtmClient dtmClient = new DtmClient(ipPort);
        Tcc tcc = dtmClient.newTcc();
        return tcc.tccGlobalTransaction(TccController::tccTrans);
    }
    
    public static Boolean tccTrans(Tcc tcc) {
        try {
            boolean a = tcc.callBranch("", svc + "/TransOutTry", svc + "/TransOutConfirm", svc + "/TransOutCancel");
            boolean b = tcc.callBranch("", svc + "/TransInTry", svc + "/TransInConfirm", svc + "/TransInCancel");
            return a && b;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    @RequestMapping("tccBarrier")
    public String test() throws Exception {
        Tcc tcc = new Tcc(ipPort);
        return tcc.tccGlobalTransaction(TccController::tccBarrierTrans);
    }
    
    public static Boolean tccBarrierTrans(Tcc tcc) {
        try {
            boolean a = tcc.callBranch("", svc + "/barrierTransOutTry", svc + "/barrierTransOutConfirm",
                    svc + "/barrierTransOutCancel");
            boolean b = tcc.callBranch("", svc + "/barrierTransInTry", svc + "/barrierTransInConfirm",
                    svc + "/barrierTransInCancel");
            return a && b;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}