package com.dtmcli.java.sample.controller;

import cn.hutool.http.server.HttpServerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("api")
public class TransController {
    
    Logger logger = LoggerFactory.getLogger(TransController.class);
    
    @RequestMapping("TransOutTry")
    public Map<String, String> TransOutTry() {
        logger.info("TransOutTry");
        Map<String, String> result = new HashMap<>();
        result.put("dtm_result", "SUCCESS");
        return result;
    }
    
    @RequestMapping("TransOutConfirm")
    public Map<String, String> TransOutConfirm(HttpServerResponse response) {
        logger.info("TransOutConfirm");
        Map<String, String> result = new HashMap<>();
        result.put("dtm_result", "SUCCESS");
        return result;
    }
    
    @RequestMapping("TransOutCancel")
    public Map<String, String> TransOutCancel() {
        logger.info("TransOutCancel");
        Map<String, String> result = new HashMap<>();
        result.put("dtm_result", "SUCCESS");
        return result;
    }
    
    @RequestMapping("TransInTry")
    public Map<String, String> TransInTry() {
        logger.info("TransInTry");
        Map<String, String> result = new HashMap<>();
        result.put("dtm_result", "SUCCESS");
        return result;
    }
    
    @RequestMapping("TransInConfirm")
    public Map<String, String> TransInConfirm() {
        logger.info("TransInConfirm");
        Map<String, String> result = new HashMap<>();
        result.put("dtm_result", "SUCCESS");
        return result;
    }
    
    @RequestMapping("TransInCancel")
    public Map<String, String> TransInCancel() {
        logger.info("TransInCancel");
        Map<String, String> result = new HashMap<>();
        result.put("dtm_result", "SUCCESS");
        return result;
    }
}