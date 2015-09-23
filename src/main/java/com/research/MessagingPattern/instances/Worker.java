package com.research.MessagingPattern.instances;

/**
 * Created by mcalvo on 05/09/15.
 */
public class Worker {

    private String ip;
    private int port;

    public Worker(){

    }

    public Worker(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
