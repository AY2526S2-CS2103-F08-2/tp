package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;
import seedu.address.model.Model;
import seedu.address.model.person.Team;

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
        ObservableList<Team> teams = model.getAddressBook().getTeamList();

        if (teams.isEmpty()) {
            return new CommandResult(MESSAGE_EMPTY);
        }

        StringBuilder builder = new StringBuilder("Teams:");
        for (int i = 0; i < teams.size(); i++) {
            builder.append(System.lineSeparator())
                    .append(i + 1)
                    .append(". ")
                    .append(teams.get(i));
        }
        return new CommandResult(builder.toString());
    }
}
