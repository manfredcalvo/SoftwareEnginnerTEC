package com.research.MessagingPattern.instances;

/**
 * Created by mcalvo on 05/09/15.
 */
public class Task {

    private Message message;
    private Worker worker;

    public Task(){

    }

    public Task(Message message, Worker worker) {
        this.message = message;
        this.worker = worker;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }
}
