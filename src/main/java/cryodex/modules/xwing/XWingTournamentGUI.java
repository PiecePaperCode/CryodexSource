package cryodex.modules.xwing;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JSplitPane;

import cryodex.components.Bootstrap;
import cryodex.modules.TournamentGUI;
import cryodex.widget.JFXSwingPanel;
import cryodex.widget.RoundTabbedPane;
import javafx.embed.swing.JFXPanel;

public class XWingTournamentGUI implements TournamentGUI {

	private RoundTabbedPane roundTabbedPane;
	private XWingRankingTable rankingTable;
	private JSplitPane tmentSplitter;
	private JPanel roundPane;
	private JFXPanel rankingPane;
	private JPanel display;
	private final XWingTournament tournament;

	public XWingTournamentGUI(XWingTournament tournament) {
		this.tournament = tournament;
	}

	public JPanel getDisplay() {
		if (display == null) {
			display = new JPanel(new BorderLayout());

			display.add(getTmentSplitter(), BorderLayout.CENTER);
		}
		return display;
	}

	public RoundTabbedPane getRoundTabbedPane() {
		if (roundTabbedPane == null) {
			roundTabbedPane = new RoundTabbedPane();
		}
		return roundTabbedPane;
	}

	public JSplitPane getTmentSplitter() {
		if (tmentSplitter == null) {
			tmentSplitter = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
					getRoundPanel(), getRankingPanel());
			tmentSplitter.setResizeWeight(1.0);
		}
		return tmentSplitter;
	}

	public JPanel getRoundPanel() {
		if (roundPane == null) {
			roundPane = new JPanel(new BorderLayout());
			roundPane.add(getRoundTabbedPane(), BorderLayout.CENTER);
		}
		return roundPane;
	}

	public JFXPanel getRankingPanel() {
		if (rankingPane == null) {
			var root = new Bootstrap().VBox();
			root.getChildren().add(getRankingTable().getTable());
			rankingPane = JFXSwingPanel.create(root);
		}
		return rankingPane;
	}

	public XWingRankingTable getRankingTable() {
		if (rankingTable == null) {
			rankingTable = new XWingRankingTable(tournament);
		}
		return rankingTable;
	}

}
