package com.grpc.grpcdemo.controller;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseData {

    private int code;

    private Object message;

    public ResponseData(int code, Object message) {
        this.code = code;
        this.message = message;
    }

    public static ResponseData success() {
        return new ResponseData(200, "");
    }


    public static ResponseData success(String message) {
        return new ResponseData(200, message);
    }
}
