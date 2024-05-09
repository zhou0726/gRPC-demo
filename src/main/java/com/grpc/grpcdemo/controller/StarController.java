package com.grpc.grpcdemo.controller;

import com.grpc.grpcdemo.service.StarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/star")
public class StarController {


    @Autowired
    private StarService starService;

    @GetMapping("/rpcCheck")
    public ResponseData rpcCheck() {

        //1.进行星光验证
        //2.进行阿里云验证
        String checkResult = starService.rpcCheck();

        return ResponseData.success(checkResult);
    }

}
