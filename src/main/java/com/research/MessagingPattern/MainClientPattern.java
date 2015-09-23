package com.research.MessagingPattern;

import com.research.MessagingPattern.roles.AbstractClient;
import com.research.MessagingPattern.roles.AbstractConnector;
import com.research.MessagingPattern.roles.impl.ClientPatternImpl;
import com.research.MessagingPattern.roles.impl.ClientSocketConnector;
import com.research.MessagingPattern.instances.Worker;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by mcalvo on 06/09/15.
 */
public class MainClientPattern {

    public static void main(String []args){

        args = new String[3];
        args[0] = "127.0.0.1:8030";
        args[1] = "1";
        args[2] = "50003";


        String serverIpPort = args[0];

        int cpus = Integer.parseInt(args[1]);

        int port = Integer.parseInt(args[2]);

        ExecutorService service = Executors.newFixedThreadPool(cpus);

        String ipPort[] = serverIpPort.split(":");

        Worker worker = new Worker(ipPort[0], Integer.parseInt(ipPort[1]));
        
        AbstractClient client = new ClientPatternImpl(service, worker);

        AbstractConnector connector = new ClientSocketConnector(service, client, port);

        client.setClient(connector);

        connector.listenConnections();


    }
}
