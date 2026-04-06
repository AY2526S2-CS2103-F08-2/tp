package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static seedu.address.testutil.TypicalPersons.PLAYER_AMY;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class PlayerTest {

    // ======================== Role ========================

    @Test
    public void constructor_basicFields_roleIsAlwaysPlayer() {
        Player player = (Player) new PersonBuilder(PLAYER_AMY).build();
        assertEquals(Role.PLAYER, player.getRole());
    }

    // ======================== Fresh stats constructors ========================

    @Test
    public void constructor_noStats_createsFreshStats() {
        Player player = (Player) new PersonBuilder(PLAYER_AMY).build();
        PlayerStats stats = player.getStats();

        assertEquals(0, stats.getGoalsScored());
        assertEquals(0, stats.getMatchesWon());
        assertEquals(0, stats.getMatchesLost());
    }

    @Test
    public void constructor_noStats_eachPlayerHasIndependentStats() {
        Player player1 = (Player) new PersonBuilder(PLAYER_AMY).build();
        Player player2 = (Player) new PersonBuilder(PLAYER_AMY).build();

        assertNotSame(player1.getStats(), player2.getStats());
    }

    // ======================== Explicit stats constructor ========================

    @Test
    public void constructor_withStats_usesProvidedStats() {
        PlayerStats stats = new PlayerStats();
        stats.setGoalsScored(10);
        stats.setMatchesWon(5);
        stats.setMatchesLost(2);

        Player player = (Player) new PersonBuilder(PLAYER_AMY).build();
        Player playerWithStats = new Player(player, stats);

        assertEquals(10, playerWithStats.getStats().getGoalsScored());
        assertEquals(5, playerWithStats.getStats().getMatchesWon());
        assertEquals(2, playerWithStats.getStats().getMatchesLost());
    }

    @Test
    public void constructor_withStats_roleRemainsPlayer() {
        PlayerStats stats = new PlayerStats();
        Player player = (Player) new PersonBuilder(PLAYER_AMY).build();
        Player playerWithStats = new Player(player, stats);

        assertEquals(Role.PLAYER, playerWithStats.getRole());
    }

    // ======================== Copy constructor Player(Player, PlayerStats) ========================

    @Test
    public void copyConstructor_preservesAllPersonFields() {
        Player original = (Player) new PersonBuilder(PLAYER_AMY).build();
        PlayerStats newStats = new PlayerStats();
        newStats.setGoalsScored(99);

        Player copy = new Player(original, newStats);

        assertEquals(original.getName(), copy.getName());
        assertEquals(original.getPhone(), copy.getPhone());
        assertEquals(original.getEmail(), copy.getEmail());
        assertEquals(original.getAddress(), copy.getAddress());
        assertEquals(original.getTags(), copy.getTags());
        assertEquals(original.getTeam(), copy.getTeam());
        assertEquals(original.getStatus(), copy.getStatus());
        assertEquals(original.getPosition(), copy.getPosition());
    }

    @Test
    public void copyConstructor_usesNewStats() {
        Player original = (Player) new PersonBuilder(PLAYER_AMY).build();
        PlayerStats newStats = new PlayerStats();
        newStats.setGoalsScored(99);

        Player copy = new Player(original, newStats);

        assertEquals(99, copy.getStats().getGoalsScored());
    }

    @Test
    public void copyConstructor_doesNotShareStatsWithOriginal() {
        Player original = (Player) new PersonBuilder(PLAYER_AMY).build();
        PlayerStats newStats = new PlayerStats();

        Player copy = new Player(original, newStats);

        assertNotSame(original.getStats(), copy.getStats());
    }

    @Test
    public void copyConstructor_mutatingNewStatsDoesNotAffectOriginal() {
        Player original = (Player) new PersonBuilder(PLAYER_AMY).build();
        original.getStats().setGoalsScored(5);

        PlayerStats newStats = new PlayerStats(original.getStats());
        Player copy = new Player(original, newStats);
        copy.getStats().setGoalsScored(99);

        assertEquals(5, original.getStats().getGoalsScored());
    }

    // ======================== getStats() ========================

    @Test
    public void getStats_returnsCorrectStatsReference() {
        PlayerStats stats = new PlayerStats();
        stats.setMatchesWon(3);

        Player original = (Player) new PersonBuilder(PLAYER_AMY).build();
        Player player = new Player(original, stats);

        assertEquals(stats, player.getStats());
    }
}
