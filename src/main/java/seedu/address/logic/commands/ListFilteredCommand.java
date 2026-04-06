package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.Model;
import seedu.address.model.person.PersonMatchesListFiltersPredicate;

/**
 * Lists persons that match the given role and attribute filters.
 */
public class ListFilteredCommand extends Command {

    public static final String MESSAGE_SUCCESS = "Listed %s";

    private final PersonMatchesListFiltersPredicate predicate;
    private final String description;

    /**
     * Creates a ListFilteredCommand to list persons matching the given filters.
     */
    public ListFilteredCommand(PersonMatchesListFiltersPredicate predicate, String description) {
        requireNonNull(predicate);
        requireNonNull(description);
        this.predicate = predicate;
        this.description = description;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(String.format(MESSAGE_SUCCESS, description));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof ListFilteredCommand)) {
            return false;
        }

        ListFilteredCommand otherCommand = (ListFilteredCommand) other;
        return predicate.equals(otherCommand.predicate)
                && description.equals(otherCommand.description);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .add("description", description)
                .toString();
    }
}
