package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.PersonMatchesFilterPredicate;

/**
 * Filters persons using structured role, attribute, and stat criteria.
 */
public class FilterCommand extends Command {
    public static final String COMMAND_WORD = "filter";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Filters persons by role, team, status, position, and player stats.\n"
            + "Parameters: [r/ROLE] [tm/TEAM] [st/STATUS] [pos/POSITION] [goals/[>|<|=]NUM] "
            + "[wins/[>|<|=]NUM] [losses/[>|<|=]NUM]\n"
            + "Examples:\n"
            + "  " + COMMAND_WORD + " r/player pos/Forward goals/>10\n"
            + "  " + COMMAND_WORD + " tm/First Team st/Active\n"
            + "  " + COMMAND_WORD + " wins/<3";
    public static final String MESSAGE_TEAM_NOT_FOUND = "Team %s does not exist in the address book!";
    public static final String MESSAGE_STATUS_NOT_FOUND = "Status %s does not exist in the address book!";
    public static final String MESSAGE_POSITION_NOT_FOUND = "Position %s does not exist in the address book!";

    private final PersonMatchesFilterPredicate predicate;

    /**
     * Creates a FilterCommand using the given predicate.
     */
    public FilterCommand(PersonMatchesFilterPredicate predicate) {
        requireNonNull(predicate);
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        validateAttributesExist(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW,
                model.getFilteredPersonList().size()));
    }

    private void validateAttributesExist(Model model) throws CommandException {
        if (predicate.getTeam().isPresent() && !model.hasTeam(predicate.getTeam().get())) {
            throw new CommandException(String.format(MESSAGE_TEAM_NOT_FOUND, predicate.getTeam().get()));
        }
        if (predicate.getStatus().isPresent() && !model.hasStatus(predicate.getStatus().get())) {
            throw new CommandException(String.format(MESSAGE_STATUS_NOT_FOUND, predicate.getStatus().get()));
        }
        if (predicate.getPosition().isPresent() && !model.hasPosition(predicate.getPosition().get())) {
            throw new CommandException(String.format(MESSAGE_POSITION_NOT_FOUND, predicate.getPosition().get()));
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof FilterCommand)) {
            return false;
        }

        FilterCommand otherCommand = (FilterCommand) other;
        return predicate.equals(otherCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
