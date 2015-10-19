package com.research.MessagingPattern.roles;

import com.research.MessagingPattern.instances.Message;
import com.research.MessagingPattern.instances.Result;
import com.research.MessagingPattern.instances.Worker;

import java.util.concurrent.ExecutorService;

/**
 * Created by mcalvo on 05/09/15.
 */
public abstract class AbstractClient {

    protected AbstractConnector client;

    protected Worker worker;


    public AbstractClient(Worker worker){

        this.worker = worker;

    }

    public abstract void processTask(Message message);

    public abstract void sendResult(Result result);

    public AbstractConnector getClient() {
        return client;
    }

    public void setClient(AbstractConnector client) {
        this.client = client;
    }

    protected class ExecuteTask implements Runnable{

        private Message message;

        public ExecuteTask(Message message){
            this.message = message;
        }

        public void run() {

            int limit = message.getLimit();

            double value = 0;

            int one = 1;

            for(int x = 0; x < limit; x++){

                value += (double)one/( 2 * x + 1);

                one = -1 * one;

            }

            value = value * 4.0;

            Result result = new Result(message.getCoordinate(), value);

            sendResult(result);


        }
    }

    protected class SendMessage implements Runnable {

        private Result result;

        public SendMessage(Result result){

            this.result = result;

        }

        @Override
        public void run() {

            client.sendMessage(result, worker);

        }
    }

}
