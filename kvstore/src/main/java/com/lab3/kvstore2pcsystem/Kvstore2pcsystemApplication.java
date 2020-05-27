package com.lab3.kvstore2pcsystem;

import com.lab3.kvstore2pcsystem.func_co.Participant;
import com.lab3.kvstore2pcsystem.func_co.coordinator.CheckNodeAliveRunner;
import com.lab3.kvstore2pcsystem.func_co.coordinator.CoordinatorServer;
import com.lab3.kvstore2pcsystem.func_co.coordinator.NodeManager;
import com.lab3.kvstore2pcsystem.func_pa.participant.Database;
import com.lab3.kvstore2pcsystem.func_pa.participant.HeartBeatRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;

import java.io.IOException;
import java.util.Date;

//import java.lang.Object.joptsimple.OptionParser;

@SpringBootApplication
public class Kvstore2pcsystemApplication implements WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> {

    public static void main(String[] args) throws IOException {
        //根据传入的配置文件url进行配置
        Config config = Config.getInstance();
        config.Config_intepret(args[1]);
//        config.Config_intepret("src/main/resources/coordinator.conf");

        SpringApplication.run(Kvstore2pcsystemApplication.class, args);
        if (config.getMode() == 0) {
            run_co();
        } else if (config.getMode() == 1) {
            run_pa();
        }


    }

    static void run_co() {

        //设置参与者MAP
        Config config = Config.getInstance();
        System.out.println("size is" + config.paIP.size());
        for (int i = 0; i < config.paIP.size(); ++i) {
            Participant p = new Participant();
            p.setIp(config.paIP.get(i));
            p.setPort(config.paPORT.get(i));
            System.out.println("ip+port is==" + p.getIp() + ":" + p.getPort());
            NodeManager.participants.put(p, new Date());
        }
        //开启协作者tcp客户端
        new CoordinatorServer().run();
        //开启数据节点活跃检测线程
        new CheckNodeAliveRunner().run();
    }

    static void run_pa() {
        //初始化Database的信息
        Config config = Config.getInstance();
        Database.participant.setCo_addr(config.getCoIP());
        System.out.println("COIP is " + config.getCoIP());
        Database.participant.setCo_port(String.valueOf(config.getCoPORT()));
        System.out.println("CO PORT is==" + config.getCoPORT());
        Database.participant.setIp(config.getParIP());

        Database.participant.setPort(String.valueOf(config.getParPORT()));
        System.out.println("my PORT is==" + Database.participant.getPort());
        new HeartBeatRunner().run();
    }

    @Override
    public void customize(ConfigurableServletWebServerFactory factory) {
        //如果是参与者 那么需要把配置文件中的paPort改成springboot的容器端口号
        //如果是协作者 那么springboot端口号就是我们内定的8080
        if (Config.getInstance().getMode() == 1) {
            factory.setPort(Config.getInstance().getParPORT());
        } else if (Config.getInstance().getMode() == 0) {
            factory.setPort(8080);
        }
    }
}