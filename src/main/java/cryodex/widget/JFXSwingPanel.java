package cryodex.widget;

import javafx.scene.Parent;
import javafx.scene.Scene;
import org.kordamp.bootstrapfx.BootstrapFX;

public class JFXSwingPanel {
    public static javafx.embed.swing.JFXPanel create(Parent root) {
        javafx.embed.swing.JFXPanel panel = new javafx.embed.swing.JFXPanel();
        var scene = new Scene(root);
        scene.getStylesheets().add(BootstrapFX.bootstrapFXStylesheet());
        panel.setScene(scene);
        return panel;
    }
}
