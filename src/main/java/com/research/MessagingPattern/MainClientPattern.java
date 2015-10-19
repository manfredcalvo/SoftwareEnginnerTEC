package com.research.MessagingPattern;

import com.research.MessagingPattern.instances.Mode;
import com.research.MessagingPattern.roles.AbstractClient;
import com.research.MessagingPattern.roles.AbstractConnector;
import com.research.MessagingPattern.roles.impl.ClientNoPatternImpl;
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

        args = new String[4];
        args[0] = "127.0.0.1:8030";
        args[1] = "2";
        args[2] = "50004";
        args[3] = "PATTERN";

        String serverIpPort = args[0];

        int cpus = Integer.parseInt(args[1]);

        int port = Integer.parseInt(args[2]);

        Mode mode = Mode.valueOf(args[3]);

        String ipPort[] = serverIpPort.split(":");

        Worker worker = new Worker(ipPort[0], Integer.parseInt(ipPort[1]));
        
        AbstractClient client = null;

        switch (mode) {
            case PATTERN:   client = new ClientPatternImpl(worker, cpus);break;
            case NO_PATTERN: client = new ClientNoPatternImpl(worker); break;

        }

        AbstractConnector connector = new ClientSocketConnector(client, port);

        client.setClient(connector);

        connector.listenConnections();


    }
}
