package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsAllKeywordsPredicate;
import seedu.address.model.person.Person;

/**
 * Deletes a person by displayed index or by matching name keywords.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";
    public static final String CONFIRM_KEYWORD = "confirm";
    public static final String YES_KEYWORD = "y";
    public static final String NO_KEYWORD = "n";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes a person by list index or name keywords.\n"
            + "You can confirm with Y/N after selecting a person.\n"
            + "Parameters:\n"
            + "  1) INDEX\n"
            + "  2) NAME [MATCH_INDEX]\n"
            + "Examples:\n"
            + "  " + COMMAND_WORD + " 3\n"
            + "  " + COMMAND_WORD + " Ryan\n";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted %1$s: %2$s";
    public static final String MESSAGE_DELETE_PERSON_CANCELLED = "Deletion cancelled for %1$s: %2$s";
    public static final String MESSAGE_NO_MATCHING_PERSON = "No persons found matching: %1$s";
    public static final String MESSAGE_INVALID_MATCH_INDEX = "The match index provided is invalid";

    public static final String MESSAGE_DELETE_PERSON_CONFIRMATION = "Selected %1$s for deletion: %2$s\n"
            + "Confirm deletion? (%3$s/%4$s)\n"
            + "Type '%5$s' to delete or '%6$s' to cancel.";
    public static final String MESSAGE_DELETE_PERSON_CLASH = "Multiple matching persons found for \"%1$s\":\n%2$s\n"
            + "Type the index corresponding to the person you wish to delete.";

    private final Index targetIndex;
    private final String criteria;
    private final Index criteriaMatchIndex;
    private final DeletionDecision deletionDecision;

    /**
     * Describes whether the user confirmed, cancelled, or has not yet decided on a deletion.
     */
    public enum DeletionDecision {
        UNDECIDED,
        CONFIRM,
        CANCEL
    }

    /**
     * Creates a {@code DeleteCommand} that requests confirmation for deletion by index.
     */
    public DeleteCommand(Index targetIndex) {
        this(targetIndex, DeletionDecision.UNDECIDED);
    }

    /**
     * Creates a {@code DeleteCommand} for deletion by index.
     *
     * @param targetIndex index of the person in the filtered list.
     * @param isConfirmed whether the deletion has been explicitly confirmed.
     */
    public DeleteCommand(Index targetIndex, boolean isConfirmed) {
        this(targetIndex, isConfirmed ? DeletionDecision.CONFIRM : DeletionDecision.UNDECIDED);
    }

    /**
     * Creates a {@code DeleteCommand} for deletion by index with an explicit decision state.
     *
     * @param targetIndex index of the person in the filtered list.
     * @param deletionDecision user confirmation state.
     */
    public DeleteCommand(Index targetIndex, DeletionDecision deletionDecision) {
        this(targetIndex, null, null, deletionDecision);
    }

    /**
     * Creates a {@code DeleteCommand} based on matching criteria.
     *
     * @param criteria keywords used to find matching persons.
     * @param criteriaMatchIndex optional index for selecting a person when there are clashes.
     * @param deletionDecision user confirmation state.
     */
    public DeleteCommand(String criteria, Index criteriaMatchIndex, DeletionDecision deletionDecision) {
        this(null, criteria, criteriaMatchIndex, deletionDecision);
    }

    private DeleteCommand(Index targetIndex, String criteria, Index criteriaMatchIndex,
                          DeletionDecision deletionDecision) {
        this.targetIndex = targetIndex;
        this.criteria = criteria == null ? null : criteria.trim();
        this.criteriaMatchIndex = criteriaMatchIndex;
        this.deletionDecision = deletionDecision;
    }

    /**
     * Creates a {@code DeleteCommand} for input that could refer to either an index or a literal numeric name.
     */
    public static DeleteCommand forAmbiguousNumericInput(String criteria, Index targetIndex) {
        return new DeleteCommand(targetIndex, criteria, null, DeletionDecision.UNDECIDED);
    }

    /**
     * Creates a {@code DeleteCommand} for numeric input that could refer to either an index or a literal name,
     * while carrying an explicit confirmation decision.
     */
    public static DeleteCommand forAmbiguousNumericInput(String criteria, Index targetIndex,
                                                         DeletionDecision deletionDecision) {
        return new DeleteCommand(targetIndex, criteria, null, deletionDecision);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        if (targetIndex != null && criteria != null) {
            return executeAmbiguousDelete(model);
        }

        return targetIndex != null ? executeIndexDelete(model) : executeCriteriaDelete(model);
    }

    private CommandResult executeAmbiguousDelete(Model model) throws CommandException {
        boolean hasExactNameMatch = model.getAddressBook().getPersonList().stream()
                .anyMatch(person -> person.getName().fullName.equalsIgnoreCase(criteria));

        return hasExactNameMatch ? executeCriteriaDelete(model) : executeIndexDelete(model);
    }

    private CommandResult executeIndexDelete(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToDelete = lastShownList.get(targetIndex.getZeroBased());
        return executeDecision(model, personToDelete);
    }

    private CommandResult executeCriteriaDelete(Model model) throws CommandException {
        NameContainsAllKeywordsPredicate predicate = buildNamePredicate(criteria);
        List<Person> matches = model.getPersonsMatching(predicate);
        if (matches.isEmpty()) {
            throw new CommandException(String.format(MESSAGE_NO_MATCHING_PERSON, criteria));
        }

        if (matches.size() > 1 && criteriaMatchIndex == null) {
            String formattedMatches = formatMatches(matches);
            return new CommandResult(String.format(MESSAGE_DELETE_PERSON_CLASH, criteria, formattedMatches));
        }

        Person personToDelete = selectPersonFromMatches(matches);
        return executeDecision(model, personToDelete);
    }

    private Person selectPersonFromMatches(List<Person> matches) throws CommandException {
        if (criteriaMatchIndex == null) {
            return matches.get(0);
        }

        if (criteriaMatchIndex.getZeroBased() >= matches.size()) {
            throw new CommandException(MESSAGE_INVALID_MATCH_INDEX);
        }

        return matches.get(criteriaMatchIndex.getZeroBased());
    }

    private CommandResult executeDecision(Model model, Person personToDelete) throws CommandException {
        String roleLabel = personToDelete.getRole().name().toLowerCase(Locale.ROOT);

        if (deletionDecision == DeletionDecision.CONFIRM) {
            model.deletePerson(personToDelete);
            return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS,
                    roleLabel, Messages.format(personToDelete)));
        }

        if (deletionDecision == DeletionDecision.CANCEL) {
            return new CommandResult(String.format(MESSAGE_DELETE_PERSON_CANCELLED,
                    roleLabel, Messages.format(personToDelete)));
        }

        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_CONFIRMATION,
                roleLabel, Messages.format(personToDelete), YES_KEYWORD.toUpperCase(Locale.ROOT),
                NO_KEYWORD.toUpperCase(Locale.ROOT),
                YES_KEYWORD.toUpperCase(Locale.ROOT),
                NO_KEYWORD.toUpperCase(Locale.ROOT)));
    }

    private NameContainsAllKeywordsPredicate buildNamePredicate(String rawCriteria) {
        List<String> keywords = Arrays.asList(rawCriteria.trim().split("\\s+"));
        return new NameContainsAllKeywordsPredicate(keywords);
    }

    private String formatMatches(List<Person> matches) {
        return IntStream.range(0, matches.size())
                .mapToObj(i -> (i + 1) + ". " + Messages.format(matches.get(i)))
                .collect(Collectors.joining("\n"));
    }

    /**
     * Returns the target index for index-based delete commands.
     */
    public Index getTargetIndex() {
        return targetIndex;
    }

    /**
     * Returns the raw criteria for criteria-based delete commands.
     */
    public String getCriteria() {
        return criteria;
    }

    /**
     * Returns the clash-selection index for criteria-based delete commands, if present.
     */
    public Index getCriteriaMatchIndex() {
        return criteriaMatchIndex;
    }

    /**
     * Returns the current deletion decision state.
     */
    public DeletionDecision getDeletionDecision() {
        return deletionDecision;
    }

    /**
     * Returns true if this command deletes by criteria instead of list index.
     */
    public boolean isCriteriaDelete() {
        return criteria != null;
    }

    /**
     * Returns true if this command was created from numeric input that could be interpreted as either a name or index.
     */
    public boolean isAmbiguousNumericDelete() {
        return targetIndex != null && criteria != null;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof DeleteCommand)) {
            return false;
        }

        DeleteCommand otherDeleteCommand = (DeleteCommand) other;
        return Objects.equals(targetIndex, otherDeleteCommand.targetIndex)
                && Objects.equals(criteria, otherDeleteCommand.criteria)
                && Objects.equals(criteriaMatchIndex, otherDeleteCommand.criteriaMatchIndex)
                && deletionDecision == otherDeleteCommand.deletionDecision;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .add("criteria", criteria)
                .add("criteriaMatchIndex", criteriaMatchIndex)
                .add("deletionDecision", deletionDecision)
                .toString();
    }
}
