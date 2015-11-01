package com.research.MessagingPattern;

import com.research.MessagingPattern.instances.Mode;
import com.research.MessagingPattern.roles.AbstractConnector;
import com.research.MessagingPattern.roles.AbstractServer;
import com.research.MessagingPattern.roles.impl.ServerNoPatternImpl;
import com.research.MessagingPattern.roles.impl.ServerPatternImpl;
import com.research.MessagingPattern.roles.impl.ServerSocketConnector;
import com.research.MessagingPattern.instances.Message;
import com.research.MessagingPattern.instances.Task;
import com.research.MessagingPattern.instances.Worker;
import hep.aida.bin.DynamicBin1D;
import org.javatuples.Pair;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by mcalvo on 06/09/15.
 */
public class MainServer {


    private static Logger logger = Logger.getLogger(MainServer.class.getName());


    public static void main(String []args) throws Exception {


      /* args = new String[8];

        args[0] = "1";
        args[1] = "127.0.0.1:50003;127.0.0.1:50004";
        args[2] = "1000";
        args[3] = "1";
        args[4] = "8030";
        args[5] = "NO_PATTERN";//PATTERN 2.332
        args[6] = "100";
        args[7] = "/home/mcalvo/resultsPattern10MilV1.csv";*/
        //args[7] = "/Users/raquelrodriguezchaves/resultsNoPattern10MilV1.csv";


        int nVeces = Integer.parseInt(args[0]);


        while(nVeces > 0) {

            String workers[] = args[1].split(";");

            int numberOfWorkers = workers.length;

            int limit = Integer.parseInt(args[2]);

            int cpus = Integer.parseInt(args[3]);

            int port = Integer.parseInt(args[4]);

            Mode mode = Mode.valueOf(args[5]);

            int matrixLength = Integer.parseInt(args[6]);

            double matrix[][] = new double[matrixLength][matrixLength];

            AbstractServer server = null;

            switch (mode) {
                case PATTERN:
                    server = new ServerPatternImpl(matrix, cpus);
                    break;
                case NO_PATTERN:
                    server = new ServerNoPatternImpl(matrix);
                    break;
            }

            AbstractConnector connector = new ServerSocketConnector(server, port, matrixLength * matrixLength);

            server.setClient(connector);

            List<Worker> workerList = new ArrayList<Worker>(matrixLength);

            for (String worker : workers) {

                String ipPort[] = worker.split(":");

                Worker newWorker = new Worker(ipPort[0], Integer.parseInt(ipPort[1]));

                workerList.add(newWorker);

            }

            ListenConnections listenConnections = new ListenConnections(connector);

            Thread t = new Thread(listenConnections);

            t.start();

            Thread.sleep(2000);

            int nextWorker = 0;

            for (int j = 0; j < matrixLength; j++) {

                for (int k = 0; k < matrixLength; k++) {

                    Worker actual = workerList.get(nextWorker);

                    nextWorker = (nextWorker + 1) % numberOfWorkers;

                    Message message = new Message(limit, new Pair<Integer, Integer>(k, j));

                    server.assignTaskToWorker(new Task(message, actual));

                }
            }

            server.awaitForTermination();

            for (int j = 0; j < matrixLength; j++) {

                for (int k = 0; k < matrixLength; k++) {

                    System.out.println("Entry = " + matrix[j][k]);

                }
            }

            double throughput = ((double)matrixLength * matrixLength) / (double)server.getStatistics().totalTimeSeconds();

            BufferedWriter writer = new BufferedWriter(new FileWriter(args[7], true));

            writer.append(String.valueOf(throughput)).append("\n");

            writer.close();

            nVeces--;

        }

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