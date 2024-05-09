package com.grpc.grpcdemo.initial;

import org.springframework.context.ApplicationEvent;

public class StarApplicationEvent extends ApplicationEvent {

    public StarApplicationEvent(Object source) {
        super(source);
    }
}