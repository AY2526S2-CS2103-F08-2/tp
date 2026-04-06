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
    }

    /**
     * Copy constructor of {@code PlayerStats}.
     *
     * @param toCopy {@code PlayerStats} to copy
     */
    public PlayerStats(PlayerStats toCopy) {
        this.goalsScored = toCopy.goalsScored;
        this.matchesWon = toCopy.matchesWon;
        this.matchesLost = toCopy.matchesLost;
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


    @Override
    public String toString() {
        return "{ goalsScored = " + goalsScored
                + ", matchesWon = " + matchesWon
                + ", matchesLost = " + matchesLost + " }";
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PlayerStats)) {
            return false;
        }

        // temp hardcode equals for now
        PlayerStats otherPlayerStats = (PlayerStats) other;
        return otherPlayerStats.getGoalsScored() == goalsScored
                && otherPlayerStats.getMatchesWon() == matchesWon
                && otherPlayerStats.getMatchesLost() == matchesLost;
    }
}
