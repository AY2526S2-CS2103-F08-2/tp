package seedu.address.model.person;

import java.util.function.Function;

/**
 * Represents an advanced stat field that is calculated with one or more {@link StatField}s,
 * returning a read-only value after the calculation.
 *
 * <p> Add a new calculated stat type by declaring a new enum constant and get all values needed
 * from the provided {@link PlayerStats}
 */
public enum CalculatedStatField {
    WIN_RATE(stats -> {
        long total = (long) stats.getMatchesWon() + stats.getMatchesLost();
        if (total == 0) {
            // deal with zero division
            return 0.0;
        }
        return ((double) stats.getMatchesWon() / total) * 100;
    }),

    GOALS_PER_GAME(stats -> {
        long total = (long) stats.getMatchesWon() + stats.getMatchesLost();
        if (total == 0) {
            // deal with zero division
            return 0.0;
        }
        return (double) stats.getGoalsScored() / total;
    });

    private final Function<PlayerStats, Double> getter;

    CalculatedStatField(Function<PlayerStats, Double> getter) {
        this.getter = getter;
    }

    /**
     * Gets the value of the specific {@code CalculatedStatField} from the given {@code PlayerStats stats}.
     *
     * @param stats the given player stats
     * @return the calculated stat value
     */
    public double getValue(PlayerStats stats) {
        return getter.apply(stats);
    }
}
