package cryodex.modules.imperialassault;

import cryodex.CryodexController.Modules;
import cryodex.Player;
import cryodex.components.Bootstrap;
import cryodex.modules.ModulePlayer;
import cryodex.modules.RegistrationPanel;
import cryodex.widget.JFXSwingPanel;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;

public class IARegistrationPanel implements RegistrationPanel {

	// private JTextField squadField;
	private CheckBox firstRoundByeCheckbox;
	private VBox panel;

	@Override
	public VBox getPanel() {
		if (panel == null) {
			panel = new Bootstrap().VBox(
				new Bootstrap().H4(Modules.IA.getName()),
				getFirstRoundByeCheckbox()
			);
		}
		return panel;
	}

	private CheckBox getFirstRoundByeCheckbox() {
		if (firstRoundByeCheckbox == null) {
			firstRoundByeCheckbox = new CheckBox("First Round Bye");
		}
		return firstRoundByeCheckbox;
	}

	@Override
	public void save(Player player) {

		IAPlayer xp = null;

		// get module information
		if (
			player.getModuleInfo() != null
			&& !player.getModuleInfo().isEmpty()
		) {
			for (ModulePlayer mp : player.getModuleInfo()) {
				if (mp instanceof IAPlayer) {
					xp = (IAPlayer) mp;
					break;
				}
			}
		}

		// if no module information, create one and add it to player
		if (xp == null) {
			xp = new IAPlayer(player);
			player.getModuleInfo().add(xp);
		}

		// update module information
		// xp.setSquadId(getSquadField().getText());
		xp.setFirstRoundBye(getFirstRoundByeCheckbox().isSelected());
	}

	@Override
	public void load(Player player) {
		IAPlayer xp = null;

		// get module information
		if (
			player != null && player.getModuleInfo() != null
			&& !player.getModuleInfo().isEmpty()
		) {
			for (ModulePlayer mp : player.getModuleInfo()) {
				if (mp instanceof IAPlayer) {
					xp = (IAPlayer) mp;
					break;
				}
			}
		}

		// if no module information, create one and add it to player
		if (xp != null) {
			// getSquadField().setText(xp.getSquadId());
			getFirstRoundByeCheckbox().setSelected(xp.isFirstRoundBye());
		} else {
			clearFields();
		}
	}

	@Override
	public void clearFields() {
		// getSquadField().setText("");
		getFirstRoundByeCheckbox().setSelected(false);
	}

}
