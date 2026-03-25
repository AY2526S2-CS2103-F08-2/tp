package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;
import seedu.address.model.Model;
import seedu.address.model.person.Status;

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
        ObservableList<Status> statuses = model.getStatusList();

        if (statuses.isEmpty()) {
            return new CommandResult(MESSAGE_EMPTY);
        }

        StringBuilder builder = new StringBuilder("Statuses:");
        for (int i = 0; i < statuses.size(); i++) {
            builder.append(System.lineSeparator())
                    .append(i + 1)
                    .append(". ")
                    .append(statuses.get(i));
        }
        return new CommandResult(builder.toString());
    }
}

