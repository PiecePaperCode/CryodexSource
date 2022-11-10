package cryodex.modules;

import cryodex.Player;
import javafx.scene.layout.BorderPane;

public interface RegistrationPanel {
	public BorderPane getPanel();

	public void save(Player player);

	public void load(Player player);

	public void clearFields();
}
