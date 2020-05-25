package com.lab3.kvstore2pcsystem.utils;


import com.lab3.kvstore2pcsystem.protocol.RespRequest;
import com.lab3.kvstore2pcsystem.protocol.RespResponse;

import java.util.ArrayList;

//解析RESP报文
public class RespParseUtil {
    public static void main(String[] args) {
        String respStr = "*4\r\n$3\r\nSET\r\n$7\r\nCS06142\r\n$5\r\nCloud\r\n$9\r\nComputing\r\n";
        String respStr2 = "*3\r\n$3\r\nDEL\r\n$7\r\nCS06142\r\n$5\r\nCS162\r\n";
        RespRequest respRequest = parseRequest(respStr2);

    }

    /**
     * 解析RESP请求报文
     *
     * @param respStr
     * @return
     */
    public static RespRequest parseRequest(String respStr) {
        //RESP请求报文格式如下：
        //第一行 *n\r\n   等于本报文的key和value的总数加1 （注意value的数量与字符串中空格数量有关系）
        //第二行 $3\r\n   表示报文类型的长度 本次实验仅要求 SET GET DEL 所以长度都是3
        //第三行 $m\r\n   表示第一个参数字符串的长度
        //第x行  $m\r\n   表示这个参数字符串的长度

        //根据实验指导书：
        //对于SET请求  当客户端输入 SET key "apple banana cat"时  value这个字符串会被拆成三个部分
        //变成*5\r\n$3\r\nSET\r\n$key的长度\r\nkey\r\n$1\r\napple$2\r\nbanana$3\r\ncat 这里解析的时候要注意

        //对于GET请求  客户端只能输入 GET key 解析之后一定是 *2\r\n$3\r\nGET\r\n$key的长度\r\nkey

        //对于DEL请求  客户端能够输入 DEL key1 key2 key3 ... 解析时第一行的*后是x 就表示有x-1个key
        //参数无效
        if (respStr == null || respStr.isEmpty()) {
            return null;
        }

        RespRequest request = new RespRequest();
        String[] split = respStr.split("\r\n");
        //传入参数不正确       按照\r\n划分之后 至少都是五个字符串 参数计数、method长度、method、key长度、key 少于5个就一定是错误的请求
        if (split.length < 5) {
            return null;
        }

        //首先解析第一行
        char c0 = split[0].charAt(0);
        if (c0 != '*') {
            return null;
        }
        int paramCount = 0;
        try {
            paramCount = Integer.valueOf(split[0].substring(1));

            //校验参数总长度是否正确   1 + 2 * 参数数量 = split[]的长度
            if (split.length != 1 + 2 * paramCount) {
                return null;
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return null;
        }

        //共有paramCount这么多个参数 把split[1]开始 每两个字符串分成一组进行抽取
        //先单独提取method
        c0 = split[1].charAt(0);
        if (c0 != '$') {
            return null;
        }
        int paramLength = 0;
        try {
            paramLength = Integer.valueOf(split[1].substring(1));
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return null;
        }
        if (split[2].length() != paramLength) {
            return null;
        }
        int i = RespRequest.METHOD.codeOf(split[2]);
        if (i == -1) {
            return null;
        }
        request.setRequestType(i);
        request.setMethod(split[2]);

        //SET 一个key 多个value
        if (request.getRequestType() == RespRequest.METHOD.SET.getCode()) {
            //先单独提取key
            c0 = split[3].charAt(0);
            if (c0 != '$') {
                return null;
            }
            try {
                paramLength = Integer.valueOf(split[3].substring(1));
            } catch (NumberFormatException e) {
                e.printStackTrace();
                return null;
            }
            if (split[4].length() != paramLength) {
                return null;
            }
            ArrayList<String> list = new ArrayList<>();
            list.add(split[4]);
            request.setKeys(list);
            //提取values
            request.setValues(new ArrayList<String>());
            for (int j = 5; j < split.length; j += 2) {
                c0 = split[j].charAt(0);
                if (c0 != '$') {
                    return null;
                }
                paramLength = 0;
                try {
                    paramLength = Integer.valueOf(split[j].substring(1));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    return null;
                }
                if (split[j + 1].length() != paramLength) {
                    return null;
                }
                request.getValues().add(split[j + 1]);
            }
        }

        //GET 一个key
        else if (request.getRequestType() == RespRequest.METHOD.GET.getCode()) {
            //先单独提取key
            c0 = split[3].charAt(0);
            if (c0 != '$') {
                return null;
            }
            try {
                paramLength = Integer.valueOf(split[3].substring(1));
            } catch (NumberFormatException e) {
                e.printStackTrace();
                return null;
            }
            if (split[4].length() != paramLength) {
                return null;
            }
            ArrayList<String> list = new ArrayList<>();
            list.add(split[4]);
            request.setKeys(list);
        }
        //DEL 多个key
        else if (request.getRequestType() == RespRequest.METHOD.DEL.getCode()) {
            //提取key
            request.setKeys(new ArrayList<String>());
            for (int j = 3; j < split.length; j += 2) {
                c0 = split[j].charAt(0);
                if (c0 != '$') {
                    return null;
                }
                try {
                    paramLength = Integer.valueOf(split[j].substring(1));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    return null;
                }
                if (split[j + 1].length() != paramLength) {
                    return null;
                }
                request.getKeys().add(split[j + 1]);
            }
        }
        //请求类型不支持
        else {

        }

        request.setRaw(respStr);
        request.setTransactionNo(request.generateTransactionNo());  //生成一个事务号
        return request;
    }

    /**
     * 解析RESP响应 生成响应字符串
     *
     * @param respResponse
     * @return
     */
    public static String parseResponse(RespResponse respResponse) {
        return null;
    }

}
