package com.research.MessagingPattern;

import com.research.MessagingPattern.roles.AbstractConnector;
import com.research.MessagingPattern.roles.AbstractServer;
import com.research.MessagingPattern.roles.impl.ServerPatternImpl;
import com.research.MessagingPattern.roles.impl.ServerSocketConnector;
import com.research.MessagingPattern.instances.Message;
import com.research.MessagingPattern.instances.Task;
import com.research.MessagingPattern.instances.Worker;
import org.javatuples.Pair;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by mcalvo on 06/09/15.
 */
public class MainServerPattern {



    public static void main(String []args) throws InterruptedException {


        args = new String[4];
        args[0] = "127.0.0.1:50002;127.0.0.1:50003";
        args[1] = "10000";
        args[2] = "1";
        args[3] = "8030";


        String workers[] = args[0].split(";");

        int matrixLength = workers.length;

        int limit = Integer.parseInt(args[1]);

        int cpus = Integer.parseInt(args[2]);

        int port = Integer.parseInt(args[3]);

        double matrix[][] = new double[matrixLength][500];

        ExecutorService service = Executors.newFixedThreadPool(cpus);

        AbstractServer server = new ServerPatternImpl(matrix);

        AbstractConnector connector = new ServerSocketConnector(service, server, port);

        server.setClient(connector);

        List<Worker> workerList = new ArrayList<Worker>(matrixLength);

        for(String worker: workers){

            String ipPort[] = worker.split(":");


            Worker newWorker = new Worker(ipPort[0], Integer.parseInt(ipPort[1]));

            workerList.add(newWorker);

        }

        ListenConnections listenConnections = new ListenConnections(connector);

        Thread t = new Thread(listenConnections);

        t.start();

        for(int j = 0; j < matrixLength; j++){

            for(int k = 0; k < matrixLength; k++){

                Worker actual = workerList.get(k);

                Message message = new Message(limit, new Pair<Integer, Integer>(k, j));

                server.assignTaskToWorker(new Task(message, actual));

            }
        }

        t.join();

    }

    private static class ListenConnections implements Runnable{

        private AbstractConnector connector;

        public ListenConnections(AbstractConnector connector){
            this.connector = connector;
        }

        public void run() {

            connector.listenConnections();
        }
    }

}
