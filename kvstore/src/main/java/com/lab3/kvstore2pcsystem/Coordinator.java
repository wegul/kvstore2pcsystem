package com.lab3.kvstore2pcsystem;

import org.springframework.web.bind.annotation.GetMapping;

import java.util.Vector;

public class Coordinator {
    private String co_addr;//协调者地址
    private Vector<Participant> list;


    /**
     * 从客户端收到的是RESP格式的信息
     *
     * @return
     */
    private String recv_from_usr() {
        return null;
    }

    /**
     * 1.转发消息(request to prepare)
     * 2.接收从参与者收到的反馈（PREPARED/NOT PREPARED）
     * a.如果是PREPARED则群发COMMIT
     * b.如果是NOT PREPARED则群发ABORT
     * 3.接收DONE
     * TODO: 有的DONE接收不到，是否应该等待一次heartbeat？
     * 4.给客户端发送消息(SUCCESS/FAILED)
     *
     * @param msg
     * @return
     */
    private int twoPC(String msg) {
        return 0;
    }


    @GetMapping("/heartbeat")
    public int heartbeat(String ip, String port) {
        return 0;
    }
}
