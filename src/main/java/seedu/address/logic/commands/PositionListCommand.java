package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;
import seedu.address.model.Model;
import seedu.address.model.person.Position;

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
        ObservableList<Position> positions = model.getPositionList();

        if (positions.isEmpty()) {
            return new CommandResult(MESSAGE_EMPTY);
        }

        StringBuilder builder = new StringBuilder("Positions:");
        for (int i = 0; i < positions.size(); i++) {
            builder.append(System.lineSeparator())
                    .append(i + 1)
                    .append(". ")
                    .append(positions.get(i));
        }
        return new CommandResult(builder.toString());
    }
}

