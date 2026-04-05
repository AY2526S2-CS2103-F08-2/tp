package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.PersonMatchesListFiltersPredicate;
import seedu.address.model.person.Position;
import seedu.address.model.person.Role;
import seedu.address.model.person.Status;
import seedu.address.model.person.Team;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests for {@code ListFilteredCommand}.
 */
public class ListFilteredCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(buildAddressBookWithAttributes(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_teamFilter_success() {
        PersonMatchesListFiltersPredicate predicate = new PersonMatchesListFiltersPredicate(Optional.empty(),
                Optional.of(new Team("First Team")), Optional.empty(), Optional.empty());
        ListFilteredCommand command = new ListFilteredCommand(predicate, "persons matching team First Team");

        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, "Listed persons matching team First Team", expectedModel);
    }

    @Test
    public void execute_roleAndCombinedFilters_success() {
        PersonMatchesListFiltersPredicate predicate = new PersonMatchesListFiltersPredicate(Optional.of(Role.PLAYER),
                Optional.of(new Team("First Team")),
                Optional.of(new Status("Active")),
                Optional.of(new Position("Defender")));
        ListFilteredCommand command = new ListFilteredCommand(predicate,
                "players matching team First Team, status Active, position Defender");

        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model,
                "Listed players matching team First Team, status Active, position Defender", expectedModel);
    }

    @Test
    public void equals() {
        PersonMatchesListFiltersPredicate firstPredicate = new PersonMatchesListFiltersPredicate(Optional.empty(),
                Optional.of(new Team("First Team")), Optional.empty(), Optional.empty());
        PersonMatchesListFiltersPredicate secondPredicate = new PersonMatchesListFiltersPredicate(
                Optional.of(Role.STAFF),
                Optional.empty(),
                Optional.of(new Status("Active")),
                Optional.empty());

        ListFilteredCommand first = new ListFilteredCommand(firstPredicate, "persons matching team First Team");
        ListFilteredCommand second = new ListFilteredCommand(secondPredicate, "staff matching status Active");

        assertTrue(first.equals(first));
        assertTrue(first.equals(new ListFilteredCommand(firstPredicate, "persons matching team First Team")));
        assertFalse(first.equals(second));
        assertFalse(first.equals(1));
        assertFalse(first.equals(null));
    }

    private seedu.address.model.AddressBook buildAddressBookWithAttributes() {
        seedu.address.model.AddressBook addressBook = getTypicalAddressBook();
        addressBook.addPerson(new PersonBuilder()
                .withName("Active Defender")
                .withPhone("95551001")
                .withEmail("activedefender@example.com")
                .withAddress("Alpha Street")
                .withRole(Role.PLAYER)
                .withTeam("First Team")
                .withStatus("Active")
                .withPosition("Defender")
                .build());
        addressBook.addPerson(new PersonBuilder()
                .withName("Reserve Forward")
                .withPhone("95551002")
                .withEmail("reserveforward@example.com")
                .withAddress("Beta Street")
                .withRole(Role.PLAYER)
                .withTeam("Second Team")
                .withStatus("Unavailable")
                .withPosition("Forward")
                .build());
        addressBook.addPerson(new PersonBuilder()
                .withName("First Team Staff")
                .withPhone("95551003")
                .withEmail("firstteamstaff@example.com")
                .withAddress("Gamma Street")
                .withRole(Role.STAFF)
                .withTeam("First Team")
                .withStatus("Active")
                .build());
        return addressBook;
    }
}
