package com.dtmcli.java.sample.controller;

import common.enums.TransTypeEnum;
import common.model.DtmServerInfo;
import common.model.TransBase;
import common.utils.IdGeneratorUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tcc.Tcc;

import java.util.function.Function;

@RestController
@RequestMapping(("api"))
public class TccController {
    
    private static final String svc = "http://localhost:8081/api";
    
    @Value("${dtm.address}")
    private String address;
    
    
    @RequestMapping("fireTcc")
    public String fireTcc() throws Exception {
        DtmServerInfo dtmServerInfo = new DtmServerInfo(address);
        String gid = IdGeneratorUtil.genGid(dtmServerInfo.newGid());
        TransBase transBase = new TransBase(TransTypeEnum.TCC, gid, false);
        Tcc tcc = new Tcc(transBase, dtmServerInfo);
        Function<Tcc, Boolean> function = TccController::tccTrans;
        return tcc.tccGlobalTransaction(function);
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
        DtmServerInfo dtmServerInfo = new DtmServerInfo(address);
        String gid = IdGeneratorUtil.genGid(dtmServerInfo.newGid());
        TransBase transBase = new TransBase(TransTypeEnum.TCC, gid, false);
        Tcc tcc = new Tcc(transBase, dtmServerInfo);
        Function<Tcc, Boolean> function = TccController::tccBarrierTrans;
        return tcc.tccGlobalTransaction(function);
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