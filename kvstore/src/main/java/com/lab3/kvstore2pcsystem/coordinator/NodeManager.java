package com.lab3.kvstore2pcsystem.coordinator;

import com.lab3.kvstore2pcsystem.Participant;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//参与者节点管理中心
@Component
public class NodeManager {
    public static HashMap<Participant, Date> participants = new HashMap<Participant, Date>();

    //获取当前存活的参与者列表
    public static ArrayList<Participant> getAliveParticipantList() {
        ArrayList<Participant> aliveParticipants = new ArrayList<Participant>();
        for (Map.Entry<Participant, Date> entry : participants.entrySet()) {
            //数据节点存活
            if (new Date().getTime() - entry.getValue().getTime() < Const.ALIVE_CHECK_INTERVAL) {
                aliveParticipants.add(entry.getKey());
            }
            //数据节点心跳不及时  认为已经掉线 删除
            else {
                participants.remove(entry.getKey());
            }
        }
        return aliveParticipants;
    }

    //刷新数据节点的活跃时间
    public static int refreshAliveNode(String ip, String port) {
        for (Map.Entry<Participant, Date> entry : participants.entrySet()) {
            Participant key = entry.getKey();
            if (key.getCo_addr().equals(ip) && key.getPort().equals(port)) {
                entry.setValue(new Date());
                return 1;
            }
        }
        //没有找到需要刷新的节点
        return 0;
    }

    //检查数据节点活跃性
    public static void checkAlive() {
        for (Map.Entry<Participant, Date> entry : participants.entrySet()) {
            //数据节点心跳不及时  认为已经掉线 删除
            if (new Date().getTime() - entry.getValue().getTime() >= Const.ALIVE_CHECK_INTERVAL) {
                participants.remove(entry.getKey());
            }
        }
    }


}
