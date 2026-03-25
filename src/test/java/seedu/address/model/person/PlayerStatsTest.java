package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

public class PlayerStatsTest {

    // ======================== Default Constructor ========================

    @Test
    public void constructor_defaultValues_allZero() {
        PlayerStats stats = new PlayerStats();
        assertEquals(0, stats.getGoalsScored());
        assertEquals(0, stats.getMatchesWon());
        assertEquals(0, stats.getMatchesLost());
    }

    // ======================== Setters ========================

    @Test
    public void setGoalsScored_validValue_success() {
        PlayerStats stats = new PlayerStats();
        stats.setGoalsScored(5);
        assertEquals(5, stats.getGoalsScored());
    }

    @Test
    public void setMatchesWon_validValue_success() {
        PlayerStats stats = new PlayerStats();
        stats.setMatchesWon(3);
        assertEquals(3, stats.getMatchesWon());
    }

    @Test
    public void setMatchesLost_validValue_success() {
        PlayerStats stats = new PlayerStats();
        stats.setMatchesLost(2);
        assertEquals(2, stats.getMatchesLost());
    }

    @Test
    public void setGoalsScored_zeroValue_success() {
        PlayerStats stats = new PlayerStats();
        stats.setGoalsScored(10);
        stats.setGoalsScored(0);
        assertEquals(0, stats.getGoalsScored());
    }

    // ======================== equals() ========================

    @Test
    public void equals_sameValues_returnsTrue() {
        PlayerStats a = new PlayerStats();
        PlayerStats b = new PlayerStats();
        assertEquals(a, b);
    }

    @Test
    public void equals_differentGoals_returnsFalse() {
        PlayerStats a = new PlayerStats();
        PlayerStats b = new PlayerStats();
        a.setGoalsScored(1);
        assertNotEquals(a, b);
    }

    @Test
    public void equals_differentWins_returnsFalse() {
        PlayerStats a = new PlayerStats();
        PlayerStats b = new PlayerStats();
        a.setMatchesWon(1);
        assertNotEquals(a, b);
    }

    @Test
    public void equals_differentLosses_returnsFalse() {
        PlayerStats a = new PlayerStats();
        PlayerStats b = new PlayerStats();
        a.setMatchesLost(1);
        assertNotEquals(a, b);
    }

    @Test
    public void equals_sameObject_returnsTrue() {
        PlayerStats stats = new PlayerStats();
        assertEquals(stats, stats);
    }

    @Test
    public void equals_null_returnsFalse() {
        PlayerStats stats = new PlayerStats();
        assertNotEquals(stats, null);
    }

    @Test
    public void equals_differentType_returnsFalse() {
        PlayerStats stats = new PlayerStats();
        assertNotEquals(stats, "not a PlayerStats");
    }

    // ======================== toString() ========================

    @Test
    public void toString_defaultValues_correctFormat() {
        PlayerStats stats = new PlayerStats();
        assertEquals("{ goalsScored = 0, matchesWon = 0, matchesLost = 0 }", stats.toString());
    }

    @Test
    public void toString_nonDefaultValues_correctFormat() {
        PlayerStats stats = new PlayerStats();
        stats.setGoalsScored(3);
        stats.setMatchesWon(2);
        stats.setMatchesLost(1);
        assertEquals("{ goalsScored = 3, matchesWon = 2, matchesLost = 1 }", stats.toString());
    }
}
