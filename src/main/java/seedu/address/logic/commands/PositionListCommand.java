package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

/**
 * Lists all positions in the catalog to the user.
 */
public class PositionListCommand extends Command {

    public static final String COMMAND_WORD = "positionlist";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all positions in the catalog.\n"
            + "Example: " + COMMAND_WORD;
    public static final String MESSAGE_EMPTY = "No positions found.";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        return AttributeCatalogFormatter.formatCatalogList(model.getPositionList(), "Positions:", MESSAGE_EMPTY);
    }
}
