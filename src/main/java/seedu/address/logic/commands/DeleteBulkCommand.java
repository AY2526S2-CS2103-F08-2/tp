package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * Deletes all persons that have a given tag with a confirmation flow.
 */
public class DeleteBulkCommand extends Command {
    public static final String COMMAND_WORD = "deletebulk";
    public static final String YES_KEYWORD = "y";
    public static final String NO_KEYWORD = "n";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes all persons with a given tag.\n"
            + "Format: " + COMMAND_WORD + " " + PREFIX_TAG + "TAG\n"
            + "After the list is shown, type " + YES_KEYWORD + " to confirm or "
            + NO_KEYWORD + " to cancel.\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_TAG + "graduated";

    public static final String MESSAGE_NO_MATCHING_PERSONS = "No persons found with tag: %1$s";
    public static final String MESSAGE_DELETE_BULK_CONFIRMATION = "%1$d person(s) with tag '%2$s' selected:\n%3$s\n"
            + "Confirm deletion? (%4$s/%5$s)\n"
            + "Type '%6$s' to delete or '%7$s' to cancel.";
    public static final String MESSAGE_DELETE_BULK_SUCCESS = "Deleted %1$d person(s) with tag: %2$s";
    public static final String MESSAGE_DELETE_BULK_CANCELLED = "Bulk deletion cancelled for tag: %1$s";
    private static final Logger logger = LogsCenter.getLogger(DeleteBulkCommand.class);

    private final Tag tag;
    private final BulkDeletionDecision decision;

    /**
     * Describes whether the user confirmed, cancelled, or has not yet decided on bulk deletion.
     */
    public enum BulkDeletionDecision {
        UNDECIDED,
        CONFIRM,
        CANCEL
    }

    /**
     * Creates a {@code DeleteBulkCommand} that requests confirmation.
     */
    public DeleteBulkCommand(Tag tag) {
        this(tag, BulkDeletionDecision.UNDECIDED);
    }

    /**
     * Creates a {@code DeleteBulkCommand} with an explicit decision state.
     */
    public DeleteBulkCommand(Tag tag, BulkDeletionDecision decision) {
        requireNonNull(tag);
        requireNonNull(decision);
        assert !tag.tagName.isBlank() : "DeleteBulk tag must not be blank";
        this.tag = tag;
        this.decision = decision;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Predicate<Person> predicate = person -> person.getTags().stream()
                .anyMatch(existingTag -> existingTag.tagName.equalsIgnoreCase(tag.tagName));
        List<Person> personsToDelete = model.getPersonsMatching(predicate);
        assert personsToDelete != null : "Model returned null matching list";

        logger.fine(() -> String.format("deletebulk decision=%s tag=%s matches=%d",
                decision, tag.tagName, personsToDelete.size()));
        if (personsToDelete.isEmpty()) {
            model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
            throw new CommandException(String.format(MESSAGE_NO_MATCHING_PERSONS, tag.tagName));
        }

        if (decision == BulkDeletionDecision.CONFIRM) {
            assert !personsToDelete.isEmpty() : "Confirm decision requires non-empty matches";
            for (Person person : personsToDelete) {
                model.deletePerson(person);
            }
            model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
            return new CommandResult(String.format(MESSAGE_DELETE_BULK_SUCCESS, personsToDelete.size(), tag.tagName));
        }

        if (decision == BulkDeletionDecision.CANCEL) {
            model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
            return new CommandResult(String.format(MESSAGE_DELETE_BULK_CANCELLED, tag.tagName));
        }

        model.updateFilteredPersonList(predicate);
        String formattedMatches = personsToDelete.stream()
                .map(Messages::format)
                .collect(Collectors.joining("\n"));
        return new CommandResult(String.format(MESSAGE_DELETE_BULK_CONFIRMATION,
                personsToDelete.size(), tag.tagName, formattedMatches,
                YES_KEYWORD.toUpperCase(Locale.ROOT), NO_KEYWORD.toUpperCase(Locale.ROOT),
                YES_KEYWORD.toUpperCase(Locale.ROOT), NO_KEYWORD.toUpperCase(Locale.ROOT)));
    }

    /**
     * Returns the tag used for bulk matching.
     */
    public Tag getTag() {
        return tag;
    }

    /**
     * Returns the current bulk deletion decision state.
     */
    public BulkDeletionDecision getDecision() {
        return decision;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DeleteBulkCommand)) {
            return false;
        }

        DeleteBulkCommand otherCommand = (DeleteBulkCommand) other;
        return tag.equals(otherCommand.tag)
                && decision == otherCommand.decision;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("tag", tag)
                .add("decision", decision)
                .toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(tag, decision);
    }
}
