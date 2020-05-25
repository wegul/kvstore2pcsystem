package com.lab3.kvstore2pcsystem;

import com.lab3.kvstore2pcsystem.coordinator.CheckNodeAliveRunner;
import com.lab3.kvstore2pcsystem.coordinator.CoordinatorServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Kvstore2pcsystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(Kvstore2pcsystemApplication.class, args);
        //开启协作者tcp客户端
        new CoordinatorServer().run();
        //开启数据节点活跃检测线程
        new CheckNodeAliveRunner().run();
    }

}
