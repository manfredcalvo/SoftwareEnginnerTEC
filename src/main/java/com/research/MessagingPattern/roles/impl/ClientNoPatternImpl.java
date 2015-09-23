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


    public ClientNoPatternImpl(ExecutorService service, Worker worker){
        super(service, worker);
    }

    @Override
    public void processTask(Message message) {

        ExecuteTask task = new ExecuteTask(message);

        service.execute(task);

    }

    @Override
    public void sendResult(Result result) {

        client.sendMessage(result, worker);

    }


}
