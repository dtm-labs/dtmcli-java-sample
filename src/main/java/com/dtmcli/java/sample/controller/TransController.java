package com.dtmcli.java.sample.controller;

import common.constant.Constant;
import common.model.TransResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("api")
public class TransController {

    Logger logger = LoggerFactory.getLogger(TransController.class);

    @RequestMapping("TransOutTry")
    public Object TransOutTry() {
        logger.info("TransOutTry");
        return TransResponse.buildTransResponse(Constant.SUCCESS_RESULT);
    }

    @RequestMapping("TransOutConfirm")
    public Object TransOutConfirm() {
        logger.info("TransOutConfirm");
        return TransResponse.buildTransResponse(Constant.SUCCESS_RESULT);

    }

    @RequestMapping("TransOutCancel")
    public Object TransOutCancel() {
        logger.info("TransOutCancel");
        return TransResponse.buildTransResponse(Constant.SUCCESS_RESULT);

    }

    @RequestMapping("TransInTry")
    public Object TransInTry() {
        logger.info("TransInTry");
        return TransResponse.buildTransResponse(Constant.SUCCESS_RESULT);

    }

    @RequestMapping("TransInConfirm")
    public Object TransInConfirm() {
        logger.info("TransInConfirm");
        return TransResponse.buildTransResponse(Constant.SUCCESS_RESULT);
    }

    @RequestMapping("TransInCancel")
    public Object TransInCancel() {
        logger.info("TransInCancel");
        return TransResponse.buildTransResponse(Constant.SUCCESS_RESULT);
    }
}