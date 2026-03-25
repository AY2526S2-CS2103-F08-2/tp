package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Team;

/**
 * Adds a team to the team catalog.
 */
public class TeamAddCommand extends Command {

    public static final String COMMAND_WORD = "teamadd";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a team to the catalog.\n"
            + "Parameters: TEAM_NAME\n"
            + "Example: " + COMMAND_WORD + " First Team";

    public static final String MESSAGE_SUCCESS = "New team added: %1$s";
    public static final String MESSAGE_DUPLICATE_TEAM = "This team already exists in the catalog";

    private final Team toAdd;

    /**
     * Creates a TeamAddCommand to add the specified {@code Team}.
     */
    public TeamAddCommand(Team team) {
        requireNonNull(team);
        toAdd = team;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasTeam(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_TEAM);
        }

        model.addTeam(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof TeamAddCommand)) {
            return false;
        }

        TeamAddCommand otherTeamAddCommand = (TeamAddCommand) other;
        return toAdd.equals(otherTeamAddCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}


