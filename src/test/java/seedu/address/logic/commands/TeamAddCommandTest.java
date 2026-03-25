package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.model.util.SampleDataUtil.getSampleAddressBook;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Team;

/**
 * Contains integration tests for {@link TeamAddCommand}.
 */
public class TeamAddCommandTest {

    @Test
    public void constructor_nullTeam_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new TeamAddCommand(null));
    }

    @Test
    public void execute_newTeam_success() {
        Model model = new ModelManager(getSampleAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Team teamToAdd = new Team("Reserve Team");

        expectedModel.addTeam(teamToAdd);

        assertCommandSuccess(new TeamAddCommand(teamToAdd), model,
                String.format(TeamAddCommand.MESSAGE_SUCCESS, teamToAdd), expectedModel);
    }

    @Test
    public void execute_duplicateTeam_throwsCommandException() {
        Model model = new ModelManager(getSampleAddressBook(), new UserPrefs());
        Team duplicateTeam = new Team("First Team");

        assertCommandFailure(new TeamAddCommand(duplicateTeam), model, TeamAddCommand.MESSAGE_DUPLICATE_TEAM);
    }

    @Test
    public void equals() {
        TeamAddCommand addFirstTeam = new TeamAddCommand(new Team("First Team"));
        TeamAddCommand addReserveTeam = new TeamAddCommand(new Team("Reserve Team"));

        assertTrue(addFirstTeam.equals(addFirstTeam));
        assertTrue(addFirstTeam.equals(new TeamAddCommand(new Team("first team"))));
        assertFalse(addFirstTeam.equals(1));
        assertFalse(addFirstTeam.equals(null));
        assertFalse(addFirstTeam.equals(addReserveTeam));
    }

    @Test
    public void toStringMethod() {
        Team team = new Team("Reserve Team");
        TeamAddCommand command = new TeamAddCommand(team);
        String expected = TeamAddCommand.class.getCanonicalName() + "{toAdd=" + team + "}";
        assertEquals(expected, command.toString());
    }
}

