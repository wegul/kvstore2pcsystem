package com.lab3.kvstore2pcsystem.coordinator;

import java.util.Timer;
import java.util.TimerTask;

//数据节点活跃检测线程 在springboot启动类中创建并执行 com.lab3.kvstore2pcsystem.Kvstore2pcsystemApplication
public class CheckNodeAliveRunner implements Runnable {
    @Override
    public void run() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                NodeManager.checkAlive();
            }
        }, Const.ALIVE_CHECK_INTERVAL, Const.ALIVE_CHECK_INTERVAL);   //30秒后开始第一次检查 之后每隔30秒一次
    }
}
