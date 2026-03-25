package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Position;

/**
 * Adds a position to the position catalog.
 */
public class PositionAddCommand extends Command {

    public static final String COMMAND_WORD = "positionadd";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a position to the catalog.\n"
            + "Parameters: POSITION_NAME\n"
            + "Example: " + COMMAND_WORD + " Winger";

    public static final String MESSAGE_SUCCESS = "New position added: %1$s";
    public static final String MESSAGE_DUPLICATE_POSITION = "This position already exists in the catalog";

    private final Position toAdd;

    /**
     * Creates a PositionAddCommand to add the specified {@code Position}.
     */
    public PositionAddCommand(Position position) {
        requireNonNull(position);
        toAdd = position;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasPosition(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_POSITION);
        }

        model.addPosition(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof PositionAddCommand)) {
            return false;
        }

        PositionAddCommand otherPositionAddCommand = (PositionAddCommand) other;
        return toAdd.equals(otherPositionAddCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}

