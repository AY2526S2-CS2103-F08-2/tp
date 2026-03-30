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
import seedu.address.model.person.Status;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests for {@link StatusDeleteCommand}.
 */
public class StatusDeleteCommandTest {

    @Test
    public void constructor_nullStatus_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new StatusDeleteCommand(null));
    }

    @Test
    public void execute_existingStatus_success() {
        Model model = new ModelManager(getSampleAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Status statusToDelete = new Status("Unavailable");

        expectedModel.deleteStatus(statusToDelete);

        assertCommandSuccess(new StatusDeleteCommand(statusToDelete), model,
                String.format(StatusDeleteCommand.MESSAGE_SUCCESS, statusToDelete), expectedModel);
    }

    @Test
    public void execute_missingStatus_throwsCommandException() {
        Model model = new ModelManager(getSampleAddressBook(), new UserPrefs());

        assertCommandFailure(new StatusDeleteCommand(new Status("Ghost Status")),
                model, StatusDeleteCommand.MESSAGE_STATUS_NOT_FOUND);
    }

    @Test
    public void execute_defaultStatus_throwsCommandException() {
        Model model = new ModelManager(getSampleAddressBook(), new UserPrefs());

        assertCommandFailure(new StatusDeleteCommand(new Status(Status.DEFAULT_UNKNOWN_STATUS)),
                model, StatusDeleteCommand.MESSAGE_CANNOT_DELETE_DEFAULT_STATUS);
    }

    @Test
    public void execute_statusInUse_throwsCommandException() {
        Model model = new ModelManager(getSampleAddressBook(), new UserPrefs());
        Status inUseStatus = new Status("Rehab");
        model.addStatus(inUseStatus);
        model.addPerson(new PersonBuilder().withStatus("Rehab").build());

        assertCommandFailure(new StatusDeleteCommand(inUseStatus), model, StatusDeleteCommand.MESSAGE_STATUS_IN_USE);
    }

    @Test
    public void equals() {
        StatusDeleteCommand deleteActive = new StatusDeleteCommand(new Status("Active"));
        StatusDeleteCommand deleteUnavailable = new StatusDeleteCommand(new Status("Unavailable"));

        assertTrue(deleteActive.equals(deleteActive));
        assertTrue(deleteActive.equals(new StatusDeleteCommand(new Status("active"))));
        assertFalse(deleteActive.equals(1));
        assertFalse(deleteActive.equals(null));
        assertFalse(deleteActive.equals(deleteUnavailable));
    }

    @Test
    public void toStringMethod() {
        Status status = new Status("Unavailable");
        StatusDeleteCommand command = new StatusDeleteCommand(status);
        String expected = StatusDeleteCommand.class.getCanonicalName() + "{toDelete=" + status + "}";
        assertEquals(expected, command.toString());
    }
}
