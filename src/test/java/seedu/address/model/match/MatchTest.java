package seedu.address.model.match;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.event.Date;
import seedu.address.model.event.EventName;
import seedu.address.model.event.EventPlayerList;
import seedu.address.model.event.match.Match;
import seedu.address.model.person.Person;
import seedu.address.model.person.Role;
import seedu.address.testutil.MatchBuilder;
import seedu.address.testutil.PersonBuilder;

public class MatchTest {

    private final Person playerA = new PersonBuilder().withName("Alice").withRole(Role.PLAYER).build();
    private final Person playerB = new PersonBuilder().withName("Bob").withRole(Role.PLAYER).build();

    private final EventPlayerList playerListA = new EventPlayerList(List.of(playerA));
    private final EventPlayerList playerListB = new EventPlayerList(List.of(playerB));

    @Test
    public void constructor_nullFields_throwsNullPointerException() {
        EventName name = new EventName("Team A");
        Date date = new Date("2026-04-15 1600");

        assertThrows(NullPointerException.class, () -> new Match(null, date, playerListA));
        assertThrows(NullPointerException.class, () -> new Match(name, null, playerListA));
        assertThrows(NullPointerException.class, () -> new Match(name, date, null));
    }

    @Test
    public void isSameMatch_sameObject_returnsTrue() {
        Match match = new MatchBuilder().build();
        assertTrue(match.isSameMatch(match));
    }

    @Test
    public void isSameMatch_null_returnsFalse() {
        Match match = new MatchBuilder().build();
        assertFalse(match.isSameMatch(null));
    }

    @Test
    public void isSameMatch_sameIdentityFields_returnsTrue() {
        Match match1 = new MatchBuilder()
                .withOpponentName("Team A")
                .withDate("2026-04-15 1600")
                .build();

        Match match2 = new MatchBuilder()
                .withOpponentName("Team A")
                .withDate("2026-04-15 1600")
                .build();

        assertTrue(match1.isSameMatch(match2));
    }

    @Test
    public void isSameMatch_differentOpponent_returnsFalse() {
        Match match1 = new MatchBuilder().withOpponentName("Team A").build();
        Match match2 = new MatchBuilder().withOpponentName("Team B").build();

        assertFalse(match1.isSameMatch(match2));
    }

    @Test
    public void isSameMatch_differentDate_returnsFalse() {
        Match match1 = new MatchBuilder().withDate("2026-04-15 1600").build();
        Match match2 = new MatchBuilder().withDate("2026-04-16 1600").build();

        assertFalse(match1.isSameMatch(match2));
    }

    @Test
    public void equals() {
        Match match = new MatchBuilder()
                .withOpponentName("Team A")
                .withDate("2026-04-15 1600")
                .build();

        Match sameMatch = new MatchBuilder(match).build();

        Match differentOpponent = new MatchBuilder(match)
                .withOpponentName("Different Team")
                .build();

        Match differentDate = new MatchBuilder(match)
                .withDate("2026-04-16 1600")
                .build();

        Match differentPlayers = new Match(
                match.getOpponentName(),
                match.getMatchDate(),
                playerListB
        );

        // same values
        assertTrue(match.equals(sameMatch));

        // same object
        assertTrue(match.equals(match));

        // null
        assertFalse(match.equals(null));

        // different type
        assertFalse(match.equals("string"));

        // different fields
        assertFalse(match.equals(differentOpponent));
        assertFalse(match.equals(differentDate));
        assertFalse(match.equals(differentPlayers));
    }

    @Test
    public void hashCode_consistency() {
        Match match1 = new MatchBuilder().build();
        Match match2 = new MatchBuilder(match1).build();

        assertEquals(match1.hashCode(), match2.hashCode());
    }

    @Test
    public void toStringMethod() {
        Match match = new MatchBuilder().build();

        String expected = new seedu.address.commons.util.ToStringBuilder(match)
                .add("opponent name", match.getOpponentName())
                .add("match date", match.getMatchDate())
                .add("players", match.getMatchPlayerList())
                .toString();

        assertEquals(expected, match.toString());
    }
}
