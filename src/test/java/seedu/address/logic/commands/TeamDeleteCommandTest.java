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
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests for {@link TeamDeleteCommand}.
 */
public class TeamDeleteCommandTest {

    @Test
    // INVALID_CASE + EP_INVALID (null input)
    public void constructor_nullTeam_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new TeamDeleteCommand(null));
    }

    @Test
    // VALID_CASE + EP_VALID
    public void execute_existingTeam_success() {
        Model model = new ModelManager(getSampleAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Team teamToDelete = new Team("Second Team");

        expectedModel.deleteTeam(teamToDelete);

        assertCommandSuccess(new TeamDeleteCommand(teamToDelete), model,
                String.format(TeamDeleteCommand.MESSAGE_SUCCESS, teamToDelete), expectedModel);
    }

    @Test
    // INVALID_CASE + EP_INVALID (value not found)
    public void execute_missingTeam_throwsCommandException() {
        Model model = new ModelManager(getSampleAddressBook(), new UserPrefs());

        assertCommandFailure(new TeamDeleteCommand(new Team("Ghost Team")),
                model, TeamDeleteCommand.MESSAGE_TEAM_NOT_FOUND);
    }

    @Test
    // INVALID_CASE + DEPENDENCY_RULE (protected default)
    public void execute_defaultTeam_throwsCommandException() {
        Model model = new ModelManager(getSampleAddressBook(), new UserPrefs());

        assertCommandFailure(new TeamDeleteCommand(new Team(Team.DEFAULT_UNASSIGNED_TEAM)),
                model, TeamDeleteCommand.MESSAGE_CANNOT_DELETE_DEFAULT_TEAM);
    }

    @Test
    // INVALID_CASE + DEPENDENCY_RULE (cannot delete in-use value)
    public void execute_teamInUse_throwsCommandException() {
        Model model = new ModelManager(getSampleAddressBook(), new UserPrefs());
        Team inUseTeam = new Team("Reserve Team");
        model.addTeam(inUseTeam);
        model.addPerson(new PersonBuilder().withTeam("Reserve Team").build());

        assertCommandFailure(new TeamDeleteCommand(inUseTeam), model, TeamDeleteCommand.MESSAGE_TEAM_IN_USE);
    }

    @Test
    public void equals() {
        TeamDeleteCommand deleteFirstTeam = new TeamDeleteCommand(new Team("First Team"));
        TeamDeleteCommand deleteSecondTeam = new TeamDeleteCommand(new Team("Second Team"));

        assertTrue(deleteFirstTeam.equals(deleteFirstTeam));
        assertTrue(deleteFirstTeam.equals(new TeamDeleteCommand(new Team("first team"))));
        assertFalse(deleteFirstTeam.equals(1));
        assertFalse(deleteFirstTeam.equals(null));
        assertFalse(deleteFirstTeam.equals(deleteSecondTeam));
    }

    @Test
    public void toStringMethod() {
        Team team = new Team("Second Team");
        TeamDeleteCommand command = new TeamDeleteCommand(team);
        String expected = TeamDeleteCommand.class.getCanonicalName() + "{toDelete=" + team + "}";
        assertEquals(expected, command.toString());
    }
}
