package org.example.network;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Main {
    public static void main(String[] args) throws UnknownHostException {
        // TODO:
        //  Inet4Address - IPv4
        //  Inet6Address - IPv6
        //  host -> device with address
        InetAddress address = InetAddress.getByName("ya.ru");
        System.out.println("address = " + address);
        InetAddress localHost = InetAddress.getLocalHost();
        System.out.println("localHost = " + localHost);
    }
}
