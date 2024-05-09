package com.grpc.grpcdemo.initial;

import com.grpc.grpcdemo.server.StreamService;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class StarServerInitial {

    @EventListener
    public void onApplicationEvent(ApplicationReadyEvent event) {

        Server s = ServerBuilder.forPort(8081)
                .addService(new StreamService())
                .build();
        try {
            s.start();
            System.out.println("server started......");
            s.awaitTermination();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
