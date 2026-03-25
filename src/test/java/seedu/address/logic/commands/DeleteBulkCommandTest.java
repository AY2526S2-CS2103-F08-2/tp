package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.commands.DeleteBulkCommand.BulkDeletionDecision;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Role;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests and unit tests for {@code DeleteBulkCommand}.
 */
public class DeleteBulkCommandTest {

    private final Tag graduatedTag = new Tag("graduated");

    @Test
    public void execute_undecided_successShowsMatchesAndPrompt() {
        Model model = new ModelManager(buildAddressBookWithGraduatedPlayers(), new UserPrefs());
        DeleteBulkCommand command = new DeleteBulkCommand(graduatedTag, BulkDeletionDecision.UNDECIDED);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateFilteredPersonList(person -> person.getTags().stream()
                .anyMatch(existingTag -> existingTag.tagName.equalsIgnoreCase(graduatedTag.tagName)));
        String expectedMatches = expectedModel.getFilteredPersonList().stream()
                .map(seedu.address.logic.Messages::format)
                .reduce((a, b) -> a + "\n" + b)
                .orElse("");
        String expectedMessage = String.format(DeleteBulkCommand.MESSAGE_DELETE_BULK_CONFIRMATION,
                expectedModel.getFilteredPersonList().size(),
                graduatedTag.tagName,
                expectedMatches,
                "Y", "N", "Y", "N");

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_confirm_successDeletesAllMatchingPersons() {
        Model model = new ModelManager(buildAddressBookWithGraduatedPlayers(), new UserPrefs());
        DeleteBulkCommand command = new DeleteBulkCommand(graduatedTag, BulkDeletionDecision.CONFIRM);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Person gradOne = new PersonBuilder()
                .withName("Grad One")
                .withPhone("91234567")
                .withEmail("gradone@example.com")
                .withAddress("Alpha Street")
                .withTags("graduated")
                .build();
        Person gradTwo = new PersonBuilder()
                .withName("Grad Two")
                .withPhone("92345678")
                .withEmail("gradtwo@example.com")
                .withAddress("Beta Street")
                .withTags("graduated")
                .build();
        Person gradStaff = new PersonBuilder()
                .withName("Grad Staff")
                .withPhone("93456789")
                .withEmail("gradstaff@example.com")
                .withAddress("Gamma Street")
                .withTags("graduated")
                .withRole(Role.STAFF)
                .build();
        expectedModel.deletePerson(gradOne);
        expectedModel.deletePerson(gradTwo);
        expectedModel.deletePerson(gradStaff);
        expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);

        String expectedMessage = String.format(DeleteBulkCommand.MESSAGE_DELETE_BULK_SUCCESS, 3, graduatedTag.tagName);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_cancel_success() {
        Model model = new ModelManager(buildAddressBookWithGraduatedPlayers(), new UserPrefs());
        DeleteBulkCommand command = new DeleteBulkCommand(graduatedTag, BulkDeletionDecision.CANCEL);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        String expectedMessage = String.format(DeleteBulkCommand.MESSAGE_DELETE_BULK_CANCELLED, graduatedTag.tagName);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noMatches_throwsCommandException() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        DeleteBulkCommand command = new DeleteBulkCommand(graduatedTag);

        assertCommandFailure(command, model,
                String.format(DeleteBulkCommand.MESSAGE_NO_MATCHING_PERSONS, graduatedTag.tagName));
    }

    @Test
    public void equals() {
        DeleteBulkCommand first = new DeleteBulkCommand(graduatedTag, BulkDeletionDecision.UNDECIDED);
        DeleteBulkCommand second = new DeleteBulkCommand(new Tag("cut"), BulkDeletionDecision.UNDECIDED);
        DeleteBulkCommand confirmed = new DeleteBulkCommand(graduatedTag, BulkDeletionDecision.CONFIRM);

        assertTrue(first.equals(first));
        assertTrue(first.equals(new DeleteBulkCommand(new Tag("graduated"), BulkDeletionDecision.UNDECIDED)));
        assertFalse(first.equals(second));
        assertFalse(first.equals(confirmed));
        assertFalse(first.equals(1));
        assertFalse(first.equals(null));
    }

    @Test
    public void toStringMethod() {
        DeleteBulkCommand command = new DeleteBulkCommand(graduatedTag, BulkDeletionDecision.CANCEL);
        String expected = new ToStringBuilder(command)
                .add("tag", graduatedTag)
                .add("decision", BulkDeletionDecision.CANCEL)
                .toString();
        assertEquals(expected, command.toString());
    }

    private seedu.address.model.AddressBook buildAddressBookWithGraduatedPlayers() {
        seedu.address.model.AddressBook addressBook = getTypicalAddressBook();
        addressBook.addPerson(new PersonBuilder()
                .withName("Grad One")
                .withPhone("91234567")
                .withEmail("gradone@example.com")
                .withAddress("Alpha Street")
                .withTags("graduated")
                .build());
        addressBook.addPerson(new PersonBuilder()
                .withName("Grad Two")
                .withPhone("92345678")
                .withEmail("gradtwo@example.com")
                .withAddress("Beta Street")
                .withTags("graduated")
                .build());
        addressBook.addPerson(new PersonBuilder()
                .withName("Grad Staff")
                .withPhone("93456789")
                .withEmail("gradstaff@example.com")
                .withAddress("Gamma Street")
                .withTags("graduated")
                .withRole(Role.STAFF)
                .build());
        return addressBook;
    }
}
