package cryodex.widget;

import cryodex.CryodexController;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class RoundTabbedPane {
	TabPane roundTabbedPane;
	public RoundTabbedPane() {
		roundTabbedPane = new TabPane();
	}

	public Node getNode() {
		return roundTabbedPane;
	}

	public void addSwissTab(int roundNumber, Tab roundPanel) {
		while (roundTabbedPane.getTabs().size() > roundNumber) {
			roundTabbedPane.getTabs().remove(roundNumber - 2);
		}

		roundTabbedPane.getTabs().add(roundNumber, roundPanel);
	}

	public void addSingleEliminationTab(int numberOfPlayers, Tab roundPanel) {
		roundTabbedPane.getTabs().add(numberOfPlayers, roundPanel);
	}
}
