package com.research.MessagingPattern.roles.impl;

import com.research.MessagingPattern.roles.AbstractServer;
import com.research.MessagingPattern.instances.Result;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.logging.Logger;

/**
 * Created by mcalvo on 06/09/15.
 */
public class ServerSocketConnector extends SocketConnector{

    private AbstractServer server;
    private long totalConnections ;
    private Logger logger = Logger.getLogger(ServerSocketConnector.class.getName());

    public ServerSocketConnector(AbstractServer server, int portToListen, long totalConnections){

        super(portToListen);

        this.server = server;
        this.totalConnections = totalConnections;
    }

    public AbstractServer getServer() {
        return server;
    }

    public void setServer(AbstractServer server) {
        this.server = server;
    }

    @Override
    public void listenConnections() {

        ServerSocket serverSocket = null;

        try {

            logger.info("Socket listen connections");

            serverSocket = new ServerSocket(portToListen);

            int x = 0;

            while (x < totalConnections) {

                try {

                    Socket clientSocket = serverSocket.accept();

                    logger.info("Connecting with client: " + clientSocket.getInetAddress().getHostAddress());

                    readMessage(clientSocket);

                    x++;

                }catch (Throwable e){
                    e.printStackTrace();
                }

            }



        }catch (Throwable e){
            e.printStackTrace();
        }finally {
            if(serverSocket != null){
                try {
                    serverSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @Override
    public void readMessage(Object message) throws Exception {

        Socket in = (Socket) message;

        ObjectInputStream input = new ObjectInputStream(in.getInputStream());

        Result result = (Result)input.readObject();

        in.close();

        server.updateCoordinateValue(result);



    }


}
