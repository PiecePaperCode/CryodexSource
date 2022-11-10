package cryodex.widget;

import javafx.scene.Node;
import javax.swing.*;

public class SwingNode {
    public static Node convert(JComponent component) {
        var swingNode = new javafx.embed.swing.SwingNode();
        swingNode.setContent(component);
        return swingNode;
    }
}
