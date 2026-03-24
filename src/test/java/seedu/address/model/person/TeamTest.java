package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class TeamTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Team(null));
    }

    @Test
    public void constructor_invalidTeam_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Team(" "));
    }

    @Test
    public void constructor_validTeam_trimsWhitespace() {
        Team team = new Team("  First Team  ");
        assertTrue(team.equals(new Team("First Team")));
    }

    @Test
    public void isValidTeamName() {
        assertFalse(Team.isValidTeamName(""));
        assertFalse(Team.isValidTeamName(" "));
        assertFalse(Team.isValidTeamName("#Team"));

        assertTrue(Team.isValidTeamName("First Team"));
        assertTrue(Team.isValidTeamName("U21 Squad"));
        assertTrue(Team.isValidTeamName("Team-2"));
    }

    @Test
    public void equals_caseInsensitive_returnsTrue() {
        Team lower = new Team("first team");
        Team upper = new Team("First Team");

        assertTrue(lower.equals(upper));
        assertTrue(lower.hashCode() == upper.hashCode());
    }
}

