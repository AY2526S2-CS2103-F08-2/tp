package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Team;

/**
 * Renames an existing team in the team catalog.
 */
public class TeamEditCommand extends Command {

    public static final String COMMAND_WORD = "teamedit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Renames an existing team in the catalog.\n"
            + "Parameters: old/OLD_TEAM_NAME new/NEW_TEAM_NAME\n"
            + "Example: " + COMMAND_WORD + " old/First Team new/Reserve Team";

    public static final String MESSAGE_SUCCESS = "Renamed team: %1$s -> %2$s";
    public static final String MESSAGE_TEAM_NOT_FOUND = "The specified team does not exist in the catalog";
    public static final String MESSAGE_DUPLICATE_TEAM = "This team already exists in the catalog";
    public static final String MESSAGE_CANNOT_EDIT_DEFAULT_TEAM = "Cannot edit default team: Unassigned Team";

    private final Team oldTeam;
    private final Team newTeam;

    /**
     * Creates a TeamEditCommand to rename {@code oldTeam} to {@code newTeam}.
     */
    public TeamEditCommand(Team oldTeam, Team newTeam) {
        requireNonNull(oldTeam);
        requireNonNull(newTeam);
        this.oldTeam = oldTeam;
        this.newTeam = newTeam;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (oldTeam.isDefaultUnassignedTeam()) {
            throw new CommandException(MESSAGE_CANNOT_EDIT_DEFAULT_TEAM);
        }

        if (!model.hasTeam(oldTeam)) {
            throw new CommandException(MESSAGE_TEAM_NOT_FOUND);
        }

        if (!oldTeam.equals(newTeam) && model.hasTeam(newTeam)) {
            throw new CommandException(MESSAGE_DUPLICATE_TEAM);
        }

        model.setTeam(oldTeam, newTeam);
        return new CommandResult(String.format(MESSAGE_SUCCESS, oldTeam, newTeam));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof TeamEditCommand)) {
            return false;
        }

        TeamEditCommand otherTeamEditCommand = (TeamEditCommand) other;
        return oldTeam.equals(otherTeamEditCommand.oldTeam)
                && newTeam.equals(otherTeamEditCommand.newTeam);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("oldTeam", oldTeam)
                .add("newTeam", newTeam)
                .toString();
    }
}

