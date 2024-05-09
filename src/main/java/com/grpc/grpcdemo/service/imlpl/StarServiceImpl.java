package com.grpc.grpcdemo.service.imlpl;

import com.grpc.grpcdemo.grpc.StreamServiceGrpc;
import com.grpc.grpcdemo.service.StarService;
import example.StreamServiceOuterClass;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Service;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

@Service
public class StarServiceImpl implements StarService {

    @Override
    public String rpcCheck() {
        CountDownLatch finishLatch = new CountDownLatch(1);

        StringBuffer checkResult = new StringBuffer();
        try {
            //进行星光RPC验证
            ManagedChannel managedChannel = ManagedChannelBuilder.forTarget("localhost:8083").usePlaintext()
                    .build();

            StreamServiceGrpc.StreamServiceStub stub = StreamServiceGrpc.newStub(managedChannel);
            StreamObserver<StreamServiceOuterClass.StreamResponse> streamObserver = new StreamObserver<StreamServiceOuterClass.StreamResponse>() {
                @Override
                public void onNext(StreamServiceOuterClass.StreamResponse streamResponse) {
                    //接受阿里验证的结果
                    String responseMessage = "阿里验证的结果：" + streamResponse.getMessage();
                    checkResult.append("--").append(responseMessage);
                    System.out.println(checkResult);
                }

                @Override
                public void onError(Throwable throwable) {
                    System.out.println(throwable.getMessage());
                }

                @Override
                public void onCompleted() {
                    finishLatch.countDown();
                }
            };

            StreamObserver<StreamServiceOuterClass.StreamRequest> requestObserver = stub.bidirectionalStreaming(streamObserver);
            try {
                //获取星光验证的结果
                String starCheck = this.starCheck();

                checkResult.append(starCheck);

                StreamServiceOuterClass.StreamRequest req = StreamServiceOuterClass.StreamRequest.newBuilder().setMessage("星光验证结果：" + starCheck ).build();
                requestObserver.onNext(req);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            requestObserver.onCompleted();

            finishLatch.await(10, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return checkResult.toString();
    }

    @Override
    public String starCheck() {
        //进行星光RPC验证
        return "星光验证成功";
    }
}
