package com.vishlesha.app;

//product of Vishlesha
//created by Ridwan

import com.vishlesha.dataType.Node;
import com.vishlesha.log.AppLogger;
import com.vishlesha.network.Client;
import com.vishlesha.network.Server;
import com.vishlesha.request.*;

import java.io.*;
import java.net.InetAddress;
import java.util.*;
import java.util.logging.Logger;

/*
    * Runs the interface for the client application
    * Displays the dialog box asking for connection details and query commands
    * Connects to server and displays the responseMessage
*/

public class App {


    final GlobalConstant globalConstant = new GlobalConstant();
    final static Scanner scanner = new Scanner(System.in);

    public static void main(final String[] args) throws IOException {
        Client client = new Client();
        final Node bootstrapServerNode = new Node();


        System.out.println("Vishlesha Distributed System");
        System.out.println("----------------------------\n");
        setup(bootstrapServerNode, client);

        Request regRequest = new RegisterRequest(bootstrapServerNode);
        client.sendTCPRequest(regRequest);

        // TODO modify to issue multiple queries

        System.out.println("connecting to the network..........");


        boolean print = true;

        while(true){
            if (GlobalState.getNeighbors().size() > 0){

                if (print){
                    System.out.println("connected to network");
                    System.out.println("\nInitiate Search\n---------------------");
                    print = false;
                }

                if (scanner.hasNextLine()) {
                    scanner.nextLine();
                }
                System.out.print("Enter your search query: ");
                String searchQuery = scanner.nextLine();
                SearchRequest ser = new SearchRequest(GlobalState.getLocalServerNode(),searchQuery , 0);
                client.sendUDPRequest(ser);
                System.out.printf("searching for file ......");
            }
        }
    }

    private static void setup(final Node bootstrapServerNode, final Client client){
        int nodeId;
        String bootstrapAddress, userName;
        int bootstrapPort;
        final Logger log = Logger.getLogger(AppLogger.APP_LOGGER_NAME);
        Server server;

        bootstrapAddress = "127.0.0.1";
        bootstrapPort = 1033;

        bootstrapServerNode.setIpaddress(bootstrapAddress);
        bootstrapServerNode.setPortNumber(bootstrapPort);

        // handle LEAVE and UNREG on shutdown
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                System.out.println("\nUnregistering and leaving the network");
                Request unregRequest = new UnregisterRequest(bootstrapServerNode);
                client.sendTCPRequest(unregRequest);

                for (Node n : GlobalState.getNeighbors().keySet()) {
                    client.sendUDPRequest(new LeaveRequest(n));
                }

                // TODO add reasonable blocking mechanism
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Ready to shut down");
                log.info(this.getClass() + " : shutting down app");
            }
        });
        try {
            AppLogger.setup();

            // switch to seed-based IP addresses on 127.0.0.1 (local environment)
            Node localServer = new Node();
            String localIp = InetAddress.getLocalHost().getHostAddress();
            if ("127.0.0.1".equals(localIp)) {
                System.out.print("Enter seed: ");
                int seed = scanner.nextInt();
                localServer.setIpaddress("127.0.0." + seed);
                localServer.setPortNumber(GlobalConstant.PORT_MIN + seed);
            } else {
                localServer.setIpaddress(localIp);
                localServer.setPortNumber(GlobalConstant.PORT_LISTEN);
            }

            GlobalState.setLocalServerNode(localServer);
            server= new Server(localServer);
            server.start();

            System.out.println("Generating local file list\n................................");
            final Random rand = new Random();
            int index;
            Set<Integer> prevIndices = new HashSet<>();
            for (int i = 3 + rand.nextInt(3); i > 0; i--) {
                // pick previously unused file
                do {
                    index = rand.nextInt(GlobalConstant.ALL_FILES.size());
                } while (prevIndices.contains(index));

                prevIndices.add(index);
                String file = GlobalConstant.ALL_FILES.get(index);
                GlobalState.getLocalFiles().add(file);
                System.out.format("%2d %s\n", index, file);
            }
            System.out.println("File list generated\n");


        }catch (IOException ex){
            System.out.println("Unable to create log files");
            ex.printStackTrace();
        }

    }
}


