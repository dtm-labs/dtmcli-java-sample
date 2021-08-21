package com.github.viticis.dtmclijavaexamples;

import dtmcli.Tcc;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class DtmcliJavaExamplesApplication {

    @Value("${dtm.svr}")
    private String dtm;

    public static void main(String[] args) {
        SpringApplication.run(DtmcliJavaExamplesApplication.class, args);
    }

    @Bean
    public Tcc tcc() throws Exception {
        return new Tcc(dtm, false);
    }
}