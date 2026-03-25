package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Position;

/**
 * Renames an existing position in the position catalog.
 */
public class PositionEditCommand extends Command {

    public static final String COMMAND_WORD = "positionedit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Renames an existing position in the catalog.\n"
            + "Parameters: old/OLD_POSITION_NAME new/NEW_POSITION_NAME\n"
            + "Example: " + COMMAND_WORD + " old/Defender new/Center Back";

    public static final String MESSAGE_SUCCESS = "Renamed position: %1$s -> %2$s";
    public static final String MESSAGE_POSITION_NOT_FOUND = "The specified position does not exist in the catalog";
    public static final String MESSAGE_DUPLICATE_POSITION = "This position already exists in the catalog";

    private final Position oldPosition;
    private final Position newPosition;

    /**
     * Creates a PositionEditCommand to rename {@code oldPosition} to {@code newPosition}.
     */
    public PositionEditCommand(Position oldPosition, Position newPosition) {
        requireNonNull(oldPosition);
        requireNonNull(newPosition);
        this.oldPosition = oldPosition;
        this.newPosition = newPosition;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasPosition(oldPosition)) {
            throw new CommandException(MESSAGE_POSITION_NOT_FOUND);
        }

        if (!oldPosition.equals(newPosition) && model.hasPosition(newPosition)) {
            throw new CommandException(MESSAGE_DUPLICATE_POSITION);
        }

        model.setPosition(oldPosition, newPosition);
        return new CommandResult(String.format(MESSAGE_SUCCESS, oldPosition, newPosition));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof PositionEditCommand)) {
            return false;
        }

        PositionEditCommand otherPositionEditCommand = (PositionEditCommand) other;
        return oldPosition.equals(otherPositionEditCommand.oldPosition)
                && newPosition.equals(otherPositionEditCommand.newPosition);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("oldPosition", oldPosition)
                .add("newPosition", newPosition)
                .toString();
    }
}

