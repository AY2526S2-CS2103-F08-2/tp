package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CalculatedStatFieldTest {

    private static final double DELTA = 0.01;

    private PlayerStats stats;

    @BeforeEach
    public void setUp() {
        stats = new PlayerStats();
    }

    // ======================== WIN_RATE ========================

    @Test
    public void getValue_winRate_noGamesPlayed() {
        assertEquals(0.0, CalculatedStatField.WIN_RATE.getValue(stats), DELTA);
    }

    @Test
    public void getValue_winRate_allWins() {
        stats.setMatchesWon(5);
        assertEquals(100.0, CalculatedStatField.WIN_RATE.getValue(stats), DELTA);
    }

    @Test
    public void getValue_winRate_allLosses() {
        stats.setMatchesLost(5);
        assertEquals(0.0, CalculatedStatField.WIN_RATE.getValue(stats), DELTA);
    }

    @Test
    public void getValue_winRate_mixedRecord() {
        stats.setMatchesWon(3);
        stats.setMatchesLost(1);
        // 3 / (3 + 1) * 100 = 75.0
        assertEquals(75.0, CalculatedStatField.WIN_RATE.getValue(stats), DELTA);
    }

    @Test
    public void getValue_winRate_equalWinsAndLosses() {
        stats.setMatchesWon(4);
        stats.setMatchesLost(4);
        assertEquals(50.0, CalculatedStatField.WIN_RATE.getValue(stats), DELTA);
    }

    // ======================== GOALS_PER_GAME ========================

    @Test
    public void getValue_goalsPerGame_noGamesPlayed() {
        assertEquals(0.0, CalculatedStatField.GOALS_PER_GAME.getValue(stats), DELTA);
    }

    @Test
    public void getValue_goalsPerGame_noGoals() {
        stats.setMatchesWon(3);
        stats.setMatchesLost(2);
        assertEquals(0.0, CalculatedStatField.GOALS_PER_GAME.getValue(stats), DELTA);
    }

    @Test
    public void getValue_goalsPerGame_normalValues() {
        stats.setGoalsScored(10);
        stats.setMatchesWon(3);
        stats.setMatchesLost(2);
        // 10 / (3 + 2) = 2.0
        assertEquals(2.0, CalculatedStatField.GOALS_PER_GAME.getValue(stats), DELTA);
    }

    @Test
    public void getValue_goalsPerGame_moreGoalsThanGames() {
        stats.setGoalsScored(7);
        stats.setMatchesWon(2);
        stats.setMatchesLost(1);
        // 7 / (2 + 1) = 2.33
        assertEquals(2.33, CalculatedStatField.GOALS_PER_GAME.getValue(stats), DELTA);
    }

    @Test
    public void values_containsExpectedFields() {
        CalculatedStatField[] fields = CalculatedStatField.values();
        assertEquals(2, fields.length);

        boolean hasWinRate = false;
        boolean hasGoalsPerGame = false;
        for (CalculatedStatField field : fields) {
            if (field == CalculatedStatField.WIN_RATE) {
                hasWinRate = true;
            }
            if (field == CalculatedStatField.GOALS_PER_GAME) {
                hasGoalsPerGame = true;
            }
        }
        assertTrue(hasWinRate);
        assertTrue(hasGoalsPerGame);
    }

    @Test
    public void getValue_winRate_maxWinsAndLosses() {
        stats.setMatchesWon(Integer.MAX_VALUE);
        stats.setMatchesLost(Integer.MAX_VALUE);
        // should be 50.00%, not a negative/nonsensical value
        double result = CalculatedStatField.WIN_RATE.getValue(stats);
        assertEquals(50.0, result, DELTA);
        assertTrue(result >= 0.0 && result <= 100.0);
    }

    @Test
    public void getValue_goalsPerGame_maxWinsAndLosses() {
        stats.setGoalsScored(Integer.MAX_VALUE);
        stats.setMatchesWon(Integer.MAX_VALUE);
        stats.setMatchesLost(Integer.MAX_VALUE);
        // should be 0.50, not negative
        double result = CalculatedStatField.GOALS_PER_GAME.getValue(stats);
        assertEquals(0.5, result, DELTA);
        assertTrue(result >= 0.0);
    }

    @Test
    public void getValue_winRate_maxWinsOneLoss() {
        stats.setMatchesWon(Integer.MAX_VALUE);
        stats.setMatchesLost(1);
        double result = CalculatedStatField.WIN_RATE.getValue(stats);
        assertTrue(result >= 0.0 && result <= 100.0);
    }
}
