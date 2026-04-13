package seedu.address.model.person;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;

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
    GOALS(PlayerStats::getGoalsScored, PlayerStats::setGoalsScored,
            x -> x >= 0, StatField.MESSAGE_GTE_ZERO),
    WINS(PlayerStats::getMatchesWon, PlayerStats::setMatchesWon,
            x -> x >= 0, StatField.MESSAGE_GTE_ZERO),
    LOSSES(PlayerStats::getMatchesLost, PlayerStats::setMatchesLost,
            x -> x >= 0, StatField.MESSAGE_GTE_ZERO),
    DRAWS(PlayerStats::getMatchesDrawn, PlayerStats::setMatchesDrawn,
            x -> x >= 0, StatField.MESSAGE_GTE_ZERO);

    public static final String MESSAGE_GTE_ZERO = "The value for goals should be more or equal to 0.";

    public final String messageConstraints;

    private final Function<PlayerStats, Integer> getter;
    private final BiConsumer<PlayerStats, Integer> setter;
    private final Predicate<Integer> validator; // check if the value is valid

    StatField(Function<PlayerStats, Integer> getter, BiConsumer<PlayerStats, Integer> setter,
              Predicate<Integer> validator, String messageConstraints) {
        this.getter = getter;
        this.setter = setter;
        this.validator = validator;
        this.messageConstraints = messageConstraints;
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

    /**
     * Checks whether the given value is considered a valid value for the stat.
     *
     * @param value the value to test
     * @return true if valid else false
     */
    public boolean isValid(int value) {
        return validator.test(value);
    }
}
