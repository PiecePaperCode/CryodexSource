package cryodex.widget;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.Objects;

public class SplashPanel {

	public static void main(String[] args) {
		new SplashPanel();
	}

	public SplashPanel() {
		Platform.runLater(this::showSplash);
	}

	public void showSplash() {
		BorderPane content = new BorderPane();

		// Build the splash screen
		ImageView ardvark = new ImageView(new Image(
			Objects.requireNonNull(
				SplashPanel.class.getResourceAsStream("logo.png")
			)
		));
		ImageView wait = new ImageView(new Image(
				Objects.requireNonNull(
						SplashPanel.class.getResourceAsStream("wait.gif")
				)
		));

		content.setCenter(ardvark);
		content.setBottom(wait);

		var scene = new Scene(content, 600, 500);
		var stage = new Stage();
		stage.setScene(scene);
		stage.show();
	}
}
