package cryodex.modules.xwing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RXWingTournament {
    private final ArrayList<RXWingPlayer> players = new ArrayList<>();
    private final ArrayList<RXWingPlayer> droppedPlayers = new ArrayList<>();
    private final RXWingRound rounds;

    public RXWingTournament(List<RXWingPlayer> players) {
        this.players.addAll(players);
        this.rounds = new RXWingRound(players);
    }

    public void addPlayer(RXWingPlayer player) {
        this.players.add(player);
    }

    public void dropPlayer(RXWingPlayer player) {
        if (this.players.remove(player)) {
            droppedPlayers.add(player);
        }
    }

    public List<RXWingPlayer> getPlayers() {
        return players;
    }

    public RXWingRound getRounds() {
        return rounds;
    }
}
