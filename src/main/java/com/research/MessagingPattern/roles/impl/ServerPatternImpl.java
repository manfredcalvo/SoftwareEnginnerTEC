package com.research.MessagingPattern.roles.impl;

import com.research.MessagingPattern.roles.AbstractServer;
import com.research.MessagingPattern.instances.Result;
import com.research.MessagingPattern.instances.Task;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Created by mcalvo on 05/09/15.
 */

public class ServerPatternImpl extends AbstractServer{

    private Queue<Task> tasks;
    private Queue<Result> results;

    public ServerPatternImpl(double matrix[][]){

        super(matrix);
        tasks = new ArrayDeque<Task>();
        results = new ArrayDeque<Result>();

        Thread thread = new Thread(new ProcessTasksQueue());
        thread.start();
        Thread thread1 = new Thread(new ProcessResultsQueue());
        thread1.start();
    }

    private synchronized void addToResultsQueue(Result result){

        results.add(result);

    }

    private synchronized Result getResultOfQueue(){

        return results.poll();

    }


    private synchronized void addToTaskToQueue(Task task){

        tasks.add(task);

    }

    private synchronized boolean tasksIsEmpty(){

        return tasks.isEmpty();

    }

    private synchronized boolean resultIsEmpty(){

        return results.isEmpty();

    }

    private synchronized Task getTaskOfQueue(){

        return tasks.poll();

    }

    @Override
    public void assignTaskToWorker(Task task){

        addToTaskToQueue(task);

    }

    @Override
    public void updateCoordinateValue(Result result) {

        addToResultsQueue(result);

    }


    private class ProcessTasksQueue implements Runnable{


        public ProcessTasksQueue(){

        }

        public void run() {

            while(true) {

                if(!tasksIsEmpty()) {

                    Task task = getTaskOfQueue();

                    client.sendMessage(task.getMessage(), task.getWorker());

                }
            }

        }
    }

    private class ProcessResultsQueue implements Runnable{

        public void run() {

            while(true){

                if(!resultIsEmpty()){

                    Result result = getResultOfQueue();

                    updateMatrixValue(result.getX(), result.getY(), result.getValue());


                }


            }

        }
    }





}
