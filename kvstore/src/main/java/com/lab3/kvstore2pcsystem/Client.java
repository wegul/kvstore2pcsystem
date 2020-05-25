package com.lab3.kvstore2pcsystem;

import java.util.Scanner;

public class Client {
    private String msg;
    private String RESP;

    public String input() {
        Scanner s = new Scanner(System.in);
        msg = s.nextLine();
        //TODO: pack it into RESP
        RESP = msg;
        return RESP;
    }

    public int send2co() {
        return 0;
    }
}
