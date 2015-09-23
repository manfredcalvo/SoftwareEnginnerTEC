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

    public ClientSocketConnector(ExecutorService service, AbstractClient client, int portToListen){
        super(service, portToListen);
        this.client = client;
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

                Message message = (Message)input.readObject();

                client.processTask(message);

                in.close();


            }catch (Throwable e){
                e.printStackTrace();
            }
        }
    }

}
