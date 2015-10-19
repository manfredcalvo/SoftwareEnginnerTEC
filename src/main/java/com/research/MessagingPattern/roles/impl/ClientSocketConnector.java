package com.research.MessagingPattern.roles.impl;

import com.research.MessagingPattern.roles.AbstractClient;
import com.research.MessagingPattern.instances.Message;

import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

/**
 * Created by mcalvo on 06/09/15.
 */
public class ClientSocketConnector extends SocketConnector{

    private AbstractClient client;

    public ClientSocketConnector( AbstractClient client, int portToListen){
        super(portToListen);
        this.client = client;
    }

    @Override
    public void readMessage(Object message)throws Exception{

        Socket in = (Socket)message;

        ObjectInputStream input= new ObjectInputStream(in.getInputStream());

        Message objectMessage = (Message) input.readObject();

        client.processTask(objectMessage);

        in.close();


    }

}
