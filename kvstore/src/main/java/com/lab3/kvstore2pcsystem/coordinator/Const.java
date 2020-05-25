package com.lab3.kvstore2pcsystem.coordinator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.tools.JavaCompiler;

@Component
public class Const {

    private static int port;

    // 1、最近活跃时间早于30*1000毫秒 那么就认为数据节点掉线
    // 2、每隔30秒检查一遍数据节点列表
    public static int ALIVE_CHECK_INTERVAL = 30 * 1000;

    //java -jar --xxx.config xxxx.jar

    public static int getPort() {
        return port;
    }

    @Value("${tcpServer.port}")
    public void setPort(int port) {
        Const.port = port;
    }

}
