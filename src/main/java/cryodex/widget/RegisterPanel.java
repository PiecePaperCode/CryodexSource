package cryodex.widget;

import java.util.List;

import cryodex.CryodexController;
import cryodex.Player;
import cryodex.modules.Module;
import javafx.application.Platform;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 * Panel for the creation of players. Also contains the donation link.
 * 
 * @author cbrown
 * 
 */
public class RegisterPanel  {
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
	private BorderPane playerPanel;

	public RegisterPanel() {
        playerPanel = new BorderPane();
        playerPanel.setTop(getPlayerPanel());
		playerPanel.setCenter(new BorderPane());
	}

	/**
	 * Main panel layout
	 * 
	 * @return
	 */
	public BorderPane getPlayerPanel() {
		if (playerPanel == null) {
			playerPanel = new BorderPane();

			Label playersTitle = new Label("Player Info");
			playersTitle.setFont(new Font(20));

			ScrollPane listScroller = new ScrollPane(getPlayerList());
            listScroller.setHmax(150);
            listScroller.setMaxWidth(250);

			VBox labelPanel = new VBox();
            labelPanel.getChildren().addAll(getCounterLabel(), getDonationLabel());

			VBox southPanel = new VBox();
            southPanel.getChildren().addAll(listScroller, labelPanel);

			playerPanel.setTop(playersTitle);
			playerPanel.setCenter(getPlayerInfoPanel());
			playerPanel.setBottom(southPanel);
		}

		return playerPanel;
	}

	/**
	 * Information input
	 * 
	 * @return
	 */
	private VBox getPlayerInfoPanel() {
		if (playerInfoPanel == null) {
			playerInfoPanel = new VBox();
			playerInfoPanel.getChildren().add(new Label("Name"));
			playerInfoPanel.getChildren().add(getNameField());
			playerInfoPanel.getChildren().add(new Label("Group Name"));
			playerInfoPanel.getChildren().add(getGroupNameField());
			playerInfoPanel.getChildren().add(new Label("Email Address"));
			playerInfoPanel.getChildren().add(getEmailField());
			for (Module m : CryodexController.getModules()) {
				playerInfoPanel.getChildren().add(m.getRegistration().getPanel());
			}
            HBox playerInfoPanelButtons = new HBox();
            playerInfoPanelButtons
                .getChildren()
                .addAll(getSaveButton(), getCancelButton(), getDeleteButton());
		}
		return playerInfoPanel;
	}

	private TextField getNameField() {
		if (nameField == null) {
			nameField = new TextField();
		}
		return nameField;
	}

	private TextField getGroupNameField() {
		if (groupField == null) {
			groupField = new TextField();
		}
		return groupField;
	}

	private TextField getEmailField() {
		if (emailField == null) {
			emailField = new TextField();
		}
		return emailField;
	}

	private Button getSaveButton() {

		if (saveButton == null) {

			saveButton = new Button("Save");

			saveButton.addEventFilter(
                MouseEvent.MOUSE_CLICKED,
                MouseEvent -> {
                    // /////////////////////////////
                    // Update general information
                    // /////////////////////////////
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
			deleteButton = new Button("Delete");
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
			cancelButton = new Button("Cancel");
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
                MouseEvent -> {
                    updateCounterLabel();
                }
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
			donationLabel = new Label(
					"<html><a href=\"\">Donate To Cryodex</a></html>");
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
