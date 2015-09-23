package com.research.MessagingPattern.roles;

import com.research.MessagingPattern.instances.Worker;

import java.util.concurrent.ExecutorService;

/**
 * Created by mcalvo on 06/09/15.
 */
public abstract class AbstractConnector {

    protected int portToListen;

    protected ExecutorService service;

    public AbstractConnector(ExecutorService service, int portToListen){

        this.service = service;
        this.portToListen = portToListen;

    }

    public abstract void listenConnections();

    public abstract void sendMessage(Object Message, Worker worker);



}
