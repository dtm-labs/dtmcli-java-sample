package com.github.viticis.dtmclijavaexamples.controller;

import dtmcli.Tcc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.function.Function;

@RestController
@RequestMapping(("api"))
public class TccController {

    private static final String svc = "http://localhost:8081/api";
    @Autowired
    private Tcc tcc;

    @RequestMapping("fireTcc")
    public String fireTcc() {
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
}