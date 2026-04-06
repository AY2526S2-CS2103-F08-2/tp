package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
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

    private final PersonMatchesFilterPredicate predicate;

    /**
     * Creates a FilterCommand using the given predicate.
     */
    public FilterCommand(PersonMatchesFilterPredicate predicate) {
        requireNonNull(predicate);
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW,
                model.getFilteredPersonList().size()));
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
