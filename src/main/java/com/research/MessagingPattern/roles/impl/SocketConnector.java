package com.research.MessagingPattern.roles.impl;

import com.research.MessagingPattern.instances.Message;
import com.research.MessagingPattern.roles.AbstractConnector;
import com.research.MessagingPattern.instances.Worker;
import org.javatuples.Pair;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Logger;


/**
 * Created by mcalvo on 06/09/15.
 */

public abstract class SocketConnector extends AbstractConnector{


    private Logger logger = Logger.getLogger(SocketConnector.class.getName());

    public SocketConnector( int portToListen){

        super(portToListen);
    }


    @Override
    public void listenConnections(){

        try {

            logger.info("Socket listen connections");

            ServerSocket serverSocket = new ServerSocket(portToListen);

            while (true) {

                Socket clientSocket = serverSocket.accept();

                logger.info("Connecting with client: " + clientSocket.getInetAddress().getHostAddress());

                readMessage(clientSocket);

            }

        }catch (Throwable e){
            e.printStackTrace();

        }

    }


    @Override
    public void sendMessage(Object message, Worker worker) {

        try {

            Socket client = new Socket();

            client.connect(new InetSocketAddress(worker.getIp(),worker.getPort()));

            ObjectOutputStream output = new ObjectOutputStream(client.getOutputStream());

            output.writeObject(message);

            client.close();

        }catch (Throwable e){
            e.printStackTrace();
        }
    }
}

