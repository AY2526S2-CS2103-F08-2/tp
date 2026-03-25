package seedu.address.model.person;

import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * Represents a specific player stat field belonging to a {@link PlayerStats},
 * mapping to a numeric value that tracks a player's performance.
 *
 * <p> Each {@code StatField} will encapsulate the getter/setter
 * for its corresponding primitive field in {@code PlayerStats},
 * allowing stat updates to be performed easily after parsing.
 *
 * <p> Add a new stat type by declaring a new enum constant with the
 * corresponding method references.
 */
public enum StatField {
    GOALS(PlayerStats::getGoalsScored, PlayerStats::setGoalsScored),
    WINS(PlayerStats::getMatchesWon, PlayerStats::setMatchesWon),
    LOSSES(PlayerStats::getMatchesLost, PlayerStats::setMatchesLost);

    private final Function<PlayerStats, Integer> getter;
    private final BiConsumer<PlayerStats, Integer> setter;

    StatField(Function<PlayerStats, Integer> getter, BiConsumer<PlayerStats, Integer> setter) {
        this.getter = getter;
        this.setter = setter;
    }

    /**
     * Gets the value of the specific {@code StatField} from the given {@code PlayerStats stats}.
     *
     * @param stats the given player stats
     * @return the stat value
     */
    public int getValue(PlayerStats stats) {
        return this.getter.apply(stats);
    }

    /**
     * Sets the value of the specific {@code StatField} of the given {@code PlayerStats stats} with the given value.
     *
     * @param stats the given player stats
     * @param value a valid stats value
     */
    public void setValue(PlayerStats stats, int value) {
        setter.accept(stats, value);
    }
}
