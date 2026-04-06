package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PLAYER;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventPlayerList;
import seedu.address.model.person.Person;

/**
 * A command that allows user to mark attendance for events.
 */
public class AttendanceMarkCommand extends Command {
    public static final String COMMAND_WORD = "attendancemark";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Selects an event by the index number used in "
                                               + "the displayed event list, then supply players to mark attendance.\n"
                                               + "Parameters: INDEX (must be a positive integer) "
                                               + "[" + PREFIX_PLAYER + "PLAYER]...\n"
                                               + "Example: " + COMMAND_WORD + " 1 "
                                               + PREFIX_PLAYER + "Alex Yeoh ";

    public static final String MESSAGE_MARK_ATTENDANCE_SUCCESS = "Marked attendance for: %s";
    private static final String MESSAGE_PLAYER_NOT_FOUND = "This player is not part of the event!";

    private final Index index;
    private final List<String> playerNames;

    /**
     * Constructor that requires non-null for both index and playerNames.
     * @param index
     * @param playerNames
     */
    public AttendanceMarkCommand(Index index, List<String> playerNames) {
        requireNonNull(index);
        requireNonNull(playerNames);
        this.index = index;
        this.playerNames = playerNames;
    }

    /**
     * Executes the command.
     * @param model {@code Model} which the command should operate on.
     * @return
     * @throws CommandException
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Event> eventList = model.getEventList();

        if (index.getZeroBased() >= eventList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        Event eventToMark = eventList.get(index.getZeroBased());

        EventPlayerList updatedAttendees =
                new EventPlayerList(new HashSet<>(eventToMark.getAttendedPlayerList().asUnmodifiableObservableList()));

        for (String s : playerNames) {
            Optional<Person> personToMark = model.getAddressBook().getPersonList().stream()
                    .filter(p -> p.getName().fullName.equalsIgnoreCase(s))
                    .findFirst();

            if (personToMark.isPresent()) {
                Person p = personToMark.get();
                if (eventToMark.getEventPlayerList().contains(p)) {
                    updatedAttendees.add(p);
                } else {
                    throw new CommandException(String.format(MESSAGE_PLAYER_NOT_FOUND, s));
                }
            } else {
                throw new CommandException("Player not found in Address Book: " + s);
            }
        }

        Event updatedEvent = Event.createEventWithAttendees(eventToMark.getEventName(),
                eventToMark.getEventDate(),
                eventToMark.getEventType(),
                eventToMark.getEventPlayerList(),
                updatedAttendees);

        model.setEvent(eventToMark, updatedEvent);

        return new CommandResult(String.format(MESSAGE_MARK_ATTENDANCE_SUCCESS,
                String.join(", ", playerNames)));
    }
}
