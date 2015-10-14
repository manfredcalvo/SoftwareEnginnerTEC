package com.research.MessagingPattern.roles.impl;

import com.research.MessagingPattern.instances.Worker;
import com.research.MessagingPattern.roles.AbstractConnector;
import com.research.MessagingPattern.roles.AbstractServer;
import com.research.MessagingPattern.instances.Result;
import com.research.MessagingPattern.instances.Task;
import com.research.MessagingPattern.instances.Message;
import java.util.concurrent.*;

/**
 * Created by mcalvo on 05/09/15.
 */

public class ServerPatternImpl extends AbstractServer{

    private ExecutorService executorMessages;
    private ExecutorService executorResults;
    private static BlockingQueue<Runnable> MessageQueue;
    private static BlockingQueue<Runnable> ResultQueue;

    public ServerPatternImpl(double matrix[][]){

        super(matrix);
        MessageQueue = new PriorityBlockingQueue<Runnable>();
        ResultQueue  = new PriorityBlockingQueue<Runnable>();
        executorMessages = new ThreadPoolExecutor(10, 20, 5000, TimeUnit.MILLISECONDS, MessageQueue);
        executorResults  = new ThreadPoolExecutor(10, 20, 5000, TimeUnit.MILLISECONDS, ResultQueue);

    }

    @Override
    public void assignTaskToWorker(Task task){
        executorMessages.execute(new ProcessMessagesQueue(this.client, task));

    }


    @Override
    public void updateCoordinateValue(Result result) {
        executorResults.execute(new ProcessResultsQueue(this, result));
    }



    private static class ProcessResultsQueue implements Runnable{

        private Result result;
        private ServerPatternImpl server;

        public ProcessResultsQueue(ServerPatternImpl server, Result result) {
            this.result = result;
            this.server = server;
        }

        public void run() {
            server.updateMatrixValue(result.getX(), result.getY(), result.getValue());
        }
    }



    private static class ProcessMessagesQueue implements Runnable{

        private AbstractConnector client;
        private Message message;
        private Worker worker;

        public ProcessMessagesQueue(AbstractConnector client,Task task) {
            this.client = client;
            this.message = task.getMessage();
            this.worker = task.getWorker();
        }

        public void run() {
            client.sendMessage(this.message, this.worker);
        }
    }

}




