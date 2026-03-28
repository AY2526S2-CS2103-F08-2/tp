package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Status;

/**
 * Renames an existing status in the status catalog.
 */
public class StatusEditCommand extends Command {

    public static final String COMMAND_WORD = "statusedit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Renames an existing status in the catalog.\n"
            + "Parameters: old/OLD_STATUS_NAME new/NEW_STATUS_NAME\n"
            + "Example: " + COMMAND_WORD + " old/Active new/Rehab";

    public static final String MESSAGE_SUCCESS = "Renamed status: %1$s -> %2$s";
    public static final String MESSAGE_STATUS_NOT_FOUND = "The specified status does not exist in the catalog";
    public static final String MESSAGE_DUPLICATE_STATUS = "This status already exists in the catalog";
    public static final String MESSAGE_CANNOT_EDIT_DEFAULT_STATUS = "Cannot edit default status: Unknown";

    private final Status oldStatus;
    private final Status newStatus;

    /**
     * Creates a StatusEditCommand to rename {@code oldStatus} to {@code newStatus}.
     */
    public StatusEditCommand(Status oldStatus, Status newStatus) {
        requireNonNull(oldStatus);
        requireNonNull(newStatus);
        this.oldStatus = oldStatus;
        this.newStatus = newStatus;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (oldStatus.isDefaultUnknownStatus()) {
            throw new CommandException(MESSAGE_CANNOT_EDIT_DEFAULT_STATUS);
        }

        if (!model.hasStatus(oldStatus)) {
            throw new CommandException(MESSAGE_STATUS_NOT_FOUND);
        }

        if (!oldStatus.equals(newStatus) && model.hasStatus(newStatus)) {
            throw new CommandException(MESSAGE_DUPLICATE_STATUS);
        }

        model.setStatus(oldStatus, newStatus);
        return new CommandResult(String.format(MESSAGE_SUCCESS, oldStatus, newStatus));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof StatusEditCommand)) {
            return false;
        }

        StatusEditCommand otherStatusEditCommand = (StatusEditCommand) other;
        return oldStatus.equals(otherStatusEditCommand.oldStatus)
                && newStatus.equals(otherStatusEditCommand.newStatus);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("oldStatus", oldStatus)
                .add("newStatus", newStatus)
                .toString();
    }
}

