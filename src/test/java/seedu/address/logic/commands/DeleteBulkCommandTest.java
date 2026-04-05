package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.DeleteBulkCommand.BulkDeletionDecision;
import seedu.address.logic.commands.DeleteBulkCommand.BulkDeletionCriterion;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Role;
import seedu.address.model.person.Status;
import seedu.address.model.person.Team;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests and unit tests for {@code DeleteBulkCommand}.
 */
public class DeleteBulkCommandTest {

    private final Tag graduatedTag = new Tag("graduated");
    private final Team reserveTeam = new Team("Reserve Team");
    private final Status unavailableStatus = new Status("Unavailable");

    @Test
    public void execute_undecided_successShowsMatchesAndPrompt() {
        Model model = new ModelManager(buildAddressBookWithGraduatedPlayers(), new UserPrefs());
        DeleteBulkCommand command = new DeleteBulkCommand(graduatedTag, BulkDeletionDecision.UNDECIDED);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateFilteredPersonList(person -> person.getTags().stream()
                .anyMatch(existingTag -> existingTag.tagName.equalsIgnoreCase(graduatedTag.tagName)));
        String expectedMatches = expectedModel.getFilteredPersonList().stream()
                .map(Messages::format)
                .reduce((a, b) -> a + "\n" + b)
                .orElse("");
        String expectedMessage = String.format(DeleteBulkCommand.MESSAGE_DELETE_BULK_CONFIRMATION,
                expectedModel.getFilteredPersonList().size(),
                "tag",
                graduatedTag.tagName,
                expectedMatches,
                "Y", "N", "Y", "N");

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_confirm_successDeletesAllMatchingPersons() throws Exception {
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

        String expectedMessage = String.format(DeleteBulkCommand.MESSAGE_DELETE_BULK_SUCCESS, 3, "tag",
                graduatedTag.tagName);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_cancel_success() {
        Model model = new ModelManager(buildAddressBookWithGraduatedPlayers(), new UserPrefs());
        DeleteBulkCommand command = new DeleteBulkCommand(graduatedTag, BulkDeletionDecision.CANCEL);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        String expectedMessage = String.format(DeleteBulkCommand.MESSAGE_DELETE_BULK_CANCELLED, "tag",
                graduatedTag.tagName);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noMatches_throwsCommandException() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        DeleteBulkCommand command = new DeleteBulkCommand(graduatedTag);

        assertCommandFailure(command, model,
                String.format(DeleteBulkCommand.MESSAGE_NO_MATCHING_PERSONS, "tag", graduatedTag.tagName));
    }

    @Test
    public void execute_confirmTeam_successDeletesAllMatchingPersons() throws Exception {
        Model model = new ModelManager(buildAddressBookWithBulkAttributes(), new UserPrefs());
        DeleteBulkCommand command = new DeleteBulkCommand(reserveTeam, BulkDeletionDecision.CONFIRM);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(new PersonBuilder()
                .withName("Reserve One")
                .withPhone("95550001")
                .withEmail("reserveone@example.com")
                .withAddress("Delta Street")
                .withTeam("Reserve Team")
                .build());
        expectedModel.deletePerson(new PersonBuilder()
                .withName("Reserve Staff")
                .withPhone("95550002")
                .withEmail("reservestaff@example.com")
                .withAddress("Epsilon Street")
                .withRole(Role.STAFF)
                .withTeam("Reserve Team")
                .build());
        expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);

        String expectedMessage = String.format(DeleteBulkCommand.MESSAGE_DELETE_BULK_SUCCESS, 2, "team",
                reserveTeam.value);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_confirmStatus_successDeletesAllMatchingPersons() throws Exception {
        Model model = new ModelManager(buildAddressBookWithBulkAttributes(), new UserPrefs());
        DeleteBulkCommand command = new DeleteBulkCommand(unavailableStatus, BulkDeletionDecision.CONFIRM);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.deletePerson(new PersonBuilder()
                .withName("Unavailable One")
                .withPhone("95550003")
                .withEmail("unavailableone@example.com")
                .withAddress("Zeta Street")
                .withStatus("Unavailable")
                .build());
        expectedModel.deletePerson(new PersonBuilder()
                .withName("Unavailable Staff")
                .withPhone("95550004")
                .withEmail("unavailablestaff@example.com")
                .withAddress("Eta Street")
                .withRole(Role.STAFF)
                .withStatus("Unavailable")
                .build());
        expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);

        String expectedMessage = String.format(DeleteBulkCommand.MESSAGE_DELETE_BULK_SUCCESS, 2, "status",
                unavailableStatus.value);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        DeleteBulkCommand first = new DeleteBulkCommand(graduatedTag, BulkDeletionDecision.UNDECIDED);
        DeleteBulkCommand second = new DeleteBulkCommand(new Tag("cut"), BulkDeletionDecision.UNDECIDED);
        DeleteBulkCommand confirmed = new DeleteBulkCommand(graduatedTag, BulkDeletionDecision.CONFIRM);
        DeleteBulkCommand teamCommand = new DeleteBulkCommand(reserveTeam, BulkDeletionDecision.UNDECIDED);

        assertTrue(first.equals(first));
        assertTrue(first.equals(new DeleteBulkCommand(new Tag("graduated"), BulkDeletionDecision.UNDECIDED)));
        assertFalse(first.equals(second));
        assertFalse(first.equals(confirmed));
        assertFalse(first.equals(teamCommand));
        assertFalse(first.equals(1));
        assertFalse(first.equals(null));
    }

    @Test
    public void toStringMethod() {
        DeleteBulkCommand command = new DeleteBulkCommand(graduatedTag, BulkDeletionDecision.CANCEL);
        String expected = new ToStringBuilder(command)
                .add("criterion", BulkDeletionCriterion.forTag(graduatedTag))
                .add("decision", BulkDeletionDecision.CANCEL)
                .toString();
        assertEquals(expected, command.toString());
    }

    @Test
    public void hashCode_sameValues_sameHashCode() {
        DeleteBulkCommand first = new DeleteBulkCommand(graduatedTag, BulkDeletionDecision.UNDECIDED);
        DeleteBulkCommand second = new DeleteBulkCommand(new Tag("graduated"), BulkDeletionDecision.UNDECIDED);
        DeleteBulkCommand different = new DeleteBulkCommand(graduatedTag, BulkDeletionDecision.CONFIRM);

        assertEquals(first.hashCode(), second.hashCode());
        assertFalse(first.hashCode() == different.hashCode());
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

    private seedu.address.model.AddressBook buildAddressBookWithBulkAttributes() {
        seedu.address.model.AddressBook addressBook = getTypicalAddressBook();
        addressBook.addPerson(new PersonBuilder()
                .withName("Reserve One")
                .withPhone("95550001")
                .withEmail("reserveone@example.com")
                .withAddress("Delta Street")
                .withTeam("Reserve Team")
                .build());
        addressBook.addPerson(new PersonBuilder()
                .withName("Reserve Staff")
                .withPhone("95550002")
                .withEmail("reservestaff@example.com")
                .withAddress("Epsilon Street")
                .withRole(Role.STAFF)
                .withTeam("Reserve Team")
                .build());
        addressBook.addPerson(new PersonBuilder()
                .withName("Unavailable One")
                .withPhone("95550003")
                .withEmail("unavailableone@example.com")
                .withAddress("Zeta Street")
                .withStatus("Unavailable")
                .build());
        addressBook.addPerson(new PersonBuilder()
                .withName("Unavailable Staff")
                .withPhone("95550004")
                .withEmail("unavailablestaff@example.com")
                .withAddress("Eta Street")
                .withRole(Role.STAFF)
                .withStatus("Unavailable")
                .build());
        return addressBook;
    }
}
