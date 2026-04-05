package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;

/**
 * Lists all teams in the catalog to the user.
 */
public class TeamListCommand extends Command {

    public static final String COMMAND_WORD = "teamlist";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all teams in the catalog.\n"
            + "Example: " + COMMAND_WORD;
    public static final String MESSAGE_EMPTY = "No teams found.";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        return AttributeCatalogFormatter.formatCatalogList(model.getTeamList(), "Teams:", MESSAGE_EMPTY);
    }
}
