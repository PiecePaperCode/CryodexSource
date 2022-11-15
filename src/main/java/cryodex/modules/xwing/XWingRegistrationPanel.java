package cryodex.modules.xwing;

import cryodex.Player;
import cryodex.components.Header;
import cryodex.modules.ModulePlayer;
import cryodex.modules.RegistrationPanel;
import cryodex.modules.xwing.XWingPlayer.Faction;
import cryodex.widget.JFXSwingPanel;
import javafx.embed.swing.JFXPanel;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class XWingRegistrationPanel implements RegistrationPanel {

	private TextField squadField;
	private CheckBox firstRoundByeCheckbox;
	private VBox root;
	private ComboBox<Faction> factionCombo;

	@Override
	public JFXPanel getPanel() {
		if (root == null) {
			root = new VBox();
			root.setSpacing(10);
			root.getChildren().add(new Header().H3("X-Wing"));
			root.getChildren().add(new Header().H4("Squadron Builder ID"));
			root.getChildren().add(getSquadField());
			root.getChildren().add(new Header().H4("Faction"));
			root.getChildren().add(getFactionCombo());
			root.getChildren().add(getFirstRoundByeCheckbox());
		}
		return JFXSwingPanel.create(root);
	}

	private ComboBox<Faction> getFactionCombo() {
		if(factionCombo == null){
			factionCombo = new ComboBox<>();
			factionCombo.getStyleClass().add("split-menu-btn");
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
			squadField = new TextField();
		}
		return squadField;
	}

	private CheckBox getFirstRoundByeCheckbox() {
		if (firstRoundByeCheckbox == null) {
			firstRoundByeCheckbox = new CheckBox("First Round Bye");
		}
		return firstRoundByeCheckbox;
	}

	@Override
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
