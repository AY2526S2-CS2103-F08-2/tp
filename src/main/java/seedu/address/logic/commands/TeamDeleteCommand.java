package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Team;

/**
 * Deletes an existing team from the team catalog.
 */
public class TeamDeleteCommand extends Command {

    public static final String COMMAND_WORD = "teamdelete";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a team from the catalog.\n"
            + "Parameters: TEAM_NAME\n"
            + "Example: " + COMMAND_WORD + " Reserve Team";

    public static final String MESSAGE_SUCCESS = "Deleted team: %1$s";
    public static final String MESSAGE_TEAM_NOT_FOUND = "The specified team does not exist in the catalog";

    private final Team toDelete;

    /**
     * Creates a TeamDeleteCommand to delete the specified {@code Team}.
     */
    public TeamDeleteCommand(Team team) {
        requireNonNull(team);
        toDelete = team;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (!model.hasTeam(toDelete)) {
            throw new CommandException(MESSAGE_TEAM_NOT_FOUND);
        }

        model.deleteTeam(toDelete);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toDelete));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof TeamDeleteCommand)) {
            return false;
        }

        TeamDeleteCommand otherTeamDeleteCommand = (TeamDeleteCommand) other;
        return toDelete.equals(otherTeamDeleteCommand.toDelete);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toDelete", toDelete)
                .toString();
    }
}

