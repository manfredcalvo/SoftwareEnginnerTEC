package com.research.MessagingPattern.roles.impl;


import com.research.MessagingPattern.roles.AbstractClient;
import com.research.MessagingPattern.instances.Message;
import com.research.MessagingPattern.instances.Result;
import com.research.MessagingPattern.instances.Worker;
import com.research.MessagingPattern.utils.PriorityExecutor;

import java.util.concurrent.ExecutorService;

/**
 * Created by mcalvo on 05/09/15.
 */

public class ClientPatternImpl extends AbstractClient {

    private PriorityExecutor messagesExecutor;
    private PriorityExecutor resultsExecutor;

    public ClientPatternImpl(Worker worker, int cpus){

        super(worker);

        this.messagesExecutor = (PriorityExecutor)PriorityExecutor.newFixedThreadPool(cpus);
        this.resultsExecutor = (PriorityExecutor)PriorityExecutor.newFixedThreadPool(cpus);

    }

    @Override
    public void processTask(Message message) {

        Runnable executeTask = new ExecuteTask(message);

        messagesExecutor.execute(executeTask);

    }

    @Override
    public void sendResult(Result result) {

        Runnable sendMessage = new SendMessage(result);

        resultsExecutor.execute(sendMessage);

    }


}
