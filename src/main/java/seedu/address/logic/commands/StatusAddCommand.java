package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Status;

/**
 * Adds a status to the status catalog.
 */
public class StatusAddCommand extends Command {

    public static final String COMMAND_WORD = "statusadd";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a status to the catalog.\n"
            + "Parameters: STATUS_NAME\n"
            + "Example: " + COMMAND_WORD + " Fit";

    public static final String MESSAGE_SUCCESS = "New status added: %1$s";
    public static final String MESSAGE_DUPLICATE_STATUS = "This status already exists in the catalog";

    private final Status toAdd;

    /**
     * Creates a StatusAddCommand to add the specified {@code Status}.
     */
    public StatusAddCommand(Status status) {
        requireNonNull(status);
        toAdd = status;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasStatus(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_STATUS);
        }

        model.addStatus(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof StatusAddCommand)) {
            return false;
        }

        StatusAddCommand otherStatusAddCommand = (StatusAddCommand) other;
        return toAdd.equals(otherStatusAddCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}

