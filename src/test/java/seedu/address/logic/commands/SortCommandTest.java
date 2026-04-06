package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonHasRolePredicate;
import seedu.address.model.person.PersonSortAttribute;
import seedu.address.model.person.Player;
import seedu.address.model.person.Role;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for SortCommand.
 */
public class SortCommandTest {

    private static final Person PLAYER_ZOE = new PersonBuilder()
            .withName("Zoe Yap")
            .withEmail("zoe@example.com")
            .withRole(Role.PLAYER)
            .withTeam("Second Team")
            .withStatus("Unavailable")
            .withPosition("Forward")
            .build();
    private static final Person STAFF_ADAM = new PersonBuilder()
            .withName("Adam Lim")
            .withEmail("adam@example.com")
            .withRole(Role.STAFF)
            .withTeam("First Team")
            .withStatus("Active")
            .build();
    private static final Person PLAYER_BETH = new PersonBuilder()
            .withName("Beth Ong")
            .withEmail("beth@example.com")
            .withRole(Role.PLAYER)
            .withTeam("First Team")
            .withStatus("Active")
            .withPosition("Defender")
            .build();
    private static final Person STAFF_MIA = new PersonBuilder()
            .withName("Mia Koh")
            .withEmail("mia@example.com")
            .withRole(Role.STAFF)
            .withTeam("Second Team")
            .withStatus("Unavailable")
            .build();

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        AddressBook addressBook = new AddressBook();
        Player playerZoe = (Player) PLAYER_ZOE;
        playerZoe.getStats().setGoalsScored(5);
        playerZoe.getStats().setMatchesWon(4);
        playerZoe.getStats().setMatchesLost(1);

        Player playerBeth = (Player) PLAYER_BETH;
        playerBeth.getStats().setGoalsScored(2);
        playerBeth.getStats().setMatchesWon(1);
        playerBeth.getStats().setMatchesLost(3);
        addressBook.addPerson(PLAYER_ZOE);
        addressBook.addPerson(STAFF_MIA);
        addressBook.addPerson(PLAYER_BETH);
        addressBook.addPerson(STAFF_ADAM);

        model = new ModelManager(addressBook, new UserPrefs());
        expectedModel = new ModelManager(addressBook, new UserPrefs());
    }

    @Test
    public void execute_sortAllPersonsByName_success() {
        SortCommand command = new SortCommand(Model.PREDICATE_SHOW_ALL_PERSONS,
                PersonSortAttribute.NAME, "persons", false);

        expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        expectedModel.updateSortedPersonListComparator(PersonSortAttribute.NAME.getComparator());

        assertCommandSuccess(command, model,
                String.format(SortCommand.MESSAGE_SUCCESS, "persons", "name", SortCommand.ORDER_ASCENDING),
                expectedModel);
        assertEquals(List.of(STAFF_ADAM, PLAYER_BETH, STAFF_MIA, PLAYER_ZOE), model.getFilteredPersonList());
    }

    @Test
    public void execute_sortPlayersByEmail_success() {
        SortCommand command = new SortCommand(
                new PersonHasRolePredicate(Role.PLAYER), PersonSortAttribute.EMAIL, "players", false);

        expectedModel.updateFilteredPersonList(new PersonHasRolePredicate(Role.PLAYER));
        expectedModel.updateSortedPersonListComparator(PersonSortAttribute.EMAIL.getComparator());

        assertCommandSuccess(command, model,
                String.format(SortCommand.MESSAGE_SUCCESS, "players", "email", SortCommand.ORDER_ASCENDING),
                expectedModel);
        assertEquals(List.of(PLAYER_BETH, PLAYER_ZOE), model.getFilteredPersonList());
    }

    @Test
    public void execute_sortAllPersonsByNameDescending_success() {
        SortCommand command = new SortCommand(Model.PREDICATE_SHOW_ALL_PERSONS,
                PersonSortAttribute.NAME, "persons", true);

        expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        expectedModel.updateSortedPersonListComparator(PersonSortAttribute.NAME.getComparator().reversed());

        assertCommandSuccess(command, model,
                String.format(SortCommand.MESSAGE_SUCCESS, "persons", "name", SortCommand.ORDER_DESCENDING),
                expectedModel);
        assertEquals(List.of(PLAYER_ZOE, STAFF_MIA, PLAYER_BETH, STAFF_ADAM), model.getFilteredPersonList());
    }

    @Test
    public void execute_sortAllPersonsByTeam_success() {
        SortCommand command = new SortCommand(Model.PREDICATE_SHOW_ALL_PERSONS,
                PersonSortAttribute.TEAM, "persons", false);

        expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        expectedModel.updateSortedPersonListComparator(PersonSortAttribute.TEAM.getComparator());

        assertCommandSuccess(command, model,
                String.format(SortCommand.MESSAGE_SUCCESS, "persons", "team", SortCommand.ORDER_ASCENDING),
                expectedModel);
        assertEquals(List.of(STAFF_ADAM, PLAYER_BETH, STAFF_MIA, PLAYER_ZOE), model.getFilteredPersonList());
    }

    @Test
    public void execute_sortAllPersonsByStatus_success() {
        SortCommand command = new SortCommand(Model.PREDICATE_SHOW_ALL_PERSONS,
                PersonSortAttribute.STATUS, "persons", false);

        expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        expectedModel.updateSortedPersonListComparator(PersonSortAttribute.STATUS.getComparator());

        assertCommandSuccess(command, model,
                String.format(SortCommand.MESSAGE_SUCCESS, "persons", "status", SortCommand.ORDER_ASCENDING),
                expectedModel);
        assertEquals(List.of(STAFF_ADAM, PLAYER_BETH, STAFF_MIA, PLAYER_ZOE), model.getFilteredPersonList());
    }

    @Test
    public void execute_sortPlayersByPosition_success() {
        SortCommand command = new SortCommand(
                new PersonHasRolePredicate(Role.PLAYER), PersonSortAttribute.POSITION, "players", false);

        expectedModel.updateFilteredPersonList(new PersonHasRolePredicate(Role.PLAYER));
        expectedModel.updateSortedPersonListComparator(PersonSortAttribute.POSITION.getComparator());

        assertCommandSuccess(command, model,
                String.format(SortCommand.MESSAGE_SUCCESS, "players", "position", SortCommand.ORDER_ASCENDING),
                expectedModel);
        assertEquals(List.of(PLAYER_BETH, PLAYER_ZOE), model.getFilteredPersonList());
    }

    @Test
    public void execute_sortPlayersByGoals_success() {
        SortCommand command = new SortCommand(
                new PersonHasRolePredicate(Role.PLAYER), PersonSortAttribute.GOALS, "players", false);

        expectedModel.updateFilteredPersonList(new PersonHasRolePredicate(Role.PLAYER));
        expectedModel.updateSortedPersonListComparator(PersonSortAttribute.GOALS.getComparator());

        assertCommandSuccess(command, model,
                String.format(SortCommand.MESSAGE_SUCCESS, "players", "goals", SortCommand.ORDER_ASCENDING),
                expectedModel);
        assertEquals(List.of(PLAYER_BETH, PLAYER_ZOE), model.getFilteredPersonList());
    }

    @Test
    public void execute_sortAllPersonsByWins_success() {
        SortCommand command = new SortCommand(Model.PREDICATE_SHOW_ALL_PERSONS,
                PersonSortAttribute.WINS, "persons", false);

        expectedModel.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        expectedModel.updateSortedPersonListComparator(PersonSortAttribute.WINS.getComparator());

        assertCommandSuccess(command, model,
                String.format(SortCommand.MESSAGE_SUCCESS, "persons", "wins", SortCommand.ORDER_ASCENDING),
                expectedModel);
        assertEquals(List.of(STAFF_ADAM, STAFF_MIA, PLAYER_BETH, PLAYER_ZOE), model.getFilteredPersonList());
    }

    @Test
    public void execute_sortPlayersByLossesDescending_success() {
        SortCommand command = new SortCommand(
                new PersonHasRolePredicate(Role.PLAYER), PersonSortAttribute.LOSSES, "players", true);

        expectedModel.updateFilteredPersonList(new PersonHasRolePredicate(Role.PLAYER));
        expectedModel.updateSortedPersonListComparator(PersonSortAttribute.LOSSES.getComparator().reversed());

        assertCommandSuccess(command, model,
                String.format(SortCommand.MESSAGE_SUCCESS, "players", "losses", SortCommand.ORDER_DESCENDING),
                expectedModel);
        assertEquals(List.of(PLAYER_BETH, PLAYER_ZOE), model.getFilteredPersonList());
    }

    @Test
    public void equals() {
        SortCommand sortByName = new SortCommand(Model.PREDICATE_SHOW_ALL_PERSONS,
                PersonSortAttribute.NAME, "persons", false);
        SortCommand sortByNameCopy =
                new SortCommand(Model.PREDICATE_SHOW_ALL_PERSONS, PersonSortAttribute.NAME, "persons", false);
        SortCommand sortPlayersByEmail =
                new SortCommand(new PersonHasRolePredicate(Role.PLAYER), PersonSortAttribute.EMAIL, "players", false);
        SortCommand sortByNameDescending =
                new SortCommand(Model.PREDICATE_SHOW_ALL_PERSONS, PersonSortAttribute.NAME, "persons", true);

        assertTrue(sortByName.equals(sortByName));
        assertTrue(sortByName.equals(sortByNameCopy));
        assertFalse(sortByName.equals(1));
        assertFalse(sortByName.equals(null));
        assertFalse(sortByName.equals(sortPlayersByEmail));
        assertFalse(sortByName.equals(sortByNameDescending));
    }

    @Test
    public void toStringMethod() {
        SortCommand sortCommand = new SortCommand(Model.PREDICATE_SHOW_ALL_PERSONS,
                PersonSortAttribute.NAME, "persons", true);
        String expected = SortCommand.class.getCanonicalName()
                + "{predicate=" + Model.PREDICATE_SHOW_ALL_PERSONS
                + ", attribute=" + PersonSortAttribute.NAME
                + ", scopeDescription=persons, isDescending=true}";
        assertEquals(expected, sortCommand.toString());
    }
}
