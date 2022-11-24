package cryodex.modules.xwing;

import cryodex.Player;
import cryodex.components.Bootstrap;
import cryodex.modules.ModulePlayer;
import cryodex.modules.RegistrationPanel;
import cryodex.modules.xwing.XWingPlayer.Faction;
import javafx.scene.Node;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class XWingRegistrationPanel implements RegistrationPanel {

	private TextField squadField;
	private CheckBox firstRoundByeCheckbox;
	private VBox panel;
	private ComboBox<Faction> factionCombo;

	@Override
	public Node getPanel() {
		if (panel == null) {
			panel = new Bootstrap().VBox(
				new Bootstrap().H4("X-Wing"),
				new Bootstrap().H5("Squadron Builder ID"),
				getSquadField(),
				new Bootstrap().H5("Faction"),
				getFactionCombo(),
				getFirstRoundByeCheckbox()
			);
		}
		return panel;
	}

	private ComboBox<Faction> getFactionCombo() {
		if(factionCombo == null){
			factionCombo = new Bootstrap().Combobox();
			factionCombo.getItems().add(Faction.FIRST_ORDER);
			factionCombo.getItems().add(Faction.IMPERIAL);
			factionCombo.getItems().add(Faction.REPUBLIC);
			factionCombo.getItems().add(Faction.REBEL);
			factionCombo.getItems().add(Faction.RESISTANCE);
			factionCombo.getItems().add(Faction.SCUM);
			factionCombo.getItems().add(Faction.SEPARATIST);
			factionCombo.getSelectionModel().select(-1);
		}
		
		return factionCombo;
	}

	private TextField getSquadField() {
		if (squadField == null) {
			squadField = new Bootstrap().DefaultTextField();
		}
		return squadField;
	}

	private CheckBox getFirstRoundByeCheckbox() {
		if (firstRoundByeCheckbox == null) {
			firstRoundByeCheckbox = new CheckBox("First Round Bye");
		}
		return firstRoundByeCheckbox;
	}

	public void save(Player player) {

		XWingPlayer xp = null;

		// get module information
		if (
			player.getModuleInfo() != null
			&& !player.getModuleInfo().isEmpty()
		) {
			for (ModulePlayer mp : player.getModuleInfo()) {
				if (mp instanceof XWingPlayer) {
					xp = (XWingPlayer) mp;
					break;
				}
			}
		}

		// if no module information, create one and add it to player
		if (xp == null) {
			xp = new XWingPlayer(player);
			player.getModuleInfo().add(xp);
		}

		// update module information
		xp.setSquadId(getSquadField().getText());
		xp.setFirstRoundBye(getFirstRoundByeCheckbox().isSelected());
		xp.setFaction(getFactionCombo().getSelectionModel().getSelectedItem());
	}

	@Override
	public void load(Player player) {
		XWingPlayer xp = null;

		// get module information
		if (
			player != null && player.getModuleInfo() != null
			&& !player.getModuleInfo().isEmpty()
		) {
			for (ModulePlayer mp : player.getModuleInfo()) {
				if (mp instanceof XWingPlayer) {
					xp = (XWingPlayer) mp;
					break;
				}
			}
		}

		if (xp != null) {
			getSquadField().setText(xp.getSquadId());
			getFirstRoundByeCheckbox().setSelected(xp.isFirstRoundBye());
			getFactionCombo().getSelectionModel().select(xp.getFaction());
		} else {
			clearFields();
		}
	}

	@Override
	public void clearFields() {
		getSquadField().setText("");
		getFactionCombo().getSelectionModel().select(-1);
		getFirstRoundByeCheckbox().setSelected(false);
	}

}
