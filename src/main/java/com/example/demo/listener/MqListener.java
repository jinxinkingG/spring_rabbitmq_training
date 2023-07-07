package com.example.demo.listener;

import java.io.Serializable;

public interface MqListener<T> extends Serializable {
    public boolean onReceive(T msg);

    public void onError(T msg,Exception e);
}
