package UserInterface;

import javax.swing.*;

// TODO: Split Ui and then client so it is it's own program
public class RootWindow {

    public RootWindow() {
        JFrame root = new JFrame();
        root.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        root.setResizable(false);
        root.setTitle("Chat App");

        MainPanel mainPanel = new MainPanel();
        root.add(mainPanel);

        // makes it so the window will be sized to fit the preferred size and layouts of the subcomponents
        // in this case the mainPanel
        root.pack();

        // sets to the middle of the screen
        root.setLocationRelativeTo(null);
        root.setVisible(true);
    }
}
