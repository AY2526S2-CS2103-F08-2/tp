package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEvents.getTypicalAddressBookWithEvents;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_EVENT;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.Event;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code EventDeleteCommand}.
 */
public class EventDeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBookWithEvents(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Event eventToDelete = model.getEventList().get(INDEX_FIRST_EVENT.getZeroBased());
        EventDeleteCommand eventDeleteCommand = new EventDeleteCommand(INDEX_FIRST_EVENT);

        String expectedMessage = String.format(EventDeleteCommand.MESSAGE_DELETE_EVENT_SUCCESS,
                Messages.format(eventToDelete));

        ModelManager expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.deleteEvent(eventToDelete);

        assertCommandSuccess(eventDeleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getEventList().size() + 1);
        EventDeleteCommand eventDeleteCommand = new EventDeleteCommand(outOfBoundIndex);

        assertCommandFailure(eventDeleteCommand, model, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        EventDeleteCommand deleteFirstCommand = new EventDeleteCommand(INDEX_FIRST_EVENT);
        EventDeleteCommand deleteSecondCommand = new EventDeleteCommand(INDEX_SECOND_EVENT);

        // same object -> returns true
        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));

        // same values -> returns true
        EventDeleteCommand deleteFirstCommandCopy = new EventDeleteCommand(INDEX_FIRST_EVENT);
        assertTrue(deleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstCommand.equals(null));

        // different event -> returns false
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
    }

    @Test
    public void toStringMethod() {
        EventDeleteCommand deleteCommand = new EventDeleteCommand(INDEX_FIRST_EVENT);
        String expected = EventDeleteCommand.class.getCanonicalName() + "{targetIndex="
                + INDEX_FIRST_EVENT + "}";
        assertEquals(expected, deleteCommand.toString());
    }
}
