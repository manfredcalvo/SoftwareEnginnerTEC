package com.research.MessagingPattern.roles.impl;

import com.research.MessagingPattern.roles.AbstractConnector;
import com.research.MessagingPattern.instances.Worker;

import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

/**
 * Created by mcalvo on 06/09/15.
 */
public abstract class SocketConnector extends AbstractConnector{


    public SocketConnector(ExecutorService service, int portToListen){

        super(service, portToListen);
    }

    public abstract Runnable getManageClient(Socket client);

    @Override
    public void listenConnections(){

        try {

            ServerSocket serverSocket = new ServerSocket(portToListen);

            while (true) {

                Socket clientSocket = serverSocket.accept();

                Runnable manageClient = getManageClient(clientSocket);

                service.execute(manageClient);

            }


        }catch (Throwable e){
            e.printStackTrace();
        }

    }

    @Override
    public void sendMessage(Object message, Worker worker) {

        try {

            Socket client = new Socket(worker.getIp(), worker.getPort());

            ObjectOutputStream output = new ObjectOutputStream(client.getOutputStream());

            output.writeObject(message);

            client.close();

        }catch (Throwable e){
            e.printStackTrace();
        }


    }


}
