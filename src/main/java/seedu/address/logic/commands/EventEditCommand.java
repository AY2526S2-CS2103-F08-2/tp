package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.MatchCommand.MESSAGE_NOT_A_PLAYER;
import static seedu.address.logic.commands.MatchCommand.MESSAGE_PERSON_DOES_NOT_EXIST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PLAYER;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Date;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventName;
import seedu.address.model.event.EventPlayerList;
import seedu.address.model.event.EventType;
import seedu.address.model.person.Person;
import seedu.address.model.person.Role;

/**
 * Edits the details of an existing event in the address book.
 */
public class EventEditCommand extends Command {

    public static final String COMMAND_WORD = "editevent";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the event identified "
            + "by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "EVENT_NAME] "
            + "[" + PREFIX_EVENT_TYPE + "EVENT_TYPE] "
            + "[" + PREFIX_DATE + "DATE] "
            + "[" + PREFIX_PLAYER + "PLAYER]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_NAME + "Manchester United "
            + PREFIX_EVENT_TYPE + "MATCH "
            + PREFIX_DATE + "2025-03-24 1600";

    public static final String MESSAGE_EDIT_EVENT_SUCCESS = "Edited Event: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_EVENT = "This event already exists in the address book.";
    public static final String MESSAGE_NO_FIELD_WAS_CHANGED = "No fields were changed from the existing event";

    private final Index index;
    private final EditEventDescriptor editEventDescriptor;

    /**
     * @param index of the event in the filtered event list to edit
     * @param editEventDescriptor details to edit the event with
     */
    public EventEditCommand(Index index, EditEventDescriptor editEventDescriptor) {
        requireNonNull(index);
        requireNonNull(editEventDescriptor);

        this.index = index;
        this.editEventDescriptor = new EditEventDescriptor(editEventDescriptor);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Event> eventList = model.getEventList();

        if (index.getZeroBased() >= eventList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        Event eventToEdit = eventList.get(index.getZeroBased());
        Event editedEvent = createEditedEvent(eventToEdit, editEventDescriptor, model);

        if (eventToEdit.equals(editedEvent)) {
            throw new CommandException(String.format(MESSAGE_NO_FIELD_WAS_CHANGED));
        }

        if (model.hasEvent(editedEvent)) {
            throw new CommandException(MESSAGE_DUPLICATE_EVENT);
        }

        model.setEvent(eventToEdit, editedEvent);
        return new CommandResult(String.format(MESSAGE_EDIT_EVENT_SUCCESS, Messages.format(editedEvent)));
    }

    /**
     * Creates and returns a {@code Event} with the details of {@code eventToEdit}
     * edited with {@code editEventDescriptor}.
     */
    public static Event createEditedEvent(Event eventToEdit, EditEventDescriptor editEventDescriptor, Model model)
            throws CommandException {
        assert eventToEdit != null;
        requireNonNull(model);

        EventName updatedName = editEventDescriptor.getEventName().orElse(eventToEdit.getEventName());
        EventType updatedEventType = editEventDescriptor.getEventType().orElse(eventToEdit.getEventType());
        Date updatedEventDate = editEventDescriptor.getEventDate().orElse(eventToEdit.getEventDate());
        Set<String> updatedPlayerNames = editEventDescriptor.getEventPlayerNames()
                .orElse(eventToEdit.getEventPlayerList().getPlayerNames());

        Set<Person> playerList = new HashSet<>();

        for (String playerName : updatedPlayerNames) {
            Person person = model.getAddressBook().getPersonList().stream()
                    .filter(p -> p.getName().toString().equals(playerName.trim()))
                    .findFirst()
                    .orElseThrow(() -> new CommandException(String.format(MESSAGE_PERSON_DOES_NOT_EXIST, playerName)));

            if (person.getRole() != Role.PLAYER) {
                throw new CommandException(String.format(MESSAGE_NOT_A_PLAYER, playerName));
            }

            playerList.add(person);
        }

        EventPlayerList eventPlayerList = new EventPlayerList(playerList);

        return Event.createEvent(updatedName, updatedEventDate, updatedEventType, eventPlayerList);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EventEditCommand)) {
            return false;
        }

        EventEditCommand otherEventEditCommand = (EventEditCommand) other;
        return index.equals(otherEventEditCommand.index)
                && editEventDescriptor.equals(otherEventEditCommand.editEventDescriptor);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("editEventDescriptor", editEventDescriptor)
                .toString();
    }

    /**
     * Stores the details to edit the event with. Each non-empty field value will replace the
     * corresponding field value of the event.
     */
    public static class EditEventDescriptor {
        private EventName eventName;
        private Date eventDate;
        private EventType eventType;
        private Set<String> eventPlayerNames;

        public EditEventDescriptor() {}

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditEventDescriptor(EditEventDescriptor toCopy) {
            setEventName(toCopy.eventName);
            setEventType(toCopy.eventType);
            setEventDate(toCopy.eventDate);
            setEventPlayerNames(toCopy.eventPlayerNames);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(eventName, eventDate, eventType, eventPlayerNames);
        }

        public void setEventName(EventName eventName) {
            this.eventName = eventName;
        }

        public Optional<EventName> getEventName() {
            return Optional.ofNullable(eventName);
        }

        public void setEventType(EventType eventType) {
            this.eventType = eventType;
        }

        public Optional<EventType> getEventType() {
            return Optional.ofNullable(eventType);
        }

        public void setEventDate(Date eventDate) {
            this.eventDate = eventDate;
        }

        public Optional<Date> getEventDate() {
            return Optional.ofNullable(eventDate);
        }

        /**
         * Sets {@code eventPlayerNames} to this object's {@code eventPlayerNames}.
         * A defensive copy of {@code eventPlayerNames} is used internally.
         */
        public void setEventPlayerNames(Set<String> eventPlayerNames) {
            this.eventPlayerNames = (eventPlayerNames != null) ? new HashSet<>(eventPlayerNames) : null;
        }

        /**
         * Returns an unmodifiable eventPlayerNames set, which throws {@code UnsupportedOperationException}
         * if modification is attempted.
         * Returns {@code Optional#empty()} if {@code eventPlayerNames} is null.
         */
        public Optional<Set<String>> getEventPlayerNames() {
            return (eventPlayerNames != null) ? Optional.of(
                    Collections.unmodifiableSet(eventPlayerNames)) : Optional.empty();
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditEventDescriptor)) {
                return false;
            }

            EditEventDescriptor otherEditEventDescriptor = (EditEventDescriptor) other;
            return Objects.equals(eventName, otherEditEventDescriptor.eventName)
                    && Objects.equals(eventType, otherEditEventDescriptor.eventType)
                    && Objects.equals(eventDate, otherEditEventDescriptor.eventDate)
                    && Objects.equals(eventPlayerNames, otherEditEventDescriptor.eventPlayerNames);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("Event Name", eventName)
                    .add("Event Type", eventType)
                    .add("Event Date", eventDate)
                    .add("Event Player Names", eventPlayerNames)
                    .toString();
        }
    }
}
