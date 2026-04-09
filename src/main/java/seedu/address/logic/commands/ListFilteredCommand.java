package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.PersonMatchesListFiltersPredicate;

/**
 * Lists persons that match the given role and attribute filters.
 */
public class ListFilteredCommand extends Command {

    public static final String MESSAGE_SUCCESS = "Listed %s";
    public static final String MESSAGE_NO_MATCHES = "No %s found.";
    public static final String MESSAGE_TEAM_NOT_FOUND = "Team %s does not exist in the address book!";
    public static final String MESSAGE_STATUS_NOT_FOUND = "Status %s does not exist in the address book!";
    public static final String MESSAGE_POSITION_NOT_FOUND = "Position %s does not exist in the address book!";

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
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        validateAttributesExist(model);
        model.updateFilteredPersonList(predicate);
        if (model.getFilteredPersonList().isEmpty()) {
            return new CommandResult(String.format(MESSAGE_NO_MATCHES, description));
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, description));
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
