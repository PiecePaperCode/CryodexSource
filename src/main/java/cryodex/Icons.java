package cryodex;

import javafx.scene.image.Image;

import java.io.IOException;
import java.net.URL;


public class Icons {

	private static Icons instance;

	public static Icons getInstance() {
		if (instance == null) {
			instance = new Icons();
		}
		return instance;
	}

	private Image redx;
	private Image greencheckmark;

	public Image getRedX() throws IOException {
		if (redx == null) {
			URL imgURL = Icons.class.getResource("RedX.png");
			if (imgURL == null) {
				System.out.println("fail!!!!!!!!!!");
			}
			assert imgURL != null;
			redx = new Image(imgURL.openStream());
		}
		return redx;
	}

	public Image getGreenCheckMark() throws IOException {
		if (greencheckmark == null) {
			URL imgURL = Icons.class.getResource("GreenCheckMark.png");
			if (imgURL == null) {
				System.out.println("fail!!!!!!!!!!");
			}
			assert imgURL != null;
			greencheckmark = new Image(imgURL.openStream());
		}
		return greencheckmark;
	}

}
