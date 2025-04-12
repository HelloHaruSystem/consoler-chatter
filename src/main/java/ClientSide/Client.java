package ClientSide;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

// TODO: split client and server
public class Client implements Runnable {

    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private boolean running;

    public Client() {

    }

    @Override
    public void run() {
        try {
            // using ipv4 loopback address to run it on localhost
            clientSocket = new Socket("127.0.0.1", 42069);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            running = true;


            InputHandler inputHandler = new InputHandler();
            Thread thread = new Thread(inputHandler);
            thread.start();

            String inMessage;
            while((inMessage = in.readLine()) != null) {
                System.out.println(inMessage);
            }

        } catch (Exception e) {
           // TODO: Handle Properly
            e.printStackTrace();
        }
    }

    private void shutdown() {
        running = false;

        try {
            in.close();
            out.close();
            if (!this.clientSocket.isClosed()) {
                this.clientSocket.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // inner class inputHandler
    class InputHandler implements Runnable {

        @Override
        public void run() {
            try {
                BufferedReader inputReader = new BufferedReader(new InputStreamReader(System.in));

                while (running) {
                    String message = inputReader.readLine();
                    if (message.equals("/quit")) {
                        out.println(message);
                        inputReader.close();
                        shutdown();
                    } else {
                        out.println(message);
                    }
                }

            } catch (IOException ex) {
                // TODO: handle Proberly
                shutdown();
                ex.printStackTrace();
            }
        }

    }


    public static void main(String[] args) {
        Client client = new Client();
        client.run();
    }

}
