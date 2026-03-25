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
 * Contains integration tests for {@link TeamEditCommand}.
 */
public class TeamEditCommandTest {

    @Test
    public void constructor_nullOldTeam_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new TeamEditCommand(null, new Team("Reserve Team")));
    }

    @Test
    public void constructor_nullNewTeam_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new TeamEditCommand(new Team("First Team"), null));
    }

    @Test
    public void execute_validRename_success() {
        Model model = new ModelManager(getSampleAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        Team oldTeam = new Team("First Team");
        Team newTeam = new Team("Reserve Team");
        expectedModel.setTeam(oldTeam, newTeam);

        assertCommandSuccess(new TeamEditCommand(oldTeam, newTeam), model,
                String.format(TeamEditCommand.MESSAGE_SUCCESS, oldTeam, newTeam), expectedModel);
    }

    @Test
    public void execute_oldTeamNotFound_throwsCommandException() {
        Model model = new ModelManager(getSampleAddressBook(), new UserPrefs());

        assertCommandFailure(new TeamEditCommand(new Team("Ghost Team"), new Team("Reserve Team")),
                model, TeamEditCommand.MESSAGE_TEAM_NOT_FOUND);
    }

    @Test
    public void execute_newTeamAlreadyExists_throwsCommandException() {
        Model model = new ModelManager(getSampleAddressBook(), new UserPrefs());

        assertCommandFailure(new TeamEditCommand(new Team("First Team"), new Team("Second Team")),
                model, TeamEditCommand.MESSAGE_DUPLICATE_TEAM);
    }

    @Test
    public void equals() {
        TeamEditCommand editFirstToReserve = new TeamEditCommand(new Team("First Team"), new Team("Reserve Team"));
        TeamEditCommand editSecondToReserve = new TeamEditCommand(new Team("Second Team"), new Team("Reserve Team"));

        assertTrue(editFirstToReserve.equals(editFirstToReserve));
        assertTrue(editFirstToReserve.equals(
                new TeamEditCommand(new Team("first team"), new Team("Reserve Team"))));
        assertFalse(editFirstToReserve.equals(1));
        assertFalse(editFirstToReserve.equals(null));
        assertFalse(editFirstToReserve.equals(editSecondToReserve));
    }

    @Test
    public void toStringMethod() {
        TeamEditCommand command = new TeamEditCommand(new Team("First Team"), new Team("Reserve Team"));
        String expected = TeamEditCommand.class.getCanonicalName()
                + "{oldTeam=First Team, newTeam=Reserve Team}";
        assertEquals(expected, command.toString());
    }
}

