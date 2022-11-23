import cryodex.modules.xwing.RXWingPlayer;
import cryodex.modules.xwing.RXWingTournament;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import static org.junit.Assert.*;

public class TestTournament {
    RXWingTournament tournament;
    @Before
    public void testTournament() {
        List<RXWingPlayer> players = List.of(
            new RXWingPlayer("Player 1"),
            new RXWingPlayer("Player 2"),
            new RXWingPlayer("Player 3"),
            new RXWingPlayer("Player 4"),
            new RXWingPlayer("Player 5")
        );
        tournament = new RXWingTournament(players);
    }

    @Test
    public void testPlayer() {
        RXWingPlayer player = new RXWingPlayer("Player 6");
        tournament.addPlayer(player);
        assertTrue(tournament.getPlayers().contains(player));
        tournament.dropPlayer(player);
        assertFalse(tournament.getPlayers().contains(player));
        assertEquals(5, tournament.getPlayers().size());
    }

    @Test
    public void testRounds() {
        tournament.getRounds().generate();
        System.out.println(tournament.getRounds().getMatches());
        assertEquals(3, tournament.getRounds().getMatches().size());
        assertEquals(3, tournament.getRounds().getMatches(0).size());
    }
}
