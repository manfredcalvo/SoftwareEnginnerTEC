package com.research.MessagingPattern.roles.impl;

import com.research.MessagingPattern.roles.AbstractServer;
import com.research.MessagingPattern.instances.Result;

import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

/**
 * Created by mcalvo on 06/09/15.
 */
public class ServerSocketConnector extends SocketConnector{

    private AbstractServer server;

    public ServerSocketConnector(ExecutorService service, AbstractServer server, int portToListen){

        super(service, portToListen);

        this.server = server;
    }

    public AbstractServer getServer() {
        return server;
    }

    public void setServer(AbstractServer server) {
        this.server = server;
    }

    @Override
    public Runnable getManageClient(Socket client) {

        return new ManageClients(client);

    }

    private class ManageClients implements Runnable{


        private Socket in;
        private ObjectInputStream input;

        public ManageClients(Socket in){

            this.in = in;
        }

        public void run() {

            try {

                input = new ObjectInputStream(in.getInputStream());

                Result result = (Result)input.readObject();

                server.updateCoordinateValue(result);

                in.close();

            }catch (Throwable e){
                e.printStackTrace();
            }
        }
    }


}
