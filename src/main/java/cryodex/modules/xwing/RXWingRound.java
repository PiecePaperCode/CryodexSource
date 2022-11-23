package cryodex.modules.xwing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RXWingRound {
    private final ArrayList<List<RXWingMatch>> rounds = new ArrayList<>();
    private final List<RXWingPlayer> players;

    public RXWingRound(List<RXWingPlayer> players) {
        this.players = players;
        this.generate();
    }

    public void generate() {
        if (this.rounds.size() == 0) {
            generateRandom();
        } else {
            generateSwiss();
        }
    }

    public List<RXWingMatch> getMatches(int round) {
        return rounds.get(round);
    }

    public List<RXWingMatch> getMatches() {
        ArrayList<RXWingMatch> matches = new ArrayList<>();
        for (List<RXWingMatch> match: this.rounds) {
            matches.addAll(match);
        }
        return matches;
    }

    private void generateRandom() {
        List<Integer> randomNumbers = new ArrayList<>(
            IntStream.range(0, players.size()).boxed().toList()
        );
        Collections.shuffle(randomNumbers);
        ArrayList<RXWingMatch> matches = new ArrayList<>();
        for (int i = 1; i <= players.size(); i += 2) {
            if (i == players.size()) {
                matches.add(new RXWingMatch(players.get(randomNumbers.get(i - 1))));
            } else {
                matches.add(
                    new RXWingMatch(
                        players.get(randomNumbers.get(i - 1)),
                        players.get(randomNumbers.get(i))
                    )
                );
            }
        }
        this.rounds.add(matches);
    }

    private void generateSwiss() {

    }
}
