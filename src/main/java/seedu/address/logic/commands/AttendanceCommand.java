package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.EventType;
import seedu.address.model.person.Role;

/**
 * Provides an overview of all players' training attendance.
 */
public class AttendanceCommand extends Command {

    public static final String COMMAND_WORD = "attendance";

    public static final String MESSAGE_SUCCESS = "=== Player Attendance Rates ===\n";
    public static final String MESSAGE_NO_TRAINING = "No trainings detected!";
    public static final String MESSAGE_NO_PLAYERS = "No players detected!";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.getEventList().filtered(e -> e.getEventType() == EventType.TRAINING).isEmpty()) {
            throw new CommandException(MESSAGE_NO_TRAINING);
        } else if (model.getAddressBook().getPersonList().filtered(p -> p.getRole() == Role.PLAYER).isEmpty()) {
            throw new CommandException(MESSAGE_NO_PLAYERS);
        }
        String result = model.getAttendanceReport();
        return new CommandResult(MESSAGE_SUCCESS + result);
    }
}
