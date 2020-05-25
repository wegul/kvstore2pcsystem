package com.lab3.kvstore2pcsystem.coordinator;


import com.alibaba.fastjson.JSON;
import com.lab3.kvstore2pcsystem.Participant;
import com.lab3.kvstore2pcsystem.protocol.RespRequest;
import com.lab3.kvstore2pcsystem.protocol.RespResponse;
import com.lab3.kvstore2pcsystem.utils.HttpClientUtils;

import java.util.HashMap;
import java.util.concurrent.Callable;

//向某一个参与者发送指令的线程 在spirngboot启动类中创建执行com.lab3.kvstore2pcsystem.Kvstore2pcsystemApplication
public class RequestToPrepareRunner implements Callable<RespResponse> {
    //封装一个参与者作为参数
    private Participant participant;
    //当是SET GET DEL指令时 该参数启用
    private RespRequest respRequest;
    //command有两种类型 SET GET DEL的操作指令 以及COMMIT和ROLLBACK事务指令
    private String command;
    //用于指明回滚或提交时的事务号
    private String extraParam;

    public RequestToPrepareRunner(Participant participant, RespRequest respRequest, String command, String extraParam) {
        this.participant = participant;
        this.respRequest = respRequest;
        this.command = command;
        this.extraParam = extraParam;
    }

    @Override
    public RespResponse call() throws Exception {
        RespResponse respResponse = sendCommand2Paticipant(participant, respRequest, command, extraParam);
        return respResponse;
    }

    private RespResponse sendCommand2Paticipant(Participant participant, RespRequest respRequest, String command, String extraParam) {
        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("respRequest", JSON.toJSONString(respRequest));
        stringStringHashMap.put("extraParam", extraParam);
        String s = null;
        switch (command) {
            case "SET": {
                s = HttpClientUtils.doPost("http://" + participant.getCo_addr() + ":" + participant.getIp() + "/kvstore/set", stringStringHashMap);
                break;
            }
            case "GET": {
                s = HttpClientUtils.doPost("http://localhost:8081/kvstore/get", stringStringHashMap);
                break;
            }
            case "DEL": {
                s = HttpClientUtils.doPost("http://localhost:8081/kvstore/del", stringStringHashMap);
                break;
            }
            case "COMMIT": {
                s = HttpClientUtils.doPost("http://localhost:8081/kvstore/commit", stringStringHashMap);
                break;
            }
            case "ROLLBACK": {
                s = HttpClientUtils.doPost("http://localhost:8081/kvstore/rollback", stringStringHashMap);
                break;
            }
            default:
                return null;
        }
        //如果s是不完整的或者是空的 解析出来的RespResponse将是null
        return JSON.parseObject(s, RespResponse.class);
    }


}