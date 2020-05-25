package com.lab3.kvstore2pcsystem;

import org.springframework.web.bind.annotation.GetMapping;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        String co_addr = "localhost:8080";
        Client client = new Client();
        while (true) {
            String str = new String(client.input());
            System.out.println(str);
        }
    }

    public static String input() {
        String msg = new String();
        String RESP = new String();
        Scanner s = new Scanner(System.in);
        msg = s.nextLine();
        String len = "" + msg.length();
        RESP = "$" + String.valueOf(msg.length()) + "\r\n" + msg + "\r\n";
        return RESP;
    }

    public int send2co(String RESP) {
        try {
            Socket socket = new Socket("localhost", 8080);
            OutputStream output = socket.getOutputStream();
            output.write(RESP.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int recv_from_co() {
        return 0;
    }

}
