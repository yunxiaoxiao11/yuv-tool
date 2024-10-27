package com.example.yuv.tool.controller;

import com.example.yuv.tool.model.EnumReq;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EnumTestController {
    @PostMapping("/test/enum")
    public String test01(@RequestBody EnumReq req) {
        System.out.println((String.format("接收到参数%s", req.getStatusValue().getName())));
        System.out.println((String.format("接收到参数%s", req.getStatusField().getName())));
        System.out.println((String.format("接收到参数%s", req.getStatusMethod().getName())));
        return req.getStatusValue().getName();
    }
}
