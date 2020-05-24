package com.lab3.kvstore2pcsystem;

import java.util.HashMap;
import java.util.Stack;

public class Participant {
    private String co_addr;//协调者地址
    private String ip;//参与者IP
    private String port;//参与者端口
    private HashMap<String, String> map;//内存数据库
    private Stack<Job> log;//日志

    /**
     * 解析协调者发给参与者的RESP消息，并调用各方法：添加KV、删除KV、返回KV、回滚（即删除栈顶job）
     *
     * @param msg
     * @return
     */
    private Job parse(String msg) {
        return null;
    }

    /**
     * 处理job，job内含任务信息（K V 还有method）
     *
     * @param job
     * @return 1 if succeed(prepared), 0 if fail(not prepared)
     */
    private int prepared(Job job) {
        return 0;
    }

    /**
     * 将参与者的状态发给协调者（PREPARED/NOT PREPARED/DONE）
     *
     * @param code
     * @return
     */
    private int send_p2c(String code) {
        return 0;
    }

    /**
     * 从协调者收到消息（任务/状态码），交给parse处理
     *
     * @return
     */
    private String recv_from_co() {
        return "";
    }

}
