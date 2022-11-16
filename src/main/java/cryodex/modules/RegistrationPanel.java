package cryodex.modules;

import javax.swing.*;

import cryodex.Player;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Node;

public interface RegistrationPanel {
	public Node getPanel();

	public void save(Player player);

	public void load(Player player);

	public void clearFields();
}
