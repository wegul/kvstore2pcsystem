package com.lab3.kvstore2pcsystem;

import com.alibaba.fastjson.JSON;
import com.lab3.kvstore2pcsystem.coordinator.CoordinatorServer;
import com.lab3.kvstore2pcsystem.protocol.RespResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Kvstore2pcsystemApplicationTests {

    @Test
    void contextLoads() {
        new CoordinatorServer().run();
    }

    public static void main(String[] args) {
//        String s = "*4\r\n$3\r\nSET\r\n$7\r\nCS06142\r\n$5\r\nCloud\r\n$9\r\nComputing\r\n";
//        String s1 = "*2\r\n$3\r\nGET\r\n$7\r\nCS06142\r\n";
//        System.out.println(s);
//        System.out.println("----\n" + s1);
//        new CoordinatorServer().run();

        RespResponse respResponse = JSON.parseObject(null, RespResponse.class);
        System.out.println(respResponse);
    }
}
