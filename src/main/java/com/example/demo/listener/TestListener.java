package com.example.demo.listener;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestListener implements MqListener<String> {

    @Override
    public boolean onReceive(String msg) {
        log.info("success!! "+msg);
        return true;
    }

    @Override
    public void onError(String msg, Exception e) {
        log.error("error");
    }
}
