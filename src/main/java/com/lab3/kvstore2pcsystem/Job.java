package com.lab3.kvstore2pcsystem;

public class Job {
    private String method;
    private String key;
    private String value;

    /**
     * 解析协调者发给参与者的RESP消息
     *
     * @param RESP
     * @return
     */
    public Job(String RESP) {
        method = new String();
        key = new String();
        value = new String();
        String[] split = RESP.split("\r\n");
        method = split[2];
        key = split[4];
        for (int i = 6; i < split.length; i += 2) {
            value += split[i];
        }
        System.out.println(method + " =" + key + "  =" + value);
    }

    public static void main(String[] args) {
        Job job = new Job("*3\r\n$3\r\nDEL\r\n$7\r\nCS06142\r\n$5\r\nCS162\r\n");
    }
}
