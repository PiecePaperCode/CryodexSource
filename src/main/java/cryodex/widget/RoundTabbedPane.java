package cryodex.widget;

import javax.swing.*;
import cryodex.CryodexController;
import cryodex.components.Bootstrap;
import javafx.embed.swing.SwingNode;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.MouseEvent;

import java.util.Objects;

public class RoundTabbedPane {

	private final TabPane tabPane;

	public RoundTabbedPane() {
		tabPane = new TabPane();
		Button generate = new Bootstrap().InfoButton("generate");
		generate.addEventFilter(
			MouseEvent.MOUSE_CLICKED,
			MouseEvent -> {
				boolean successful = Objects.requireNonNull(CryodexController.getActiveTournament())
				.generateNextRound();

				if (!successful) {
					tabPane.getSelectionModel().select(tabPane.getTabs().size() - 2);
				}
			}
		);
		Tab generateTab = new Tab("Generate Next Round");
		generateTab.setContent(generate);
		tabPane.getTabs().add(generateTab);
	}

	public TabPane getTabPane() {
		return tabPane;
	}

	public void addSwissTab(int roundNumber, Node roundPanel) {
		tabPane.setVisible(true);
		while (tabPane.getTabs().size() > roundNumber) {
			tabPane.getTabs().remove(roundNumber - 2);
		}
		tabPane.getTabs().add(roundNumber, new Tab("Round " + roundNumber, roundPanel));
	}

	public void addSwissTab(int roundNumber, JComponent roundPanel) {
		tabPane.setVisible(true);
		while (tabPane.getTabs().size() > roundNumber) {
			tabPane.getTabs().remove(roundNumber - 2);
		}
		SwingNode node = new SwingNode();
		node.setContent(roundPanel);
		tabPane.getTabs().add(roundNumber, new Tab("Round " + roundNumber, node));
	}

	public void addSingleEliminationTab(int numberOfPlayers, Node roundPanel) {
		tabPane.setVisible(true);
		tabPane.getTabs().add(numberOfPlayers, new Tab("Top " + numberOfPlayers, roundPanel));
	}

	public void addSingleEliminationTab(int numberOfPlayers, JComponent roundPanel) {
		tabPane.setVisible(true);
		SwingNode node = new SwingNode();
		node.setContent(roundPanel);
		tabPane.getTabs().add(numberOfPlayers, new Tab("Top " + numberOfPlayers, node));
	}
}
