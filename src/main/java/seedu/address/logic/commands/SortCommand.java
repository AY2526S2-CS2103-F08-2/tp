package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonSortAttribute;

/**
 * Sorts persons in the current UI list by a supported attribute.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sorts persons by a supported attribute.\n"
            + "Parameters: [players | staff] by/[name | email] [desc]\n"
            + "Example: " + COMMAND_WORD + " by/name desc";

    public static final String MESSAGE_SUCCESS = "Sorted %s by %s in %s order";
    public static final String ORDER_ASCENDING = "ascending";
    public static final String ORDER_DESCENDING = "descending";

    private final Predicate<Person> predicate;
    private final Comparator<Person> comparator;
    private final String scopeDescription;
    private final PersonSortAttribute attribute;
    private final boolean isDescending;

    /**
     * Creates a SortCommand to show persons matching the given predicate sorted by the given attribute.
     */
    public SortCommand(Predicate<Person> predicate, PersonSortAttribute attribute,
                       String scopeDescription, boolean isDescending) {
        this.predicate = predicate;
        this.attribute = attribute;
        this.comparator = isDescending ? attribute.getComparator().reversed() : attribute.getComparator();
        this.scopeDescription = scopeDescription;
        this.isDescending = isDescending;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        model.updateSortedPersonListComparator(comparator);
        return new CommandResult(String.format(MESSAGE_SUCCESS, scopeDescription, attribute.getKeyword(),
                isDescending ? ORDER_DESCENDING : ORDER_ASCENDING));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof SortCommand)) {
            return false;
        }

        SortCommand otherCommand = (SortCommand) other;
        return predicate.equals(otherCommand.predicate)
                && attribute == otherCommand.attribute
                && scopeDescription.equals(otherCommand.scopeDescription)
                && isDescending == otherCommand.isDescending;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .add("attribute", attribute)
                .add("scopeDescription", scopeDescription)
                .add("isDescending", isDescending)
                .toString();
    }
}
