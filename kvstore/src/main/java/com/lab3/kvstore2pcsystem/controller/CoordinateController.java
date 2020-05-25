package com.lab3.kvstore2pcsystem.controller;

import com.lab3.kvstore2pcsystem.coordinator.NodeManager;
import com.lab3.kvstore2pcsystem.protocol.RespResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@RestController
@RequestMapping("coordinator")
public class CoordinateController {
    //供数据节点调用的心跳接口
    @PostMapping(value = "heartbeat")
    String heartbeat(HttpServletRequest request) {
        //从request中获取参数
        Map<String, String[]> parameterMap = request.getParameterMap();
        String ip = parameterMap.get("ip")[0];      //数据节点ip
        String port = parameterMap.get("port")[0];  //数据节点端口号
        System.out.println("ip: " + ip + " port: " + port + "   访问heartbeat接口");
        int i = NodeManager.refreshAliveNode(ip, port);
        return i == 1 ? "success" : "false";
    }

}
