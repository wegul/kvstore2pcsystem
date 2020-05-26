package com.lab3.kvstore2pcsystem.utils;

import com.alibaba.fastjson.JSON;
import com.lab3.kvstore2pcsystem.protocol.RespRequest;
import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;


public class HttpClientUtils {
    public static final String charsetName = "UTF-8";

    public static String doGet(String url) {
        /*
         * 使用 GetMethod 来访问一个 URL 对应的网页,实现步骤: 1:生成一个 HttpClinet 对象并设置相应的参数。
         * 2:生成一个 GetMethod 对象并设置响应的参数。 3:用 HttpClinet 生成的对象来执行 GetMethod 生成的Get
         * 方法。 4:处理响应状态码。 5:若响应正常，处理 HTTP 响应内容。 6:释放连接。
         */

        /* 1 生成 HttpClinet 对象并设置参数 */
        HttpClient httpClient = new HttpClient();
        // 设置 Http 连接超时为5秒
        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);

        /* 2 生成 GetMethod 对象并设置参数 */
        GetMethod getMethod = new GetMethod(url);
        // 设置 get 请求超时为 5 秒
        getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 5000);
        // 设置请求重试处理，用的是默认的重试处理：请求三次
        getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());

        String out = null;
        /* 3 执行 HTTP GET 请求 */
        try {
            int statusCode = httpClient.executeMethod(getMethod);
            /* 4 判断访问的状态码 */
            if (statusCode == HttpStatus.SC_OK) {
                // 读取为 InputStream，在网页内容数据量大时候推荐使用
                out = inToString(getMethod.getResponseBodyAsStream());
            }
        } catch (HttpException e) {
            // 发生致命的异常，可能是协议不对或者返回的内容有问题
            e.printStackTrace();
        } catch (IOException e) {
            // 发生网络异常
            e.printStackTrace();
        } finally {
            /* 6 .释放连接 */
            getMethod.releaseConnection();
        }
        return out;
    }

    public static String doPost(String url, Map<String, String> params) {

        String out = null;
        HttpClient client = new HttpClient();

        // 设置Http Post数据
        if (params != null) {
            char c = '?';
            for (Map.Entry<String, String> entry : params.entrySet()) {
                url += c;
                c = '&';
                url += entry.getKey() + "=" + entry.getValue();
            }
            System.out.println(url);
        }

        HttpMethod method = new PostMethod(url);

        try {
            client.executeMethod(method);
            if (method.getStatusCode() == HttpStatus.SC_OK) {
                // 读取为 InputStream，在网页内容数据量大时候推荐使用
                out = inToString(method.getResponseBodyAsStream());
            } else {
                //响应码不为200 认为请求失败 数据节点挂了
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            method.releaseConnection();
        }
        return out;
    }


    //post请求参数为json格式
    public static String HttpPostWithJson(String url, String json) {
        String returnValue = "这是默认返回值，接口调用失败";
        CloseableHttpClient httpClient = HttpClients.createDefault();
        ResponseHandler<String> responseHandler = new BasicResponseHandler();
        try {
            //第一步：创建HttpClient对象
            httpClient = HttpClients.createDefault();

            //第二步：创建httpPost对象
            HttpPost httpPost = new HttpPost(url);

            //第三步：给httpPost设置JSON格式的参数
            StringEntity requestEntity = new StringEntity(json, "utf-8");
            requestEntity.setContentEncoding("UTF-8");
            httpPost.setHeader("Content-type", "application/json");
            httpPost.setEntity(requestEntity);

            //第四步：发送HttpPost请求，获取返回值
            returnValue = httpClient.execute(httpPost, responseHandler); //调接口获取返回值时，必须用此方法

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                httpClient.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        //第五步：处理返回值
        return returnValue;
    }


    private static String inToString(InputStream inputStream) throws IOException {
        ByteArrayOutputStream result = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int length;
        while ((length = inputStream.read(buffer)) != -1) {
            result.write(buffer, 0, length);
        }
        String str = result.toString(charsetName);
        return str;
    }

    public static void main(String[] args) {
//        String result = doGet("https://blog.csdn.net/zhangsweet1991");
//        System.out.println(result);
//        RespRequest respRequest = new RespRequest();
//        HashMap<String, String> stringStringHashMap = new HashMap<>();
//        stringStringHashMap.put("param", JSON.toJSONString(respRequest));
//        String s = HttpClientUtils.HttpPostWithJson("http://localhost:8088/kvstore/set", JSON.toJSONString(respRequest));
//        System.out.println(s);
    }
}