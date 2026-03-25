package seedu.address.model.person;

/**
 * Represents the recorded performance stats of a {@code Player}.
 * Stats are performance data of players based on their games.
 */
public class PlayerStats {
    private int goalsScored; // total number of goals scored
    private int matchesWon; // total matches won
    private int matchesLost; // total matches lost

    /**
     * Constructs a {@code PlayerStats} with a fresh default state. (all 0s)
     */
    public PlayerStats() {
        this(0, 0, 0);
    }

    /**
     * Constructs a {@code PlayerStats} with the given stat data.
     *
     * @param goalsScored the goals scored
     * @param matchesWon  the matches won
     * @param matchesLost the matches lost
     */
    public PlayerStats(int goalsScored, int matchesWon, int matchesLost) {
        this.goalsScored = goalsScored;
        this.matchesWon = matchesWon;
        this.matchesLost = matchesLost;
    }

    public int getGoalsScored() {
        return goalsScored;
    }

    public void setGoalsScored(int goalsScored) {
        this.goalsScored = goalsScored;
    }

    public int getMatchesWon() {
        return matchesWon;
    }

    public void setMatchesWon(int matchesWon) {
        this.matchesWon = matchesWon;
    }

    public int getMatchesLost() {
        return matchesLost;
    }

    public void setMatchesLost(int matchesLost) {
        this.matchesLost = matchesLost;
    }
}
