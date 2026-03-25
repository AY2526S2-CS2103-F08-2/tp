package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.PlayerStats;
import seedu.address.model.person.StatField;

import java.util.HashMap;
import java.util.Map;

/**
 * Jackson-friendly version of {@link PlayerStats}.
 */
public class JsonAdaptedPlayerStats {
    private final Map<String, Integer> stats;

    /**
     * Constructs a {@code JsonAdaptedPlayerStats} with the given stats mapping.
     */
    @JsonCreator
    public JsonAdaptedPlayerStats(@JsonProperty("stats") Map<String, Integer> stats) {
        this.stats = stats != null ? stats : new HashMap<>();
    }

    /**
     * Converts a given {@code PlayerStats} into this class for Jackson use.
     */
    public JsonAdaptedPlayerStats(PlayerStats source) {
        this.stats = new HashMap<>();
        for (StatField field : StatField.values()) {
            stats.put(field.name(), field.getValue(source));
        }
    }

    /**
     * Converts this Jackson-friendly adapted player stats object into the model's {@code PlayerStats} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted player stats.
     */
    public PlayerStats toModelType() throws IllegalValueException {
        PlayerStats playerStats = new PlayerStats();
        for (StatField field: StatField.values()) {
            int value = stats.getOrDefault(field.name(), 0);
            if (!field.isValid(value)) {
                throw new IllegalValueException(field.messageConstraints);
            }

            field.setValue(playerStats, value);
        }

        return playerStats;
    }
}
