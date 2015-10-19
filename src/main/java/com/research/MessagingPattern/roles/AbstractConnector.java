package com.research.MessagingPattern.roles;

import com.research.MessagingPattern.instances.Worker;

import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by mcalvo on 06/09/15.
 */
public abstract class AbstractConnector {

    protected int portToListen;


    public AbstractConnector(int portToListen){
        this.portToListen = portToListen;
    }

    public abstract void listenConnections();

    public abstract void sendMessage(Object Message, Worker worker);

    public abstract void readMessage(Object message)throws Exception;



}
