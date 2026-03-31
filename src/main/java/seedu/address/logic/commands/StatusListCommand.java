package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

/**
 * Lists all statuses in the catalog to the user.
 */
public class StatusListCommand extends Command {

    public static final String COMMAND_WORD = "statuslist";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all statuses in the catalog.\n"
            + "Example: " + COMMAND_WORD;
    public static final String MESSAGE_EMPTY = "No statuses found.";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        return AttributeCatalogFormatter.formatCatalogList(model.getStatusList(), "Statuses:", MESSAGE_EMPTY);
    }
}
