package cryodex.modules;

import javax.swing.*;

import cryodex.Player;
import javafx.embed.swing.JFXPanel;

public interface RegistrationPanel {
	public JComponent getPanel();

	public void save(Player player);

	public void load(Player player);

	public void clearFields();
}
