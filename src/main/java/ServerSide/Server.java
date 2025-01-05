package ServerSide;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server implements Runnable {

    private List<ConnectionHandler> connections;
    private ServerSocket serverSocket;
    private boolean running;
    private ExecutorService threadPool;

    public Server() {
        connections = new ArrayList<>();
        running = true;
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(42069);
            threadPool = Executors.newCachedThreadPool();

            while (running) {
                Socket clientSocket = serverSocket.accept();
                ConnectionHandler handler = new ConnectionHandler(clientSocket);
                connections.add(handler);
                threadPool.execute(handler);
            }

        } catch (Exception ex) {
            // TODO: Handle properly
            this.shutdown();
            ex.printStackTrace();
        }

    }

    private void broadcast(String message) {
        for (ConnectionHandler channel : connections) {
            if (channel != null) {
                channel.sendMessage(message);
            }
        }
    }

    private void shutdown() {
        try {
            this.running = false;
            if (!this.serverSocket.isClosed()) {
                this.serverSocket.close();
            }

            for (ConnectionHandler channel : connections) {
                channel.shutdown();
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }


    }

    // inner class
    class ConnectionHandler implements Runnable {

        private final Socket client;
        private BufferedReader in;
        private PrintWriter out;
        private String alias;

        public ConnectionHandler(Socket client) {
            this.client = client;
        }

        @Override
        public void run() {
            try {
                this.out = new PrintWriter(client.getOutputStream(), true);
                this.in = new BufferedReader(new InputStreamReader(client.getInputStream()));

                out.println("Please enter an Alias: ");
                alias = in.readLine();
                System.out.println(this.alias + " connected"); // server log
                broadcast(this.alias + " joined the room");

                String message;
                while ((message = in.readLine()) != null) {
                    // commands to change Alias
                    if (message.startsWith("/alias ")) {
                        // TODO: handle Alias
                        String[] messageParts = message.split(" ", 2);
                        if (messageParts.length == 2) {
                            broadcast(this.alias + " renamed themselves to " + messageParts[1]);
                            System.out.println(this.alias + " renamed themselves to " + messageParts[1]);
                            this.alias = messageParts[1];
                            System.out.println("Successfully changed alias to " + this.alias);
                        }
                        // command to quit the app
                    } else if (message.startsWith("/quit")) {
                        // TODO: quit
                        broadcast(this.alias + " left the room");
                        this.shutdown();
                        // else broadcast message
                    } else {
                        broadcast(alias + ": " + message);
                    }
                }
            } catch (IOException ex) {
                // TODO: handle properly
                this.shutdown();
                ex.printStackTrace();
            }
        }

        private void validAlias() {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        private void sendMessage(String message) {
            out.println(message);
        }

        private void shutdown() {
            try {
                in.close();
                out.close();
                if (!this.client.isClosed()) {
                    this.client.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }
    }
}
