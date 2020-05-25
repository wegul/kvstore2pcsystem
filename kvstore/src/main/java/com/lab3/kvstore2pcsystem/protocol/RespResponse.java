package com.lab3.kvstore2pcsystem.protocol;


import java.util.ArrayList;

//RESP响应封装类
public class RespResponse {
    //响应类型枚举
    public static int SET_OK = 201;           //SET成功
    public static int GET_OK = 202;           //GET成功
    public static int DEL_OK = 203;           //GET成功
    public static int ERROR = 204;           //发生错误
    public static int SET_COMMIT_DONE = 205;             // SET命令的commit指令执行完成
    public static int SET_ROLLBACK_DONE = 206;           // SET命令的rollback指令执行完成
    public static int DEL_COMMIT_DONE = 207;             // DEL命令的commit指令执行完成
    public static int DEL_ROLLBACK_DONE = 208;           // DEL命令的rollback指令执行完成

    private String raw;                         //响应报文字符串
    private int responseType;                   //响应类型
    private ArrayList<String> keys;             //keys
    private ArrayList<String> value;            //value
    private int keysRemoved;                    //DEL请求时删除的key数量


    public String getRaw() {
        return raw;
    }

    public void setRaw(String raw) {
        this.raw = raw;
    }

    public int getResponseType() {
        return responseType;
    }

    public void setResponseType(int responseType) {
        this.responseType = responseType;
    }

    public ArrayList<String> getKeys() {
        return keys;
    }

    public void setKeys(ArrayList<String> keys) {
        this.keys = keys;
    }

    public ArrayList<String> getValue() {
        return value;
    }

    public void setValue(ArrayList<String> value) {
        this.value = value;
    }

    public int getKeysRemoved() {
        return keysRemoved;
    }

    public void setKeysRemoved(int keysRemoved) {
        this.keysRemoved = keysRemoved;
    }

    @Override
    public String toString() {
        return "RespResponse{" +
                "row='" + raw + '\'' +
                ", responseType=" + responseType +
                ", keys=" + keys +
                ", value=" + value +
                ", keysRemoved=" + keysRemoved +
                '}';
    }
}
