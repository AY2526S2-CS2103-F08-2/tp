package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.model.util.SampleDataUtil.getSampleAddressBook;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Status;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests for {@link StatusEditCommand}.
 */
public class StatusEditCommandTest {

    @Test
    public void constructor_nullOldStatus_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new StatusEditCommand(null, new Status("Rehab")));
    }

    @Test
    public void constructor_nullNewStatus_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new StatusEditCommand(new Status("Active"), null));
    }

    @Test
    public void execute_validRename_success() {
        Model model = new ModelManager(getSampleAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        Status oldStatus = new Status("Active");
        Status newStatus = new Status("Rehab");
        expectedModel.setStatus(oldStatus, newStatus);

        assertCommandSuccess(new StatusEditCommand(oldStatus, newStatus), model,
                String.format(StatusEditCommand.MESSAGE_SUCCESS, oldStatus, newStatus), expectedModel);
    }

    @Test
    public void execute_oldStatusNotFound_throwsCommandException() {
        Model model = new ModelManager(getSampleAddressBook(), new UserPrefs());

        assertCommandFailure(new StatusEditCommand(new Status("Ghost Status"), new Status("Rehab")),
                model, StatusEditCommand.MESSAGE_STATUS_NOT_FOUND);
    }

    @Test
    public void execute_newStatusAlreadyExists_throwsCommandException() {
        Model model = new ModelManager(getSampleAddressBook(), new UserPrefs());

        assertCommandFailure(new StatusEditCommand(new Status("Active"), new Status("Unavailable")),
                model, StatusEditCommand.MESSAGE_DUPLICATE_STATUS);
    }

    @Test
    public void execute_defaultStatus_throwsCommandException() {
        Model model = new ModelManager(getSampleAddressBook(), new UserPrefs());

        assertCommandFailure(new StatusEditCommand(new Status(Status.DEFAULT_UNKNOWN_STATUS), new Status("Rehab")),
                model, StatusEditCommand.MESSAGE_CANNOT_EDIT_DEFAULT_STATUS);
    }

    @Test
    public void execute_validRename_updatesPersonsWithOldStatus() throws CommandException {
        Model model = new ModelManager(getSampleAddressBook(), new UserPrefs());
        model.addPerson(new PersonBuilder().withStatus("Active").build());

        Status oldStatus = new Status("Active");
        Status newStatus = new Status("Rehab");
        new StatusEditCommand(oldStatus, newStatus).execute(model);

        assertTrue(model.getPersonsMatching(person -> person.getStatus().equals(oldStatus)).isEmpty());
        assertFalse(model.getPersonsMatching(person -> person.getStatus().equals(newStatus)).isEmpty());
    }

    @Test
    public void equals() {
        StatusEditCommand editActiveToRehab = new StatusEditCommand(new Status("Active"), new Status("Rehab"));
        StatusEditCommand editUnknownToRehab = new StatusEditCommand(new Status("Unknown"), new Status("Rehab"));

        assertTrue(editActiveToRehab.equals(editActiveToRehab));
        assertTrue(editActiveToRehab.equals(new StatusEditCommand(new Status("active"), new Status("Rehab"))));
        assertFalse(editActiveToRehab.equals(1));
        assertFalse(editActiveToRehab.equals(null));
        assertFalse(editActiveToRehab.equals(editUnknownToRehab));
    }

    @Test
    public void toStringMethod() {
        StatusEditCommand command = new StatusEditCommand(new Status("Active"), new Status("Rehab"));
        String expected = StatusEditCommand.class.getCanonicalName()
                + "{oldStatus=Active, newStatus=Rehab}";
        assertEquals(expected, command.toString());
    }
}
