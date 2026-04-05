package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
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
import seedu.address.model.person.Status;
import seedu.address.model.person.Team;
import seedu.address.model.tag.Tag;

/**
 * Deletes all persons that have a given tag with a confirmation flow.
 */
public class DeleteBulkCommand extends Command {
    public static final String COMMAND_WORD = "deletebulk";
    public static final String YES_KEYWORD = "y";
    public static final String NO_KEYWORD = "n";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes all persons with a given tag, team, or status.\n"
            + "Format: " + COMMAND_WORD + " [t/TAG | tm/TEAM | st/STATUS]\n"
            + "After the list is shown, type " + YES_KEYWORD + " to confirm or "
            + NO_KEYWORD + " to cancel.\n"
            + "Examples: " + COMMAND_WORD + " t/graduated, "
            + COMMAND_WORD + " tm/Reserve Team, "
            + COMMAND_WORD + " st/Unavailable";

    public static final String MESSAGE_NO_MATCHING_PERSONS = "No persons found with %1$s: %2$s";
    public static final String MESSAGE_DELETE_BULK_CONFIRMATION = "%1$d person(s) with %2$s '%3$s' selected:\n%4$s\n"
            + "Confirm deletion? (%5$s/%6$s)\n"
            + "Type '%7$s' to delete or '%8$s' to cancel.";
    public static final String MESSAGE_DELETE_BULK_SUCCESS = "Deleted %1$d person(s) with %2$s: %3$s";
    public static final String MESSAGE_DELETE_BULK_CANCELLED = "Bulk deletion cancelled for %1$s: %2$s";
    private static final Logger logger = LogsCenter.getLogger(DeleteBulkCommand.class);

    private final BulkDeletionCriterion criterion;
    private final BulkDeletionDecision decision;

    /**
     * Represents the supported bulk-deletion criteria.
     */
    public enum BulkDeletionCriterionType {
        TAG("tag"),
        TEAM("team"),
        STATUS("status");

        private final String displayName;

        BulkDeletionCriterionType(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }

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
     * Creates a {@code DeleteBulkCommand} that requests confirmation.
     */
    public DeleteBulkCommand(Tag tag, BulkDeletionDecision decision) {
        this(BulkDeletionCriterion.forTag(tag), decision);
    }

    /**
     * Creates a {@code DeleteBulkCommand} that requests confirmation.
     */
    public DeleteBulkCommand(Team team) {
        this(team, BulkDeletionDecision.UNDECIDED);
    }

    /**
     * Creates a {@code DeleteBulkCommand} with an explicit team criterion and decision state.
     */
    public DeleteBulkCommand(Team team, BulkDeletionDecision decision) {
        this(BulkDeletionCriterion.forTeam(team), decision);
    }

    /**
     * Creates a {@code DeleteBulkCommand} that requests confirmation.
     */
    public DeleteBulkCommand(Status status) {
        this(status, BulkDeletionDecision.UNDECIDED);
    }

    /**
     * Creates a {@code DeleteBulkCommand} with an explicit status criterion and decision state.
     */
    public DeleteBulkCommand(Status status, BulkDeletionDecision decision) {
        this(BulkDeletionCriterion.forStatus(status), decision);
    }

    /**
     * Creates a {@code DeleteBulkCommand} with an explicit decision state.
     */
    public DeleteBulkCommand(BulkDeletionCriterion criterion, BulkDeletionDecision decision) {
        requireNonNull(criterion);
        requireNonNull(decision);
        this.criterion = criterion;
        this.decision = decision;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Predicate<Person> predicate = criterion.toPredicate();
        List<Person> personsToDelete = model.getPersonsMatching(predicate);
        assert personsToDelete != null : "Model returned null matching list";

        logger.fine(() -> String.format("deletebulk decision=%s criterionType=%s criterionValue=%s matches=%d",
                decision, criterion.getType(), criterion.getValue(), personsToDelete.size()));
        if (personsToDelete.isEmpty()) {
            model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
            throw new CommandException(String.format(MESSAGE_NO_MATCHING_PERSONS,
                    criterion.getDisplayName(), criterion.getValue()));
        }

        if (decision == BulkDeletionDecision.CONFIRM) {
            assert !personsToDelete.isEmpty() : "Confirm decision requires non-empty matches";
            for (Person person : personsToDelete) {
                model.deletePerson(person);
            }
            model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
            return new CommandResult(String.format(MESSAGE_DELETE_BULK_SUCCESS,
                    personsToDelete.size(), criterion.getDisplayName(), criterion.getValue()));
        }

        if (decision == BulkDeletionDecision.CANCEL) {
            model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
            return new CommandResult(String.format(MESSAGE_DELETE_BULK_CANCELLED,
                    criterion.getDisplayName(), criterion.getValue()));
        }

        model.updateFilteredPersonList(predicate);
        String formattedMatches = personsToDelete.stream()
                .map(Messages::format)
                .collect(Collectors.joining("\n"));
        return new CommandResult(String.format(MESSAGE_DELETE_BULK_CONFIRMATION,
                personsToDelete.size(), criterion.getDisplayName(), criterion.getValue(), formattedMatches,
                YES_KEYWORD.toUpperCase(Locale.ROOT), NO_KEYWORD.toUpperCase(Locale.ROOT),
                YES_KEYWORD.toUpperCase(Locale.ROOT), NO_KEYWORD.toUpperCase(Locale.ROOT)));
    }

    /**
     * Returns the criterion used for bulk matching.
     */
    public BulkDeletionCriterion getCriterion() {
        return criterion;
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
        return criterion.equals(otherCommand.criterion)
                && decision == otherCommand.decision;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("criterion", criterion)
                .add("decision", decision)
                .toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(criterion, decision);
    }

    /**
     * Encapsulates the field/value pair used for bulk deletion.
     */
    public static final class BulkDeletionCriterion {
        private final BulkDeletionCriterionType type;
        private final String value;

        private BulkDeletionCriterion(BulkDeletionCriterionType type, String value) {
            requireNonNull(type);
            requireNonNull(value);
            this.type = type;
            this.value = value;
        }

        public static BulkDeletionCriterion forTag(Tag tag) {
            requireNonNull(tag);
            assert !tag.tagName.isBlank() : "DeleteBulk tag must not be blank";
            return new BulkDeletionCriterion(BulkDeletionCriterionType.TAG, tag.tagName);
        }

        public static BulkDeletionCriterion forTeam(Team team) {
            requireNonNull(team);
            assert !team.value.isBlank() : "DeleteBulk team must not be blank";
            return new BulkDeletionCriterion(BulkDeletionCriterionType.TEAM, team.value);
        }

        public static BulkDeletionCriterion forStatus(Status status) {
            requireNonNull(status);
            assert !status.value.isBlank() : "DeleteBulk status must not be blank";
            return new BulkDeletionCriterion(BulkDeletionCriterionType.STATUS, status.value);
        }

        public BulkDeletionCriterionType getType() {
            return type;
        }

        public String getValue() {
            return value;
        }

        public String getDisplayName() {
            return type.getDisplayName();
        }

        public String toArgumentString() {
            return switch (type) {
            case TAG -> "t/" + value;
            case TEAM -> "tm/" + value;
            case STATUS -> "st/" + value;
            };
        }

        public Predicate<Person> toPredicate() {
            return switch (type) {
            case TAG -> person -> person.getTags().stream()
                    .anyMatch(existingTag -> existingTag.tagName.equalsIgnoreCase(value));
            case TEAM -> person -> person.getTeam().value.equalsIgnoreCase(value);
            case STATUS -> person -> person.getStatus().value.equalsIgnoreCase(value);
            };
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            if (!(other instanceof BulkDeletionCriterion)) {
                return false;
            }

            BulkDeletionCriterion otherCriterion = (BulkDeletionCriterion) other;
            return type == otherCriterion.type
                    && value.equalsIgnoreCase(otherCriterion.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(type, value.toLowerCase(Locale.ROOT));
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .add("type", type)
                    .add("value", value)
                    .toString();
        }
    }
}
