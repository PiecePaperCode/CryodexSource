package cryodex.modules.xwing;

import java.util.*;

import cryodex.components.Bootstrap;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;

public class XWingRankingTable {
	private TableView<RankingModel> table;
	private final XWingTournament tournament;
	private Label title;
	private Label statsLabel;
	private Set<XWingPlayer> xWingPlayers;

	public XWingRankingTable(XWingTournament tournament) {
		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setHmin(300);
		scrollPane.setVmin(400);
		this.tournament = tournament;

		updateLabel();
		VBox labelPanel = new Bootstrap().VBox(
			getTitleLabel(),
			getStatsLabel()
		);
		var root = new Bootstrap().VBox();
		root.getChildren().add(labelPanel);
		root.getChildren().add(scrollPane);
		// root.getChildren().add(new TimerPanel());
	}

	private Label getStatsLabel() {
		if (statsLabel == null) {
			statsLabel = new Label();
		}
		return statsLabel;
	}

	private Label getTitleLabel() {
		if (title == null) {
			title = new Bootstrap().H4("");
		}
		return title;
	}

	public void updateLabel() {
		int total = tournament.getAllXWingPlayers().size();
		int active = tournament.getXWingPlayers().size();

		if (total == 0) {
			total = active;
		}

		int dropped = total - active;
		if (total == active) {
			getStatsLabel().setText("Total Players: " + total);
		} else {
			getStatsLabel().setText(
				"Total Players: " + total + " Active Players: " +
				active
				+ " Dropped Players: " +
				dropped
			);
		}

	}

	public TableView<RankingModel> getTable() {
		if (table == null) {
			table = new TableView<>();
			table.getColumns().add(
				new TableColumn<RankingModel, String>("Name")
				//new TableColumn<>("Score"),
				//new TableColumn<>("SoS"),
				//new TableColumn<>("Mov"),
				//new TableColumn<>("Record"),
				//new TableColumn<>("Byes"),
				//new TableColumn<>("Event Points")
			);
		}
		table.getItems().removeAll();
        System.out.println(xWingPlayers);
		if (xWingPlayers != null) {
            for (RankingModel rank: getRanking()) {
                table.getItems().add(rank);
            }
            System.out.println(getRanking());
		}
		return table;
	}

	public void setPlayers(Set<XWingPlayer> xWingPlayers) {
		this.xWingPlayers = xWingPlayers;
	}

	private ArrayList<RankingModel> getRanking() {
		var ranking = new ArrayList<RankingModel>();
		for (XWingPlayer player: this.xWingPlayers) {
			ranking.add(new RankingModel(player));
		}
		ranking.sort(new RankingComparator());
		return ranking;
	}

	class RankingModel {
		String name;
		int score;
		double soS;
		double mov;
		String record;
		int byes;
		int eventPoints;
		public RankingModel(XWingPlayer player) {
			this.name = player.getName();
			this.score = player.getScore(tournament);
			this.soS = player.getAverageSoS(tournament);
			this.mov = player.getMarginOfVictory(tournament);
			this.record = player.getWins(tournament) + " / "
				+ player.getLosses(tournament) + " / "
				+ player.getDraws(tournament);
			this.byes = player.getByes(tournament);
			this.eventPoints = player.getEventScore(tournament);
		}

		public String getName() {
			return String.valueOf(name);
		}

		public String getScore() {
			return String.valueOf(score);
		}

		public String getSoS() {
			return String.valueOf(soS);
		}

		public String getMov() {
			return String.valueOf(mov);
		}

		public String getRecord() {
			return String.valueOf(record);
		}

		public String getByes() {
			return String.valueOf(byes);
		}

		public String getEventPoints() {
			return String.valueOf(eventPoints);
		}
        public void setName(String name) {
            this.name = name;
        }
        public String toString() {
            return name;
        }
    }

	class RankingComparator implements Comparator<RankingModel> {
		@Override
		public int compare(RankingModel o1, RankingModel o2) {
			if (o1.score < o2.score)
				return -1;
			if (o1.score > o2.score)
				return 1;
			if (o1.eventPoints < o2.eventPoints)
				return -1;
			if (o1.eventPoints > o2.eventPoints)
				return 1;
			return new Random().nextInt(-1, 1);
		}
	}
}
