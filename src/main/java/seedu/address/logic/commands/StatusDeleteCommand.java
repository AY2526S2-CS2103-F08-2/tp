package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Status;

/**
 * Deletes an existing status from the status catalog.
 */
public class StatusDeleteCommand extends Command {

    public static final String COMMAND_WORD = "statusdelete";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a status from the catalog.\n"
            + "Parameters: STATUS_NAME\n"
            + "Example: " + COMMAND_WORD + " Rehab";

    public static final String MESSAGE_SUCCESS = "Deleted status: %1$s";
    public static final String MESSAGE_STATUS_NOT_FOUND = "The specified status does not exist in the catalog";
    public static final String MESSAGE_CANNOT_DELETE_DEFAULT_STATUS = "Cannot delete default status: Unknown";

    private final Status toDelete;

    /**
     * Creates a StatusDeleteCommand to delete the specified {@code Status}.
     */
    public StatusDeleteCommand(Status status) {
        requireNonNull(status);
        toDelete = status;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (toDelete.isDefaultUnknownStatus()) {
            throw new CommandException(MESSAGE_CANNOT_DELETE_DEFAULT_STATUS);
        }

        if (!model.hasStatus(toDelete)) {
            throw new CommandException(MESSAGE_STATUS_NOT_FOUND);
        }

        model.deleteStatus(toDelete);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toDelete));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof StatusDeleteCommand)) {
            return false;
        }

        StatusDeleteCommand otherStatusDeleteCommand = (StatusDeleteCommand) other;
        return toDelete.equals(otherStatusDeleteCommand.toDelete);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toDelete", toDelete)
                .toString();
    }
}

