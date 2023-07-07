package com.example.demo.entity;


import com.example.demo.listener.MqListener;
import lombok.Data;

import java.io.Serializable;

@Data
public class MqMessageEntity<T> implements Serializable {

    T data;

    Class<? extends MqListener<T>> listener;

    public MqMessageEntity(T msg, Class<? extends MqListener<T>> listener){
        this.data=msg;
        this.listener=listener;
    }
}