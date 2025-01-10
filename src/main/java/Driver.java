import ServerSide.Server;
import UserInterface.RootWindow;

public class Driver {

    // TODO: add a way to display messages to the gui
    public static void main (String[] args) {
        RootWindow window = new RootWindow();
        Server chatter = new Server();

        chatter.run();
    }

}
