package com.research.MessagingPattern.roles.impl;

import com.research.MessagingPattern.instances.Worker;
import com.research.MessagingPattern.roles.AbstractServer;
import com.research.MessagingPattern.instances.Result;
import com.research.MessagingPattern.instances.Task;
import com.research.MessagingPattern.instances.Message;
import com.research.MessagingPattern.utils.PriorityExecutor;

import java.util.concurrent.*;

/**
 * Created by mcalvo on 05/09/15.
 */

public class ServerPatternImpl extends AbstractServer{

    private PriorityExecutor executorMessages;
    private PriorityExecutor executorResults;


    public ServerPatternImpl(double matrix[][], int cpus){

        super(matrix);
        executorMessages = (PriorityExecutor)PriorityExecutor.newFixedThreadPool(cpus);
        executorResults  = (PriorityExecutor)PriorityExecutor.newFixedThreadPool(cpus);

    }

    @Override
    public void assignTaskToWorker(Task task){
        executorMessages.execute(new ProcessMessagesQueue(task));

    }


    @Override
    public void updateCoordinateValue(Result result) {

        executorResults.execute(new ProcessResultsQueue(this, result));
    }

    public void shutdownExecutors(){

        executorMessages.shutdown();
        executorResults.shutdown();

    }

    private class ProcessResultsQueue implements Runnable{

        private Result result;
        private ServerPatternImpl server;

        public ProcessResultsQueue(ServerPatternImpl server, Result result) {
            this.result = result;
            this.server = server;
        }

        public void run() {
            updateMatrixValue(result.getX(), result.getY(), result.getValue());
        }
    }

    private class ProcessMessagesQueue implements Runnable{


        private Message message;
        private Worker worker;

        public ProcessMessagesQueue(Task task) {
            this.message = task.getMessage();
            this.worker = task.getWorker();
        }

        public void run() {
            client.sendMessage(this.message, this.worker);
        }
    }

}