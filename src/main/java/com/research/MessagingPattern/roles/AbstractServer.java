package com.research.MessagingPattern.roles;

import com.research.MessagingPattern.instances.Result;
import com.research.MessagingPattern.instances.Task;
import com.research.MessagingPattern.roles.impl.ServerPatternImpl;
import com.research.MessagingPattern.utils.Statistics;

import java.util.concurrent.atomic.AtomicLong;


/**
 * Created by mcalvo on 05/09/15.
 */
public abstract class AbstractServer {

    private double matrix[][];

    protected Statistics statistics;

    protected AbstractConnector client;

    public AbstractServer(double matrix[][]){

        this.matrix = matrix;
        this.statistics = new Statistics(System.currentTimeMillis(), matrix.length * matrix.length);

    }

    protected void updateMatrixValue(int x, int y, double value){

        matrix[x][y] = value;

        updateCount();

    }

    protected void updateCount(){

        if(statistics.getActualCount().incrementAndGet() == statistics.getTotalElements()){

            statistics.setEnd_time(System.currentTimeMillis());

            if(this instanceof ServerPatternImpl) {
                ((ServerPatternImpl)this).shutdownExecutors();
            }
        }
    }

    public abstract void assignTaskToWorker(Task task);


    public abstract void updateCoordinateValue(Result result);


    public AbstractConnector getClient() {
        return client;
    }

    public void setClient(AbstractConnector client) {
        this.client = client;
    }

    public Statistics getStatistics() {
        return statistics;
    }

    public void setStatistics(Statistics statistics) {
        this.statistics = statistics;
    }
}
