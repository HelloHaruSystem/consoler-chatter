import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {




    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(42069);
            Socket clientSocket = serverSocket.accept();
        } catch (IOException ex) {
            // TODO: Handle properly
        }

    }

    // inner class
    class ConnectionHandler implements Runnable {

        @Override
        public void run() {

        }

    }

}
