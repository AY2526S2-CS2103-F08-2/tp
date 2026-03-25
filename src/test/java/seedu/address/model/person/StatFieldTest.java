package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StatFieldTest {

    private PlayerStats stats;

    @BeforeEach
    public void setUp() {
        stats = new PlayerStats();
    }

    // ======================== isValid() ========================

    @Test
    public void isValid_zeroValue_returnsTrue() {
        assertTrue(StatField.GOALS.isValid(0));
        assertTrue(StatField.WINS.isValid(0));
        assertTrue(StatField.LOSSES.isValid(0));
    }

    @Test
    public void isValid_positiveValue_returnsTrue() {
        assertTrue(StatField.GOALS.isValid(10));
        assertTrue(StatField.WINS.isValid(5));
        assertTrue(StatField.LOSSES.isValid(3));
    }

    @Test
    public void isValid_negativeValue_returnsFalse() {
        assertFalse(StatField.GOALS.isValid(-1));
        assertFalse(StatField.WINS.isValid(-1));
        assertFalse(StatField.LOSSES.isValid(-1));
    }

    // ======================== getValue() ========================

    @Test
    public void getValue_defaultStats_returnsZero() {
        assertEquals(0, StatField.GOALS.getValue(stats));
        assertEquals(0, StatField.WINS.getValue(stats));
        assertEquals(0, StatField.LOSSES.getValue(stats));
    }

    @Test
    public void getValue_afterSetValue_returnsCorrectValue() {
        StatField.GOALS.setValue(stats, 5);
        assertEquals(5, StatField.GOALS.getValue(stats));

        StatField.WINS.setValue(stats, 3);
        assertEquals(3, StatField.WINS.getValue(stats));

        StatField.LOSSES.setValue(stats, 2);
        assertEquals(2, StatField.LOSSES.getValue(stats));
    }

    // ======================== setValue() ========================

    @Test
    public void setValue_validValue_updatesCorrectField() {
        StatField.GOALS.setValue(stats, 7);
        assertEquals(7, stats.getGoalsScored());
        // other fields unaffected
        assertEquals(0, stats.getMatchesWon());
        assertEquals(0, stats.getMatchesLost());
    }

    @Test
    public void setValue_wins_doesNotAffectOtherFields() {
        StatField.WINS.setValue(stats, 4);
        assertEquals(4, stats.getMatchesWon());
        assertEquals(0, stats.getGoalsScored());
        assertEquals(0, stats.getMatchesLost());
    }

    @Test
    public void setValue_losses_doesNotAffectOtherFields() {
        StatField.LOSSES.setValue(stats, 2);
        assertEquals(2, stats.getMatchesLost());
        assertEquals(0, stats.getGoalsScored());
        assertEquals(0, stats.getMatchesWon());
    }

    @Test
    public void setValue_overwritesPreviousValue() {
        StatField.GOALS.setValue(stats, 5);
        StatField.GOALS.setValue(stats, 10);
        assertEquals(10, StatField.GOALS.getValue(stats));
    }

    // ======================== messageConstraints ========================

    @Test
    public void messageConstraints_notNull() {
        for (StatField field : StatField.values()) {
            assertTrue(field.messageConstraints != null && !field.messageConstraints.isEmpty());
        }
    }

    // ======================== Enum coverage ========================

    @Test
    public void values_containsExpectedFields() {
        StatField[] fields = StatField.values();
        assertEquals(3, fields.length);

        // verify all expected fields are present
        boolean hasGoals = false;
        boolean hasWins = false;
        boolean hasLosses = false;
        for (StatField field : fields) {
            if (field == StatField.GOALS) {
                hasGoals = true;
            }
            if (field == StatField.WINS) {
                hasWins = true;
            }
            if (field == StatField.LOSSES) {
                hasLosses = true;
            }
        }
        assertTrue(hasGoals);
        assertTrue(hasWins);
        assertTrue(hasLosses);
    }
}
