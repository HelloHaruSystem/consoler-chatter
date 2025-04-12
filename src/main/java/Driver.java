import ServerSide.Server;
import UserInterface.RootWindow;

public class Driver {

    public static void main (String[] args) {
        RootWindow window = new RootWindow();
        Server chatter = new Server();

        chatter.run();
    }



}
