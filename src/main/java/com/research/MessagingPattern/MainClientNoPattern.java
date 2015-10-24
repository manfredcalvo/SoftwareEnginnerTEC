package com.research.MessagingPattern;

import com.research.MessagingPattern.instances.Worker;
import com.research.MessagingPattern.roles.AbstractClient;
import com.research.MessagingPattern.roles.AbstractConnector;
import com.research.MessagingPattern.roles.impl.ClientNoPatternImpl;
import com.research.MessagingPattern.roles.impl.ClientSocketConnector;

/**
 * Created by mcalvo on 06/09/15.
 */
public class MainClientNoPattern {

    public static void main(String []args){

        args = new String[3];
        args[0] = "127.0.0.1:8030";
        args[1] = "1";
        args[2] = "50003";

        String serverIpPort = args[0];

        int cpus = Integer.parseInt(args[1]);

        int port = Integer.parseInt(args[2]);

        String ipPort[] = serverIpPort.split(":");

        Worker worker = new Worker(ipPort[0], Integer.parseInt(ipPort[1]));

        AbstractClient client = new ClientNoPatternImpl(worker);

        AbstractConnector connector = new ClientSocketConnector(client, port);

        client.setClient(connector);

        connector.listenConnections();

    }
}
