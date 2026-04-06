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

/**
 * Contains integration tests for {@link StatusAddCommand}.
 */
public class StatusAddCommandTest {

    @Test
    // INVALID_CASE + EP_INVALID (null input)
    public void constructor_nullStatus_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new StatusAddCommand(null));
    }

    @Test
    // VALID_CASE + EP_VALID
    public void execute_newStatus_success() {
        Model model = new ModelManager(getSampleAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Status statusToAdd = new Status("Rehab");

        expectedModel.addStatus(statusToAdd);

        assertCommandSuccess(new StatusAddCommand(statusToAdd), model,
                String.format(StatusAddCommand.MESSAGE_SUCCESS, statusToAdd), expectedModel);
    }

    @Test
    // INVALID_CASE + EP_INVALID (duplicate catalog entry)
    public void execute_duplicateStatus_throwsCommandException() {
        Model model = new ModelManager(getSampleAddressBook(), new UserPrefs());
        Status duplicateStatus = new Status("Active");

        assertCommandFailure(new StatusAddCommand(duplicateStatus), model, StatusAddCommand.MESSAGE_DUPLICATE_STATUS);
    }

    @Test
    public void equals() {
        StatusAddCommand addActive = new StatusAddCommand(new Status("Active"));
        StatusAddCommand addRehab = new StatusAddCommand(new Status("Rehab"));

        assertTrue(addActive.equals(addActive));
        assertTrue(addActive.equals(new StatusAddCommand(new Status("active"))));
        assertFalse(addActive.equals(1));
        assertFalse(addActive.equals(null));
        assertFalse(addActive.equals(addRehab));
    }

    @Test
    public void toStringMethod() {
        Status status = new Status("Rehab");
        StatusAddCommand command = new StatusAddCommand(status);
        String expected = StatusAddCommand.class.getCanonicalName() + "{toAdd=" + status + "}";
        assertEquals(expected, command.toString());
    }
}
