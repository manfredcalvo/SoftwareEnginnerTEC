package com.research.MessagingPattern.roles.impl;


import com.research.MessagingPattern.roles.AbstractClient;
import com.research.MessagingPattern.instances.Message;
import com.research.MessagingPattern.instances.Result;
import com.research.MessagingPattern.instances.Worker;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.ExecutorService;

/**
 * Created by mcalvo on 05/09/15.
 */

public class ClientPatternImpl extends AbstractClient {

    private Queue<Message> messages;
    private Queue<Result> results;

    public ClientPatternImpl(ExecutorService service, Worker worker){
        super(service, worker);
        this.messages = new ArrayDeque<Message>();
        this.results = new ArrayDeque<Result>();

        Thread t = new Thread(new ProcessMessagesQueue());
        t.start();
        Thread t1 = new Thread(new ProcessResultsQueue());
        t1.start();


    }

    private synchronized boolean messagesIsEmpty(){

        return messages.isEmpty();

    }

    private synchronized boolean resultsIsEmpty(){

        return results.isEmpty();

    }

    private synchronized void addToResultQueue(Result result){

        results.add(result);

    }

    private synchronized Result getResultOfQueue(){

        return results.poll();

    }

    private synchronized Message getMessageOfQueue(){

        return messages.poll();

    }


    private synchronized void addToMessageQueue(Message message){
        messages.add(message);
    }

    @Override
    public void processTask(Message message) {

        addToMessageQueue(message);
    }

    @Override
    public void sendResult(Result result) {

        addToResultQueue(result);
    }


    private class ProcessMessagesQueue implements Runnable{

        public ProcessMessagesQueue(){

        }

        public void run() {

            while(true) {

                if(!messagesIsEmpty()) {

                    ExecuteTask task = new ExecuteTask(getMessageOfQueue());

                    service.execute(task);

                }
            }

        }
    }

    private class ProcessResultsQueue implements Runnable{

        public void run() {

            while(true){

                if(!resultsIsEmpty()){

                    client.sendMessage(getResultOfQueue(), worker);

                }

            }

        }
    }


}
