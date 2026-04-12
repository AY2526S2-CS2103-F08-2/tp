package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.KEYWORD_MATCHING_MEIER;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Locale;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.DeleteCommand.DeletionDecision;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.NameContainsAllKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for
 * {@code DeleteCommand}.
 */
public class DeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_CONFIRMATION,
                roleLabel(personToDelete), Messages.format(personToDelete), "Y", "N", "Y", "N");

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void executeConfirmedDelete_unfilteredList_success() throws CommandException {
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON, true);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                roleLabel(personToDelete), Messages.format(personToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(personToDelete);
        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void executeCancelledDelete_unfilteredList_success() {
        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON, DeletionDecision.CANCEL);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_CANCELLED,
                roleLabel(personToDelete), Messages.format(personToDelete));

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);

        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_criteriaSingleMatch_success() {
        DeleteCommand deleteCommand = new DeleteCommand("Ida", null, DeletionDecision.UNDECIDED);
        Person personToDelete = model.getAddressBook().getPersonList().stream()
                .filter(person -> person.getName().fullName.equals("Ida Mueller"))
                .findFirst()
                .orElseThrow();

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_CONFIRMATION,
                roleLabel(personToDelete), Messages.format(personToDelete), "Y", "N", "Y", "N");

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateFilteredPersonList(new NameContainsAllKeywordsPredicate(Arrays.asList("Ida")));
        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_criteriaClashListsCandidates_success() {
        DeleteCommand deleteCommand = new DeleteCommand(KEYWORD_MATCHING_MEIER, null, DeletionDecision.UNDECIDED);
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateFilteredPersonList(
                new NameContainsAllKeywordsPredicate(Arrays.asList(KEYWORD_MATCHING_MEIER)));
        String expectedList = "1. " + Messages.format(expectedModel.getFilteredPersonList().get(0)) + "\n"
                + "2. " + Messages.format(expectedModel.getFilteredPersonList().get(1));
        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_CLASH,
                KEYWORD_MATCHING_MEIER, expectedList);
        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void executeCriteriaClashWithIndexAndConfirmDeletesSelectedSuccess() throws CommandException {
        DeleteCommand deleteCommand = new DeleteCommand(KEYWORD_MATCHING_MEIER, INDEX_SECOND_PERSON,
                DeletionDecision.CONFIRM);
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateFilteredPersonList(
                new NameContainsAllKeywordsPredicate(Arrays.asList(KEYWORD_MATCHING_MEIER)));
        Person personToDelete = expectedModel.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_SUCCESS,
                roleLabel(personToDelete), Messages.format(personToDelete));
        expectedModel.deletePerson(personToDelete);
        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_criteriaMultipleKeywords_narrowsToSingleMatch() {
        AddressBook addressBook = new AddressBook(model.getAddressBook());
        Person alexYeoh = new PersonBuilder().withName("Alex Yeoh").withPhone("87438807")
                .withEmail("alexyeoh2@example.com").withAddress("Blk 30 Geylang Street 29, #06-40").build();
        Person alexNeo = new PersonBuilder().withName("Alex Neo").withPhone("12345678")
                .withEmail("alexneo@example.com").withAddress("123 New Street").build();
        addressBook.addPerson(alexYeoh);
        addressBook.addPerson(alexNeo);

        DeleteCommand deleteCommand = new DeleteCommand("Alex Neo", null, DeletionDecision.UNDECIDED);
        ModelManager modelWithAlexes = new ModelManager(addressBook, new UserPrefs());
        ModelManager expectedModel = new ModelManager(addressBook, new UserPrefs());
        Person personToDelete = expectedModel.getAddressBook().getPersonList().stream()
                .filter(person -> person.getName().fullName.equals("Alex Neo"))
                .findFirst()
                .orElseThrow();

        expectedModel.updateFilteredPersonList(new NameContainsAllKeywordsPredicate(Arrays.asList("Alex", "Neo")));
        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_CONFIRMATION,
                roleLabel(personToDelete), Messages.format(personToDelete), "Y", "N", "Y", "N");

        assertCommandSuccess(deleteCommand, modelWithAlexes, expectedMessage, expectedModel);
    }

    @Test
    public void execute_ambiguousNumericInput_prefersExactNameMatch() {
        AddressBook addressBook = new AddressBook(model.getAddressBook());
        Person numericNamePerson = new PersonBuilder().withName("2").withPhone("90000001")
                .withEmail("two@example.com").withAddress("2 Street").build();
        addressBook.addPerson(numericNamePerson);

        DeleteCommand deleteCommand = DeleteCommand.forAmbiguousNumericInput("2", INDEX_SECOND_PERSON);
        ModelManager numericModel = new ModelManager(addressBook, new UserPrefs());
        ModelManager expectedModel = new ModelManager(addressBook, new UserPrefs());
        expectedModel.updateFilteredPersonList(new NameContainsAllKeywordsPredicate(Arrays.asList("2")));

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_CONFIRMATION,
                roleLabel(numericNamePerson), Messages.format(numericNamePerson), "Y", "N", "Y", "N");

        assertCommandSuccess(deleteCommand, numericModel, expectedMessage, expectedModel);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personToDelete = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        DeleteCommand deleteCommand = new DeleteCommand(INDEX_FIRST_PERSON);
        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PERSON_CONFIRMATION,
                roleLabel(personToDelete), Messages.format(personToDelete), "Y", "N", "Y", "N");

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        showPersonAtIndex(expectedModel, INDEX_FIRST_PERSON);
        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        DeleteCommand deleteCommand = new DeleteCommand(outOfBoundIndex);
        assertCommandFailure(deleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteCommand deleteFirstCommand = new DeleteCommand(INDEX_FIRST_PERSON);
        DeleteCommand deleteSecondCommand = new DeleteCommand(INDEX_SECOND_PERSON);
        DeleteCommand confirmedDeleteFirstCommand = new DeleteCommand(INDEX_FIRST_PERSON, true);

        assertTrue(deleteFirstCommand.equals(deleteFirstCommand));
        assertTrue(deleteFirstCommand.equals(new DeleteCommand(INDEX_FIRST_PERSON)));
        assertFalse(deleteFirstCommand.equals(1));
        assertFalse(deleteFirstCommand.equals(null));
        assertFalse(deleteFirstCommand.equals(deleteSecondCommand));
        assertFalse(deleteFirstCommand.equals(confirmedDeleteFirstCommand));
    }

    @Test
    public void toStringMethod() {
        Index targetIndex = Index.fromOneBased(1);
        DeleteCommand deleteCommand = new DeleteCommand(targetIndex);
        String expected = DeleteCommand.class.getCanonicalName()
                + "{targetIndex=" + targetIndex
                + ", criteria=null, criteriaMatchIndex=null, deletionDecision=UNDECIDED}";
        assertEquals(expected, deleteCommand.toString());
    }

    private String roleLabel(Person person) {
        return person.getRole().name().toLowerCase(Locale.ROOT);
    }
}
