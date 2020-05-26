package com.lab3.kvstore2pcsystem;

import com.alibaba.fastjson.JSON;
import com.lab3.kvstore2pcsystem.coordinator.CoordinatorServer;
import com.lab3.kvstore2pcsystem.protocol.RespRequest;
import com.lab3.kvstore2pcsystem.protocol.RespResponse;
import com.lab3.kvstore2pcsystem.utils.HttpClientUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.xml.transform.Source;
import java.util.ArrayList;
import java.util.HashMap;

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

//        RespResponse respResponse = JSON.parseObject(null, RespResponse.class);
//        System.out.println(respResponse);

        HashMap<String, String> stringStringHashMap = new HashMap<>();
        RespRequest object = new RespRequest();
        object.setTransactionNo("123123");
        object.setRequestType(RespRequest.METHOD.SET.getCode());
        object.setMethod(RespRequest.METHOD.SET.getName());
        ArrayList<String> keys = new ArrayList<>();
        keys.add("name");
        object.setKeys(keys);
        ArrayList<String> values = new ArrayList<>();
        values.add("GeorgeYang");
        object.setValues(values);
        object.setRaw("1241245125");
        String string = JSON.toJSONString(object);
        System.out.println(string);
//        String s = HttpClientUtils.HttpPostWithJson("http://" + "localhost" + ":" + "8088" + "/kvstore/set", string);
//        System.out.println(s);


        //JSON传参成功了！

    }


}
