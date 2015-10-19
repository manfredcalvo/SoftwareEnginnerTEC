package com.research.MessagingPattern.roles.impl;

import com.research.MessagingPattern.roles.AbstractClient;
import com.research.MessagingPattern.instances.Message;
import com.research.MessagingPattern.instances.Result;
import com.research.MessagingPattern.instances.Worker;

import java.util.concurrent.ExecutorService;

/**
 * Created by mcalvo on 05/09/15.
 */
public class ClientNoPatternImpl extends AbstractClient{


    public ClientNoPatternImpl( Worker worker){

        super(worker);
    }


    @Override
    public void processTask(Message message) {

        Runnable executeTask = new ExecuteTask(message);

        Thread t = new Thread(executeTask);

        t.start();

    }


    @Override
    public void sendResult(Result result) {

        Runnable sendMessage = new SendMessage(result);

        Thread t = new Thread(sendMessage);

        t.start();

    }


}
