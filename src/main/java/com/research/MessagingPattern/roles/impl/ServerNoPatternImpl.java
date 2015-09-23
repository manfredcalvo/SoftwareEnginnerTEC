package com.research.MessagingPattern.roles.impl;

import com.research.MessagingPattern.roles.AbstractServer;
import com.research.MessagingPattern.instances.Result;
import com.research.MessagingPattern.instances.Task;

/**
 * Created by mcalvo on 05/09/15.
 */
public class ServerNoPatternImpl extends AbstractServer{


    public ServerNoPatternImpl(double matrix[][]){
        super(matrix);
    }

    @Override
    public void assignTaskToWorker(Task task) {

        client.sendMessage(task.getMessage(), task.getWorker());

    }


    @Override
    public void updateCoordinateValue(Result result){

        updateMatrixValue(result.getX(), result.getY(), result.getValue());

    }


}
