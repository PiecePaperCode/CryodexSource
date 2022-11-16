package cryodex.widget;

import java.util.List;

import cryodex.CryodexController;
import cryodex.Player;
import cryodex.components.Bootstrap;
import cryodex.modules.Module;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 * Panel for the creation of players. Also contains the donation link.
 * 
 * @author cbrown
 * 
 */
public class RegisterPanel {
	private Button saveButton;
	private Button deleteButton;
	private Button cancelButton;
	private ListView<Player> playerList;
	private TextField nameField;
	private ListView<Player> userModel;
	private Label counterLabel;
	private Label donationLabel;
	private TextField groupField;
	private TextField emailField;
	private VBox playerInfoPanel;
	private VBox playerPanel;
	private final JFXPanel panel;

	public RegisterPanel() {
		var root = new Bootstrap().VBox(getPlayerPanel());
		panel = JFXSwingPanel.create(root);
	}

	public JFXPanel getPanel() {
		return panel;
	}

	private VBox getPlayerPanel() {
		if (playerPanel == null) {
			Node playersTitle = new Bootstrap().H4("Player Info");
			ScrollPane listScroller = new ScrollPane(getPlayerList());
			var labelPanel = new Bootstrap().VBox(getCounterLabel(), getDonationLabel());
			var southPanel = new Bootstrap().VBox(listScroller, labelPanel);
			playerPanel = new Bootstrap().VBox(
				playersTitle,
				getPlayerInfoPanel(),
				southPanel
			);
			playerPanel.setPadding(new Insets(Bootstrap.SPACING * 2));
		}

		return playerPanel;
	}

	private VBox getPlayerInfoPanel() {
		if (playerInfoPanel == null) {
			playerInfoPanel = new Bootstrap().VBox(
				new Bootstrap().H5("Name"),
				getNameField(),
				new Bootstrap().H5("Group Name"),
				getGroupNameField(),
				new Bootstrap().H5("Email Address"),
				getEmailField()
			);
			for (Module m : CryodexController.getModules()) {
				playerInfoPanel.getChildren().add(m.getRegistration().getPanel());
			}
			playerInfoPanel.getChildren().add(
				new Bootstrap().HBox(
					getSaveButton(),
					getCancelButton(),
					getDeleteButton()
				)
			);
		}
		return playerInfoPanel;
	}

	private TextField getNameField() {
		if (nameField == null) {
			nameField = new Bootstrap().DefaultTextField();
		}
		return nameField;
	}

	private TextField getGroupNameField() {
		if (groupField == null) {
			groupField = new Bootstrap().DefaultTextField();
		}
		return groupField;
	}

	private TextField getEmailField() {
		if (emailField == null) {
			emailField = new Bootstrap().DefaultTextField();
		}
		return emailField;
	}

	private Button getSaveButton() {

		if (saveButton == null) {

			saveButton = new Bootstrap().PrimaryButton("Save");

			saveButton.addEventFilter(
				MouseEvent.MOUSE_CLICKED,
				MouseEvent -> {
						String name = getNameField().getText();
						String groupName = getGroupNameField().getText();
						String email = getEmailField().getText();

						if (name == null || name.isEmpty()) {
							new Alert(
									Alert.AlertType.ERROR,
									"Name is required",
									ButtonType.OK
							);
							return;
						}

						// Create new player or get previous player instance
						Player player;
						if (getPlayerList().getSelectionModel().getSelectedIndex() == -1) {
							for (Player p : CryodexController.getPlayers()) {
								if (p.getName().equals(name)) {
									new Alert(
											Alert.AlertType.ERROR,
											"This player name already exists.",
											ButtonType.OK
									);
									return;
								}
							}

							player = new Player();
							CryodexController.getPlayers().add(player);
							getUserModel().getItems().add(player);

						} else {
							player = getPlayerList()
									.getSelectionModel()
									.getSelectedItem();
						}

						player.setName(name);
						if (groupName != null) {
							player.setGroupName(groupName);
						}
						player.setEmail(email);

						for (Module m : CryodexController.getModules()) {
							m.getRegistration().save(player);
						}

						clearFields();
						CryodexController.saveData();

						Platform.runLater(() -> getNameField().requestFocus());

						updateCounterLabel();
					}
			);
		}
		return saveButton;
	}

	private Button getDeleteButton() {

		if (deleteButton == null) {
			deleteButton = new Bootstrap().DangerButton("Delete");
			deleteButton.addEventFilter(
				MouseEvent.MOUSE_CLICKED,
				MouseEvent -> {
					CryodexController.getPlayers().remove(
						getPlayerList()
							.getSelectionModel()
							.getSelectedItem()
					);

					getUserModel().getItems().remove(
						getPlayerList()
							.getSelectionModel()
							.getSelectedItem()
					);

					clearFields();
					CryodexController.saveData();

					updateCounterLabel();
				}
			);
			deleteButton.setDisable(true);
		}

		return deleteButton;
	}

	private Button getCancelButton() {
		if (cancelButton == null) {
			cancelButton = new Bootstrap().DefaultButton("Cancel");
			cancelButton.addEventFilter(
					MouseEvent.MOUSE_CLICKED,
					MouseEvent -> {
						clearFields();
						updateCounterLabel();
					}
			);
		}
		return cancelButton;
	}

	private void clearFields() {
		getNameField().setText("");
		getPlayerList().getItems().removeAll();
		getDeleteButton().setDisable(true);
		getNameField().requestFocus();
		getGroupNameField().setText("");
		getEmailField().setText("");

		for (Module m : CryodexController.getModules()) {
			m.getRegistration().clearFields();
		}
	}

	public void addPlayers(List<Player> players) {
		for (Player p : players) {
			getUserModel().getItems().add(p);
		}
	}

	private ListView<Player> getUserModel() {
		if (userModel == null) {
			userModel = new ListView<>();
			userModel.addEventFilter(
				MouseEvent.ANY,
				MouseEvent -> updateCounterLabel()
			);
		}
		return userModel;
	}

	private ListView<Player> getPlayerList() {
		if (playerList == null) {
			playerList = getUserModel();
			playerList.addEventFilter(
				MouseEvent.ANY,
				MouseEvent -> {
					Player player = playerList
						.getSelectionModel()
						.getSelectedItem();

					if (player == null) {
						getDeleteButton().setDisable(true);
					} else {
						getDeleteButton().setDisable(false);
						getNameField().setText(player.getName());
						getGroupNameField().setText(player.getGroupName());
						getEmailField().setText(player.getEmail());

						for (Module m : CryodexController.getModules()) {
							m.getRegistration().load(player);
						}
					}
					updateCounterLabel();
				}
			);
		}
		return playerList;
	}

	public void updateCounterLabel() {
		getCounterLabel().setText("Player Count: " + getUserModel()
			.getChildrenUnmodifiable()
			.size()
		);
	}

	public Label getCounterLabel() {
		if (counterLabel == null) {
			counterLabel = new Label();
		}

		return counterLabel;
	}

	public Label getDonationLabel() {
		if (donationLabel == null) {
			donationLabel = new Label("Donate To Cryodex");
			donationLabel.setCursor(Cursor.DEFAULT);
			donationLabel.addEventFilter(
					MouseEvent.ANY,
					MouseEvent ->  CryodexController.sendDonation()
			);
		}
		return donationLabel;
	}

	public void importPlayers(List<Player> players){
		for(Player p : players){
			CryodexController.getPlayers().add(p);
			getUserModel().getItems().add(p);
		}
		CryodexController.saveData();
	}
}
