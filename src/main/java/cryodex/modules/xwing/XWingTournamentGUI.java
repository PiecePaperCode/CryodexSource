package cryodex.modules.xwing;

import java.awt.BorderLayout;

import javax.swing.*;

import cryodex.components.Bootstrap;
import cryodex.modules.TournamentGUI;
import cryodex.widget.JFXSwingPanel;
import cryodex.widget.RoundTabbedPane;
import javafx.embed.swing.JFXPanel;
import javafx.embed.swing.SwingNode;
import javafx.scene.Node;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.VBox;

public class XWingTournamentGUI implements TournamentGUI {
	private RoundTabbedPane roundTabbedPane;
	private XWingRankingTable rankingTable;
	private JPanel roundPane;
	private Node rankingNode;
	private JFXPanel display;
	private final XWingTournament tournament;

	public XWingTournamentGUI(XWingTournament tournament) {
		this.tournament = tournament;
	}

	public JFXPanel getDisplay() {
		if (display == null) {
			var root = new SplitPane();
			var swingNode = new SwingNode();
			swingNode.setContent(getRoundPanel());
			root.getItems().addAll(swingNode, getRankingPanel());
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

	public JPanel getRoundPanel() {
		if (roundPane == null) {
			roundPane = new JPanel(new BorderLayout());
			roundPane.add(getRoundTabbedPane(), BorderLayout.CENTER);
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
