package cryodex;

import cryodex.widget.RegisterPanel;
import cryodex.widget.SplashPanel;
import cryodex.widget.TournamentTabbedPane;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.awt.*;

/**
 * Main class that creates a singleton of the GUI which everything else is built
 * on.
 * 
 * @author cbrown
 * 
 */
public class Main extends Application {

	public static final long delay = 3000;

	private static Main instance = null;
    private static Stage stage;

	public static Main getInstance() {
		if (instance == null) {

			instance = new Main();
			instance.setSize(400, 720);

			CryodexController.loadData();
			instance.getRegisterPanel().addPlayers(
					CryodexController.getPlayers());
			CryodexController.isLoading = true;
			MenuBarPane.getInstance().resetMenuBar();
			CryodexController.isLoading = false;
		}

		return instance;
	}

	private BorderPane contentPane;
	private BorderPane registerPane;
	private RegisterPanel registerPanel;
	private TabPane multipleTournamentTabbedPane;
	private BorderPane tournamentPane;
	private BorderPane singleTournamentPane;
	private Pane warningPane;
	private Label warningLabel;

	@Override
	public void start(Stage stage) {
		new SplashPanel();
        Main.stage = stage;
		Main.stage.setTitle("Piecepaper's Cryodex - Version 4.3.3");
		BorderPane root = new BorderPane();
		root.setTop(MenuBarPane.getInstance());
		root.setBottom(getWarningPane());
		root.setLeft(getRegisterPane());
		root.setCenter(getTournamentPane());
        Main.stage.setScene(new Scene(root, 400, 720));
        Main.stage.show();
	}

	public BorderPane getContentFlowPane() {
		if (contentPane == null) {
			contentPane = new BorderPane();
		}
		return contentPane;
	}

	public RegisterPanel getRegisterPanel() {
		if (registerPanel == null) {
			registerPanel = new RegisterPanel();
		}
		return registerPanel;
	}

	public BorderPane getRegisterPane() {
		if (registerPane == null) {
			registerPane = new BorderPane();
			registerPane.setCenter(getRegisterPane());
		}
		return registerPane;
	}

	public BorderPane getTournamentPane() {
		if (tournamentPane == null) {
			tournamentPane = new BorderPane();
		}
		return tournamentPane;
	}

	public BorderPane getSingleTournamentPane() {
		if (singleTournamentPane == null) {
			singleTournamentPane = new BorderPane();
		}
		return singleTournamentPane;
	}

	public TabPane getMultipleTournamentTabbedPane() {
		if (multipleTournamentTabbedPane == null) {
			multipleTournamentTabbedPane = new TournamentTabbedPane().getTabPane();
		}
		return multipleTournamentTabbedPane;
	}

	public void setMultiple(boolean isMultiple) {

		getTournamentPane().getChildren().removeAll();

		if (isMultiple) {
			getTournamentPane().setCenter(getMultipleTournamentTabbedPane());
		} else {
			getTournamentPane().setCenter(getSingleTournamentPane());
		}

		// getTournamentPane().validate();
		// getTournamentPane().repaint();
	}
	
	public Pane getWarningPane() {
		if(warningPane == null){
			warningPane = new Pane();
			warningPane.setVisible(false);
			warningLabel = new Label();
			warningPane.getChildren().add(warningLabel);

			warningLabel.setBackground(
				new Background(
					new BackgroundFill(
						Color.ORANGE,
						CornerRadii.EMPTY,
						Insets.EMPTY
					)
				)
			);
			Font font = new Font("Courier",15);
			warningLabel.setFont(font);
		}
		
		return warningPane;
	}
	
	public void setError(String error){
		if(error == null || error.isEmpty()){
			warningLabel.setText("");
			getWarningPane().setVisible(false);
		} else {
			warningLabel.setText(error);
			getWarningPane().setVisible(true);
		}
	}

	public void setSize(int width, int height) {}
    public void dispose() {
        Main.stage.close();
    }
    public void validate() {}
    public void repaint() {}
    public void setExtendedState(int frame) {}
}
