package com.dtmcli.java.sample.controller;

import client.DtmClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import saga.Saga;

@RestController
@RequestMapping(("api"))
@Slf4j
public class SagaTestController {

    private static final String svc = "http://localhost:8081/api";

    @Value("${dtm.ipPort}")
    private String ipPort;

    /**
     * normal saga demo
     *
     * @return
     */
    @RequestMapping("testSaga")
    public String testSage() {

        DtmClient dtmClient = new DtmClient(ipPort);

        try {
            // create saga transaction
            Saga saga = dtmClient.newSaga(dtmClient.genGid());
            saga.add(svc + "/TransOut", svc + "/TransOutCompensate", "");
            saga.add(svc + "/TransIn", svc + "/TransInCompensate", "");
            saga.enableWaitResult();

            saga.submit();
        } catch (Exception e) {
            log.error("saga submit error", e);
            return "fail";
        }
        return "success";
    }
}
