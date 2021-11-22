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
    public TransResponse TransOutTry() {
        logger.info("TransOutTry");
        return TransResponse.buildTransResponse(Constant.SUCCESS_RESULT);
    }
    
    @RequestMapping("TransOutConfirm")
    public TransResponse TransOutConfirm() {
        logger.info("TransOutConfirm");
        return TransResponse.buildTransResponse(Constant.SUCCESS_RESULT);
        
    }
    
    @RequestMapping("TransOutCancel")
    public TransResponse TransOutCancel() {
        logger.info("TransOutCancel");
        return TransResponse.buildTransResponse(Constant.SUCCESS_RESULT);
        
    }
    
    @RequestMapping("TransInTry")
    public TransResponse TransInTry() {
        logger.info("TransInTry");
        return TransResponse.buildTransResponse(Constant.SUCCESS_RESULT);
        
    }
    
    @RequestMapping("TransInConfirm")
    public TransResponse TransInConfirm() {
        logger.info("TransInConfirm");
        return TransResponse.buildTransResponse(Constant.SUCCESS_RESULT);
    }
    
    @RequestMapping("TransInCancel")
    public TransResponse TransInCancel() {
        logger.info("TransInCancel");
        return TransResponse.buildTransResponse(Constant.SUCCESS_RESULT);
    }
}