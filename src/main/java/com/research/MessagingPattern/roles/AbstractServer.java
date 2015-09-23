package com.research.MessagingPattern.roles;

import com.research.MessagingPattern.instances.Result;
import com.research.MessagingPattern.instances.Task;


/**
 * Created by mcalvo on 05/09/15.
 */
public abstract class AbstractServer {

    private double matrix[][];

    protected AbstractConnector client;

    public AbstractServer(double matrix[][]){
        this.matrix = matrix;
    }

    protected void updateMatrixValue(int x, int y, double value){

        matrix[x][y] = value;

    }

    public abstract void assignTaskToWorker(Task task);

    public abstract void updateCoordinateValue(Result result);


    public AbstractConnector getClient() {
        return client;
    }

    public void setClient(AbstractConnector client) {
        this.client = client;
    }
}
