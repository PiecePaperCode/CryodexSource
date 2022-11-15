package cryodex.widget;

import java.awt.*;
import java.io.IOException;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import javax.swing.*;

public class SplashPanel {
    int width = 600;
    int height = 500;
    Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
    int x = (screen.width - width) / 2;
    int y = (screen.height - height) / 2;

    public static void main(String[] args) {
        new SplashPanel();
    }

	public SplashPanel() {
        JWindow frame = new JWindow();
        final JFXPanel fxPanel = new JFXPanel();
        frame.add(fxPanel);
        frame.setBounds(x, y, width, height);
        frame.setVisible(true);
        Platform.runLater(() -> {
            try {
                fxPanel.setScene(createScene());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public Scene createScene() throws IOException {
        BorderPane content = new BorderPane();
		content.setBackground(new Background(new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY)));
        ImageView ardvark = new ImageView(new Image(SplashPanel.class.getResource("logo.png").openStream()));
        ImageView waitImg = new ImageView(new Image(SplashPanel.class.getResource("wait.gif").openStream()));
        HBox wait = new HBox();
        wait.setAlignment(Pos.CENTER);
        wait.getChildren().add(waitImg);
		content.setCenter(ardvark);
		content.setBottom(wait);
        return new Scene(content, width, height);
	}

}