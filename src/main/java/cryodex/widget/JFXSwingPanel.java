package cryodex.widget;

import javafx.embed.swing.JFXPanel;
import javafx.embed.swing.SwingNode;
import javafx.scene.Parent;
import javafx.scene.Scene;
import org.kordamp.bootstrapfx.BootstrapFX;

import javax.swing.*;

public class JFXSwingPanel {
    public static JFXPanel create(Parent root) {
        JFXPanel panel = new JFXPanel();
        var scene = new Scene(root);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        panel.setScene(scene);
        return panel;
    }
}
