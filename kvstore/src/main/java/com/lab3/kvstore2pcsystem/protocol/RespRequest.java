package com.lab3.kvstore2pcsystem.protocol;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

//RESP请求封装类
public class RespRequest {
    //请求类型枚举
    public static int SET = 101;      //存储
    public static int GET = 102;      //查询
    public static int DEL = 103;      //删除

    //SET请求的参数为 key value
    //GET请求的参数为 key
    //DEL请求的参数为 key1 key2 key3 ...

    private String raw;                       //请求报文字符串
    private int requestType;                  //请求类型
    private String method;                    //请求类型名
    private ArrayList<String> keys;           //keys
    private ArrayList<String> values;         //value
    private String transactionNo;                //事务编号

    public String getRaw() {
        return raw;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }

    public int getRequestType() {
        return requestType;
    }

    public void setRequestType(int requestType) {
        this.requestType = requestType;
    }

    public ArrayList<String> getKeys() {
        return keys;
    }

    public void setKeys(ArrayList<String> keys) {
        this.keys = keys;
    }

    public ArrayList<String> getValues() {
        return values;
    }

    public void setValues(ArrayList<String> values) {
        this.values = values;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getTransactionNo() {
        return transactionNo;
    }

    public void setTransactionNo(String transactionNo) {
        this.transactionNo = transactionNo;
    }

    public enum METHOD {
        SET("SET", 101), GET("GET", 102), DEL("DEL", 103);
        private String Name;
        private int code;

        METHOD(String name, int code) {
            Name = name;
            this.code = code;
        }

        public static String nameOf(int code) {
            for (METHOD method : values()) {
                if (method.getCode() == code) {
                    return method.getName();
                }
            }
            return null;
        }

        public static int codeOf(String name) {
            for (METHOD method : values()) {
                if (method.getName().equals(name)) {
                    return method.getCode();
                }
            }
            return -1;
        }

        public static METHOD enumOf(int requestType) {
            for (METHOD method : values()) {
                if (method.getCode() == (requestType)) {
                    return method;
                }
            }
            return null;
        }


        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }
    }

    @Override
    public String toString() {
        return "RespRequest{" +
                "raw='" + raw + '\'' +
                ", requestType=" + requestType +
                ", method='" + method + '\'' +
                ", keys=" + keys +
                ", values=" + values +
                ", transactionNo='" + transactionNo + '\'' +
                '}';
    }

    //生成唯一事务号
    public String generateTransactionNo() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date();
        String format = sdf.format(date);
        Random random = new Random();
        return format + random.nextInt(1000);
    }
}
