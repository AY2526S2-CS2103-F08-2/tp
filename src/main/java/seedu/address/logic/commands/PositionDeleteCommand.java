package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Position;

/**
 * Deletes an existing position from the position catalog.
 */
public class PositionDeleteCommand extends Command {

    public static final String COMMAND_WORD = "positiondelete";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a position from the catalog.\n"
            + "Parameters: POSITION_NAME\n"
            + "Example: " + COMMAND_WORD + " Winger";

    public static final String MESSAGE_SUCCESS = "Deleted position: %1$s";
    public static final String MESSAGE_POSITION_NOT_FOUND = "The specified position does not exist in the catalog";
    public static final String MESSAGE_CANNOT_DELETE_DEFAULT_POSITION =
            "Cannot delete default position: Unassigned Position";
    public static final String MESSAGE_POSITION_IN_USE = "Cannot delete position currently assigned to persons";

    private final Position toDelete;

    /**
     * Creates a PositionDeleteCommand to delete the specified {@code Position}.
     */
    public PositionDeleteCommand(Position position) {
        requireNonNull(position);
        toDelete = position;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (toDelete.isDefaultUnassignedPosition()) {
            throw new CommandException(MESSAGE_CANNOT_DELETE_DEFAULT_POSITION);
        }

        if (!model.hasPosition(toDelete)) {
            throw new CommandException(MESSAGE_POSITION_NOT_FOUND);
        }

        if (!model.getPersonsMatching(person -> person.getPosition().equals(toDelete)).isEmpty()) {
            throw new CommandException(MESSAGE_POSITION_IN_USE);
        }

        model.deletePosition(toDelete);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toDelete));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof PositionDeleteCommand)) {
            return false;
        }

        PositionDeleteCommand otherPositionDeleteCommand = (PositionDeleteCommand) other;
        return toDelete.equals(otherPositionDeleteCommand.toDelete);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toDelete", toDelete)
                .toString();
    }
}
