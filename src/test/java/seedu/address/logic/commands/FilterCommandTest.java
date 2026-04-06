package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_PERSONS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonMatchesFilterPredicate;
import seedu.address.model.person.PersonMatchesFilterPredicate.NumericComparison;
import seedu.address.model.person.PersonMatchesFilterPredicate.NumericComparison.Operator;
import seedu.address.model.person.Player;
import seedu.address.model.person.Position;
import seedu.address.model.person.Role;
import seedu.address.model.person.Status;
import seedu.address.model.person.Team;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests for {@code FilterCommand}.
 */
public class FilterCommandTest {

    @Test
    public void execute_attributeAndStatFilters_success() {
        Model model = new ModelManager(buildAddressBookWithFilterablePersons(), new UserPrefs());
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        PersonMatchesFilterPredicate predicate = new PersonMatchesFilterPredicate(
                Optional.of(Role.PLAYER),
                Optional.of(new Team("First Team")),
                Optional.of(new Status("Active")),
                Optional.of(new Position("Forward")),
                Optional.of(new NumericComparison(Operator.GREATER_THAN, 10)),
                Optional.empty(),
                Optional.empty());
        FilterCommand command = new FilterCommand(predicate);

        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1), expectedModel);
        assertEquals(List.of(expectedModel.getFilteredPersonList().get(0)), model.getFilteredPersonList());
    }

    @Test
    public void execute_statFilterStaffExcluded_success() {
        Model model = new ModelManager(buildAddressBookWithFilterablePersons(), new UserPrefs());
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        PersonMatchesFilterPredicate predicate = new PersonMatchesFilterPredicate(
                Optional.of(Role.PLAYER), Optional.of(new Team("First Team")), Optional.empty(),
                Optional.empty(),
                Optional.empty(),
                Optional.of(new NumericComparison(Operator.LESS_THAN, 2)),
                Optional.empty());
        FilterCommand command = new FilterCommand(predicate);

        expectedModel.updateFilteredPersonList(predicate);
        assertCommandSuccess(command, model, String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 1), expectedModel);
    }

    @Test
    public void equals() {
        PersonMatchesFilterPredicate firstPredicate = new PersonMatchesFilterPredicate(
                Optional.of(Role.PLAYER), Optional.empty(), Optional.empty(), Optional.empty(),
                Optional.of(new NumericComparison(Operator.GREATER_THAN, 5)), Optional.empty(), Optional.empty());
        PersonMatchesFilterPredicate secondPredicate = new PersonMatchesFilterPredicate(
                Optional.empty(), Optional.of(new Team("First Team")), Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty(), Optional.empty());

        FilterCommand first = new FilterCommand(firstPredicate);
        FilterCommand second = new FilterCommand(secondPredicate);

        assertTrue(first.equals(first));
        assertTrue(first.equals(new FilterCommand(firstPredicate)));
        assertFalse(first.equals(second));
        assertFalse(first.equals(1));
        assertFalse(first.equals(null));
    }

    @Test
    public void toStringMethod() {
        PersonMatchesFilterPredicate predicate = new PersonMatchesFilterPredicate(
                Optional.of(Role.PLAYER), Optional.empty(), Optional.empty(), Optional.empty(),
                Optional.of(new NumericComparison(Operator.GREATER_THAN, 5)), Optional.empty(), Optional.empty());
        FilterCommand command = new FilterCommand(predicate);

        String expected = new ToStringBuilder(command)
                .add("predicate", predicate)
                .toString();
        assertEquals(expected, command.toString());
    }

    private seedu.address.model.AddressBook buildAddressBookWithFilterablePersons() {
        seedu.address.model.AddressBook addressBook = getTypicalAddressBook();

        Person striker = new PersonBuilder()
                .withName("Active Striker")
                .withPhone("95552001")
                .withEmail("activestriker@example.com")
                .withAddress("Alpha Street")
                .withRole(Role.PLAYER)
                .withTeam("First Team")
                .withStatus("Active")
                .withPosition("Forward")
                .build();
        Player strikerPlayer = (Player) striker;
        strikerPlayer.getStats().setGoalsScored(12);
        strikerPlayer.getStats().setMatchesWon(4);
        strikerPlayer.getStats().setMatchesLost(1);

        Person backupForward = new PersonBuilder()
                .withName("Backup Forward")
                .withPhone("95552002")
                .withEmail("backupforward@example.com")
                .withAddress("Beta Street")
                .withRole(Role.PLAYER)
                .withTeam("First Team")
                .withStatus("Active")
                .withPosition("Forward")
                .build();
        Player backupForwardPlayer = (Player) backupForward;
        backupForwardPlayer.getStats().setGoalsScored(3);
        backupForwardPlayer.getStats().setMatchesWon(1);
        backupForwardPlayer.getStats().setMatchesLost(2);

        Person staff = new PersonBuilder()
                .withName("Team Staff")
                .withPhone("95552003")
                .withEmail("teamstaff@example.com")
                .withAddress("Gamma Street")
                .withRole(Role.STAFF)
                .withTeam("First Team")
                .withStatus("Active")
                .build();

        addressBook.addPerson(striker);
        addressBook.addPerson(backupForward);
        addressBook.addPerson(staff);
        return addressBook;
    }
}
