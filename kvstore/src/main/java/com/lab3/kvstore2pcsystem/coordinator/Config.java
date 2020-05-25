package com.lab3.kvstore2pcsystem.coordinator;

import java.io.*;
import java.util.ArrayList;

public class Config {

    private String coIP;//协调者IP
    private int coPORT;//协调者端口
    public ArrayList<String> paIP;
    public ArrayList<Integer> paPORT;
    //=======================
    private String parIP;//初始化参与者时，也需要给出参与者IP和PORT
    private int parPORT;


    public void Config_intepret(String fileName) throws IOException {
        File file =new File(fileName);
        if(file.exists()){
            BufferedReader reader=new BufferedReader(new FileReader(file));
            String str=new String();
            while((str=reader.readLine())!=null){
                if(str.charAt(0) != '!'){
                    if(str.matches("^mode.+$")) {
                        if (str.indexOf("coordinator") != -1) {
                            initCO(file);
                            return;
                        } else {
                            initPA(file);
                            return;
                        }
                    }
                }
            }
        }
        else System.out.println("no such file");
    }
    public void initCO(File file) throws IOException {
        coIP=new String();
        paIP=new ArrayList<String>();
        paPORT=new ArrayList<Integer>();
        String str=new String();
        BufferedReader reader=new BufferedReader(new FileReader(file));
        while((str=reader.readLine())!=null) {
            if (str.indexOf("coordinator_info") != -1) {
                String addr = str.substring(str.indexOf(' '));
                String[] split = addr.split(":");
                coIP = split[0];
                coPORT = new Integer(split[1]);
                //System.out.println(addr);
            } else if (str.indexOf("participant_info") != -1) {
                String addr = str.substring(str.indexOf(' '));
                String[] split = addr.split(":");
                paIP.add(split[0]);
                paPORT.add(new Integer(split[1]));
                //System.out.println(addr);
            }
        }
    }
    public void initPA(File file) throws IOException {
        parIP=new String();
        String str=new String();
        BufferedReader reader=new BufferedReader(new FileReader(file));
        while((str=reader.readLine())!=null) {
            if (str.indexOf("coordinator_info") != -1) {
                String addr = str.substring(str.indexOf(' '));
                String[] split = addr.split(":");
                coIP = split[0];
                coPORT = new Integer(split[1]);
                // System.out.println(addr);
            }
            else if (str.indexOf("participant_info") != -1) {
                String addr = str.substring(str.indexOf(' '));
                String[] split = addr.split(":");
                parIP = split[0];
                parPORT = new Integer(split[1]);
                // System.out.println(addr);
            }
        }
    }
    public static void main(String[] args) throws IOException {
        Config config=new Config();
        config.Config_intepret("F:/2020云计算/George/Lab3/kvstore/src/main/resources/coordinator.conf");
    }
}
