package cryodex.modules.xwing;

public class RXWingMatch {
    public final RXWingPlayer player1;
    public final RXWingPlayer player2;
    protected RXWingPlayer winner;
    public static int WIN = 3;
    public static int LOOSE = 0;
    public static int DRAW = 1;
    protected boolean draw = false;
    protected int points1 = 0;
    protected int points2 = 0;

    public RXWingMatch(
        RXWingPlayer player1,
        RXWingPlayer player2
    ) {
        this.player1 = player1;
        this.player2 = player2;
    }

    public RXWingMatch(
            RXWingPlayer player1
    ) {
        this.player1 = player1;
        this.player2 = null;
        setWinner(18, 0);
    }

    public void setWinner(int points1, int points2) {
        if (points1 < points2) {
            this.winner = player2;
            this.draw = false;
        } else if(points1 == points2) {
            this.winner = null;
            draw = true;
        } else {
            this.winner = player1;
            this.draw = false;
        }
        this.points1 = points1;
        this.points2 = points2;
    }

    public int getScore(RXWingPlayer player){
        if (this.winner == player) {
            return WIN;
        } else if (this.draw) {
            return DRAW;
        }
        return LOOSE;
    }

    public boolean isCompleted() {
        return this.winner != null || this.draw;
    }
}
