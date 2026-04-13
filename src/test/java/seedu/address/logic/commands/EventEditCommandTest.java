package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_MATCH;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEvents.getTypicalAddressBookWithEvents;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_EVENT;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EventEditCommand.EditEventDescriptor;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventPlayerList;
import seedu.address.testutil.EditEventDescriptorBuilder;
import seedu.address.testutil.EventBuilder;
import seedu.address.testutil.TypicalPersons;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EventEditCommand.
 */
public class EventEditCommandTest {

    private Model model = new ModelManager(getTypicalAddressBookWithEvents(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Event editedEvent = new EventBuilder().withEventType("MATCH").build();
        EditEventDescriptor descriptor = new EditEventDescriptorBuilder(editedEvent).build();
        EventEditCommand eventEditCommand = new EventEditCommand(INDEX_FIRST_EVENT, descriptor);

        String expectedMessage = String.format(EventEditCommand.MESSAGE_EDIT_EVENT_SUCCESS,
                Messages.format(editedEvent));

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setEvent(model.getEventList().get(0), editedEvent);

        assertCommandSuccess(eventEditCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EventEditCommand eventEditCommand = new EventEditCommand(INDEX_FIRST_EVENT, new EditEventDescriptor());

        String expectedMessage = EventEditCommand.MESSAGE_NO_FIELD_WAS_CHANGED;

        assertCommandFailure(eventEditCommand, model, expectedMessage);
    }

    @Test
    public void execute_editMetadata_preservesAttendance() throws CommandException {
        AddressBook addressBook = new AddressBook();
        addressBook.addPerson(TypicalPersons.PLAYER_AMY);

        Event baseEvent = new EventBuilder()
                .withEventName("Warm Up")
                .withEventType("TRAINING")
                .withDate("2026-05-15 1600")
                .withPlayers(java.util.Set.of(TypicalPersons.PLAYER_AMY))
                .build();
        Event eventWithAttendance = Event.createEventWithAttendees(baseEvent.getEventName(), baseEvent.getEventDate(),
                baseEvent.getEventType(), baseEvent.getEventPlayerList(),
                new EventPlayerList(java.util.Set.of(TypicalPersons.PLAYER_AMY)));
        addressBook.addEvent(eventWithAttendance);

        ModelManager model = new ModelManager(addressBook, new UserPrefs());
        EditEventDescriptor descriptor = new EditEventDescriptorBuilder().withEventName("WarmUpUpdated").build();

        new EventEditCommand(INDEX_FIRST_EVENT, descriptor).execute(model);

        Event editedEvent = model.getEventList().get(0);
        assertTrue(editedEvent.getAttendedPlayerList().contains(TypicalPersons.PLAYER_AMY));
        assertEquals(TypicalPersons.PLAYER_AMY.getName().toString() + " : 100.0%\n", model.getAttendanceReport());
    }

    @Test
    public void equals() {
        final EventEditCommand standardCommand = new EventEditCommand(INDEX_FIRST_EVENT, DESC_MATCH);

        // same values -> returns true
        EditEventDescriptor copyDescriptor = new EditEventDescriptor(DESC_MATCH);
        EventEditCommand commandWithSameValues = new EventEditCommand(INDEX_FIRST_EVENT, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EventEditCommand(INDEX_SECOND_EVENT, DESC_MATCH)));
    }

    @Test
    public void toStringMethod() {
        Index index = Index.fromOneBased(1);
        EditEventDescriptor editEventDescriptor = new EditEventDescriptor();
        EventEditCommand eventEditCommand = new EventEditCommand(index, editEventDescriptor);
        String expected = EventEditCommand.class.getCanonicalName() + "{index=" + index + ", editEventDescriptor="
                + editEventDescriptor + "}";
        assertEquals(expected, eventEditCommand.toString());
    }
}
