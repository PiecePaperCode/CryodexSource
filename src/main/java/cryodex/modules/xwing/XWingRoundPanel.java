package cryodex.modules.xwing;

import java.util.ArrayList;
import java.util.List;

import cryodex.components.Bootstrap;
import cryodex.modules.Tournament;
import cryodex.widget.JFXSwingPanel;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.w3c.dom.Text;

public class XWingRoundPanel {
	private final List<XWingMatch> matches;
	private final List<GamePanel> gamePanels = new ArrayList<>();
    private final Node panel;
    private final XWingTournament tournament;

	public XWingRoundPanel(XWingTournament t, List<XWingMatch> matches) {
        this.matches = matches;
        this.tournament = t;
		int counter = 1;
		for (XWingMatch match : matches) {
			GamePanel gpanel = new GamePanel(match);
			gamePanels.add(gpanel);
			counter++;
		}

		ScrollPane scroll = new ScrollPane(buildPanel());
		
		var root = new Bootstrap().VBox(scroll);
		panel = root;
	}

	public Node getPanel() {
		return this.panel;
	}

	public VBox buildPanel() {

		var root = new Bootstrap().VBox();
		root.setPadding(new Insets(Bootstrap.SPACING * 2));
		for (GamePanel gp : gamePanels) {
			root.getChildren().add(gp.getPlayerTitle());
			root.getChildren().add(gp.getResultCombo());

			if (gp.getMatch().getPlayer2() != null) {
				root.getChildren().add(gp.getPlayer1KillLabel());
				root.getChildren().add(gp.getPlayer1KillPointsField());
				root.getChildren().add(gp.getPlayer2KillLabel());
				root.getChildren().add(gp.getPlayer2KillPointsField());
			}
		}
		return root;
	}

	public List<XWingMatch> getMatches() {
		return matches;
	}

	private class GamePanel {
		private final XWingMatch match;
		private Label playersTitle;
		private ComboBox<String> resultsCombo;
		private TextField player1KillPoints;
		private TextField player2KillPoints;
		private Label player1KillLabel;
		private Label player2KillLabel;

        public GamePanel(XWingMatch match) {
            this.match = match;
			init();
		}

		private void init() {
            if (match.getPlayer2() == null ) {
                getPlayerTitle().setText(String.format("%s vs BYE", match.getPlayer1().getName()));
				getPlayer1KillLabel().setText(match.getPlayer1().getName());
            } else {
				getPlayer1KillLabel().setText(match.getPlayer1().getName());
				getPlayer2KillLabel().setText(match.getPlayer2().getName());
				getResultCombo().getSelectionModel().select(0);
                getPlayerTitle().setText(String.format("%s vs %s", match.getPlayer1().getName(), match.getPlayer2().getName()));
            }
            if (match.isMatchComplete()) {
				if (match.isBye()) {
					getResultCombo().getSelectionModel().select(1);
				} else {
					if (match.getWinner() == match.getPlayer1()) {
						getResultCombo().getSelectionModel().select(1);
					} else if (match.getWinner() == match.getPlayer2()) {
						getResultCombo().getSelectionModel().select(2);
					}
				}
			}
			if (match.getPlayer2() != null) {
				if (match.getPlayer1PointsDestroyed() != null) {
                    getPlayer1KillPointsField().setText(String.valueOf(match.getPlayer1PointsDestroyed()));
				}
				if (match.getPlayer2PointsDestroyed() != null) {
                    getPlayer2KillPointsField().setText(String.valueOf(match.getPlayer2PointsDestroyed()));
				}
			}
		}

		private XWingMatch getMatch() {
			return match;
		}

		private Label getPlayerTitle() {
			if (playersTitle == null) {
				playersTitle = new Bootstrap().H3("");
			}
			return playersTitle;
		}

		private Label getPlayer1KillLabel() {
			if (player1KillLabel == null) {
				player1KillLabel = new Bootstrap().H5("");
			}
			return player1KillLabel;
		}

		private Label getPlayer2KillLabel() {
			if (player2KillLabel == null) {
				player2KillLabel = new Bootstrap().H5("");
			}
			return player2KillLabel;
		}

		private String[] getComboValues() {
			if (match.getPlayer2() == null) {
				return new String[]{"Select a result", "BYE"};
            } else {
				String generic = XWingModule.getInstance().getOptions().isEnterOnlyPoints()
					? "Enter results"
					: "Select a result";
				return new String[]{
					generic,
					"WIN - " + match.getPlayer1().getName(),
					"WIN - " + match.getPlayer2().getName(),
					"Draw"
				};
			}
		}

		private ComboBox<String> getResultCombo() {
			if (resultsCombo == null) {
				resultsCombo = new ComboBox<>();
				resultsCombo.getItems().addAll(getComboValues());
				resultsCombo
					.getSelectionModel()
					.selectedItemProperty()
					.addListener((options, oldValue, newValue) -> comboChange());
			}
			return resultsCombo;
		}

		private void comboChange() {
			switch (resultsCombo.getSelectionModel().getSelectedIndex()) {
			case 0:
				match.setWinner(null);
				match.setBye(false);
				break;
			case 1:
				if (match.getPlayer2() == null) {
					match.setBye(true);
				} else {
					match.setWinner(match.getPlayer1());
				}
				break;
			case 2:
				match.setWinner(match.getPlayer2());
				break;
            case 3:
                match.setDraw(true);
                break;
			default:
				break;
			}
		}

		public TextField getPlayer1KillPointsField() {
			if (player1KillPoints == null) {
				player1KillPoints = new TextField();
				player1KillPoints
					.textProperty()
					.addListener((options, oldValue, newValue) -> {
						Integer points = null;
						try {
							points = Integer.valueOf(player1KillPoints.getText());
						} catch (Exception ignored) {}
						match.setPlayer1PointsDestroyed(points);
						setResultsCombo();
					}
				);
			}
			return player1KillPoints;
		}

		public TextField getPlayer2KillPointsField() {
			if (player2KillPoints == null) {
				player2KillPoints = new TextField();
				player2KillPoints
					.textProperty()
					.addListener(
						(options, oldValue, newValue) -> {
							Integer points = null;
							try {
								points = Integer.valueOf(player2KillPoints.getText());
							} catch (Exception ignored) {}
							match.setPlayer2PointsDestroyed(points);
							setResultsCombo();
						}
					);
			}
			return player2KillPoints;
		}

		/**
		 * This function sets the combo box value to the winner of the match based on points.
		 */
		public void setResultsCombo() {
			boolean enterOnlyPoints = XWingModule.getInstance().getOptions().isEnterOnlyPoints();
			if (match.getPlayer1PointsDestroyed() != null || match.getPlayer2PointsDestroyed() != null) {
				Integer p1points = match.getPlayer1PointsDestroyed() == null ? 0 : match.getPlayer1PointsDestroyed();
				Integer p2points = match.getPlayer2PointsDestroyed() == null ? 0 : match.getPlayer2PointsDestroyed();
				if (p1points > p2points) {
					getResultCombo().getSelectionModel().select(1);
					getResultCombo().setEditable(!enterOnlyPoints);
				}
				if (p2points > p1points) {
					getResultCombo().getSelectionModel().select(2);
					getResultCombo().setEditable(!enterOnlyPoints);
				}
				if (p2points.equals(p1points)) {
					getResultCombo().getSelectionModel().select(3);
					getResultCombo().setEditable(!enterOnlyPoints);
				}
			} else {
				getResultCombo().getSelectionModel().select(0);
				getResultCombo().setEditable(!enterOnlyPoints);
			}
			comboChange();
			tournament.getTournamentGUI().getRankingTable().update();
		}

		public void markInvalid(boolean isSingleElimination) {
			if (!match.isValidResult(isSingleElimination)) {
				getPlayerTitle().setTextFill(Color.RED);
			} else {
				getPlayerTitle().setTextFill(Color.BLACK);
			}
		}
	}

	public void markInvalid(boolean isSingleElimination) {
		for (GamePanel gamePanel : gamePanels) {
			gamePanel.markInvalid(isSingleElimination);
		}
	}

}
