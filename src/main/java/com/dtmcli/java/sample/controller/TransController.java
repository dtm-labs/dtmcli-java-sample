package com.dtmcli.java.sample.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pub.dtm.client.constant.Constants;
import pub.dtm.client.model.responses.DtmResponse;


@RestController
@RequestMapping("api")
public class TransController {

    Logger logger = LoggerFactory.getLogger(TransController.class);

    @RequestMapping("TransOutTry")
    public Object TransOutTry() {
        logger.info("TransOutTry");
        return DtmResponse.buildDtmResponse(Constants.SUCCESS_RESULT);
    }

    @RequestMapping("TransOutConfirm")
    public Object TransOutConfirm() {
        logger.info("TransOutConfirm");
        return DtmResponse.buildDtmResponse(Constants.SUCCESS_RESULT);

    }

    @RequestMapping("TransOutCancel")
    public Object TransOutCancel() {
        logger.info("TransOutCancel");
        return DtmResponse.buildDtmResponse(Constants.SUCCESS_RESULT);

    }

    @RequestMapping("TransInTry")
    public Object TransInTry() {
        logger.info("TransInTry");
        return DtmResponse.buildDtmResponse(Constants.SUCCESS_RESULT);

    }

    @RequestMapping("TransInConfirm")
    public Object TransInConfirm() {
        logger.info("TransInConfirm");
        return DtmResponse.buildDtmResponse(Constants.SUCCESS_RESULT);
    }

    @RequestMapping("TransInCancel")
    public Object TransInCancel() {
        logger.info("TransInCancel");
        return DtmResponse.buildDtmResponse(Constants.SUCCESS_RESULT);
    }

    @RequestMapping("TransOut")
    public Object TransOut() {
        logger.info("TransOut");
        return DtmResponse.buildDtmResponse(Constants.SUCCESS_RESULT);
    }

    @RequestMapping("TransOutCompensate")
    public Object TransOutCompensate() {
        logger.info("TransOutCompensate");
        return DtmResponse.buildDtmResponse(Constants.SUCCESS_RESULT);
    }

    @RequestMapping("TransIn")
    public Object TransIn() {
        logger.info("TransIn");
        return DtmResponse.buildDtmResponse(Constants.SUCCESS_RESULT);
    }

    @RequestMapping("TransInCompensate")
    public Object TransInCompensate() {
        logger.info("TransInCompensate");
        return DtmResponse.buildDtmResponse(Constants.SUCCESS_RESULT);
    }
}