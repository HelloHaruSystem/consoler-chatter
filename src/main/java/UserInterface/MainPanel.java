package UserInterface;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {

    // screen settings
    private final int screenWidth = 380;
    private final int screenHeight = 480;

    public MainPanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);
    }
}
