package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.PlayerStats;
import seedu.address.model.person.StatField;

public class JsonAdaptedPlayerStatsTest {

    @Test
    public void toModelType_allZeroStats_success() throws IllegalValueException {
        Map<String, Integer> stats = new HashMap<>();
        stats.put("GOALS", 0);
        stats.put("WINS", 0);
        stats.put("LOSSES", 0);

        JsonAdaptedPlayerStats adapted = new JsonAdaptedPlayerStats(stats);

        PlayerStats result = adapted.toModelType();
        assertEquals(0, result.getGoalsScored());
        assertEquals(0, result.getMatchesWon());
        assertEquals(0, result.getMatchesLost());
    }

    @Test
    public void toModelType_validNonZeroStats_success() throws IllegalValueException {
        Map<String, Integer> stats = new HashMap<>();
        stats.put("GOALS", 5);
        stats.put("WINS", 3);
        stats.put("LOSSES", 2);

        JsonAdaptedPlayerStats adapted = new JsonAdaptedPlayerStats(stats);

        PlayerStats result = adapted.toModelType();
        assertEquals(5, result.getGoalsScored());
        assertEquals(3, result.getMatchesWon());
        assertEquals(2, result.getMatchesLost());
    }

    @Test
    public void toModelType_missingKeys_defaultsToZero() throws IllegalValueException {
        Map<String, Integer> stats = new HashMap<>();

        JsonAdaptedPlayerStats adapted = new JsonAdaptedPlayerStats(stats);

        PlayerStats result = adapted.toModelType();
        assertEquals(0, result.getGoalsScored());
        assertEquals(0, result.getMatchesWon());
        assertEquals(0, result.getMatchesLost());
    }

    @Test
    public void toModelType_negativeGoals_throwsIllegalValueException() {
        Map<String, Integer> stats = new HashMap<>();
        stats.put("GOALS", -1);
        stats.put("WINS", 0);
        stats.put("LOSSES", 0);

        JsonAdaptedPlayerStats adapted = new JsonAdaptedPlayerStats(stats);

        assertThrows(IllegalValueException.class, adapted::toModelType);
    }

    @Test
    public void toModelType_negativeWins_throwsIllegalValueException() {
        Map<String, Integer> stats = new HashMap<>();
        stats.put("GOALS", 0);
        stats.put("WINS", -1);
        stats.put("LOSSES", 0);

        JsonAdaptedPlayerStats adapted = new JsonAdaptedPlayerStats(stats);

        assertThrows(IllegalValueException.class, adapted::toModelType);
    }

    @Test
    public void toModelType_negativeLosses_throwsIllegalValueException() {
        Map<String, Integer> stats = new HashMap<>();
        stats.put("GOALS", 0);
        stats.put("WINS", 0);
        stats.put("LOSSES", -1);

        JsonAdaptedPlayerStats adapted = new JsonAdaptedPlayerStats(stats);

        assertThrows(IllegalValueException.class, adapted::toModelType);
    }

    @Test
    public void roundTrip_defaultStats_preservesValues() throws IllegalValueException {
        PlayerStats original = new PlayerStats();

        JsonAdaptedPlayerStats adapted = new JsonAdaptedPlayerStats(original);
        PlayerStats result = adapted.toModelType();

        assertEquals(original, result);
    }

    @Test
    public void roundTrip_nonZeroStats_preservesValues() throws IllegalValueException {
        PlayerStats original = new PlayerStats();
        original.setGoalsScored(7);
        original.setMatchesWon(4);
        original.setMatchesLost(3);

        JsonAdaptedPlayerStats adapted = new JsonAdaptedPlayerStats(original);
        PlayerStats result = adapted.toModelType();

        assertEquals(original, result);
    }

    @Test
    public void constructor_fromMap_reflectsAddedValues() {
        Map<String, Integer> stats = new HashMap<>();
        stats.put("GOALS", 9);

        JsonAdaptedPlayerStats adapted = new JsonAdaptedPlayerStats(stats);

        assertEquals(9, adapted.getStats().get("GOALS"));
    }

    @Test
    public void constructor_fromPlayerStats_mapsAllStatFields() {
        PlayerStats source = new PlayerStats();
        source.setGoalsScored(2);
        source.setMatchesWon(1);
        source.setMatchesLost(1);

        JsonAdaptedPlayerStats adapted = new JsonAdaptedPlayerStats(source);
        Map<String, Integer> statsMap = adapted.getStats();

        for (StatField field : StatField.values()) {
            assertTrue(statsMap.containsKey(field.name()), "Missing key: " + field.name());
        }

        assertEquals(2, statsMap.get("GOALS"));
        assertEquals(1, statsMap.get("WINS"));
        assertEquals(1, statsMap.get("LOSSES"));
    }
}
