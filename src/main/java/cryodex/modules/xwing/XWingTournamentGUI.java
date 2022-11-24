package cryodex.modules.xwing;

import java.awt.BorderLayout;

import javax.swing.*;
import cryodex.modules.TournamentGUI;
import cryodex.widget.JFXSwingPanel;
import cryodex.widget.RoundTabbedPane;
import javafx.embed.swing.JFXPanel;
import javafx.embed.swing.SwingNode;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;
import javafx.scene.control.TabPane;

public class XWingTournamentGUI implements TournamentGUI {
	private RoundTabbedPane roundTabbedPane;
	private XWingRankingTable rankingTable;
	private TabPane roundPane;
	private Node rankingNode;
	private JFXPanel display;
	private final XWingTournament tournament;

	public XWingTournamentGUI(XWingTournament tournament) {
		this.tournament = tournament;
	}

	public JFXPanel getDisplay() {
		if (display == null) {
			var root = new SplitPane();
			root.getItems().addAll(getRoundPanel(), getRankingPanel());
			display = JFXSwingPanel.create(root);
		}
		return display;
	}

	public RoundTabbedPane getRoundTabbedPane() {
		if (roundTabbedPane == null) {
			roundTabbedPane = new RoundTabbedPane();
		}
		return roundTabbedPane;
	}

	public Node getRoundPanel() {
		if (roundPane == null) {
			roundPane = getRoundTabbedPane().getTabPane();
		}
		return roundPane;
	}

	public Node getRankingPanel() {
		if (rankingTable == null) {
			getRankingTable();
		}
		if (rankingNode == null) {
			rankingNode = rankingTable.getTable();
		}
		return rankingNode;
	}

	public XWingRankingTable getRankingTable() {
		if (rankingTable == null) {
			rankingTable = new XWingRankingTable(tournament);
		}
		return rankingTable;
	}

}
