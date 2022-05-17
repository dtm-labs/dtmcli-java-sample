package com.dtmcli.java.sample.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pub.dtm.client.DtmClient;
import pub.dtm.client.saga.Saga;

import java.util.UUID;

@RestController
@RequestMapping(("api"))
@Slf4j
public class SagaTestController {

    private static final String svc = "http://localhost:8081/api";

    @Value("${dtm.ipport}")
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
            String customGid = UUID.randomUUID().toString();
            Saga saga = dtmClient
                    .newSaga(customGid)
                    .add(svc + "/TransOut", svc + "/TransOutCompensate", "")
                    .add(svc + "/TransIn", svc + "/TransInCompensate", "")
                    .enableWaitResult();

            saga.submit();
        } catch (Exception e) {
            log.error("saga submit error", e);
            return "fail";
        }
        return "success";
    }
}
