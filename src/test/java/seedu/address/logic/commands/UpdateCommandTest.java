package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.PLAYER_AMY;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;
import seedu.address.model.person.Player;
import seedu.address.model.person.PlayerStats;
import seedu.address.model.person.Position;
import seedu.address.model.person.Role;
import seedu.address.model.person.StatField;
import seedu.address.model.person.Status;
import seedu.address.model.person.Team;
import seedu.address.testutil.PersonBuilder;

public class UpdateCommandTest {

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new UpdateCommand(null, StatField.GOALS, 5));
    }

    @Test
    public void constructor_nullStat_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new UpdateCommand(INDEX_FIRST_PERSON, null, 5));
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Player player = (Player) new PersonBuilder(PLAYER_AMY).build();
        ModelStubWithFilteredList modelStub = new ModelStubWithFilteredList(player);

        UpdateCommand command = new UpdateCommand(INDEX_SECOND_PERSON, StatField.WINS, 2);

        assertThrows(CommandException.class,
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, () -> command.execute(modelStub));
    }

    @Test
    public void execute_nonPlayer_throwsCommandException() {
        Person staff = new PersonBuilder().withRole(Role.STAFF).build();
        ModelStubWithFilteredList modelStub = new ModelStubWithFilteredList(staff);

        UpdateCommand command = new UpdateCommand(INDEX_FIRST_PERSON, StatField.WINS, 2);

        assertThrows(CommandException.class,
                UpdateCommand.MESSAGE_NOT_PLAYER, () -> command.execute(modelStub));
    }

    @Test
    public void execute_validIndexPlayer_incrementsStat() throws Exception {
        Player player = (Player) new PersonBuilder(PLAYER_AMY).build();
        ModelStubWithFilteredList modelStub = new ModelStubWithFilteredList(player);

        int oldWins = player.getStats().getMatchesWon();
        int increment = 2;
        int expectedWins = oldWins + increment;

        UpdateCommand command = new UpdateCommand(INDEX_FIRST_PERSON, StatField.WINS, increment);
        CommandResult commandResult = command.execute(modelStub);

        // command uses old player reference for the message, fetch updated from stub for stat assertion
        Player updatedPlayer = (Player) modelStub.filteredPersons.get(0);

        assertEquals(String.format(UpdateCommand.MESSAGE_SET_PLAYER_SUCCESS,
                        Messages.format(player), StatField.WINS, oldWins, expectedWins, "+" + increment),
                commandResult.getFeedbackToUser());
        assertEquals(expectedWins, updatedPlayer.getStats().getMatchesWon());
    }

    @Test
    public void execute_statOverflow_throwsCommandException() {
        Player player = (Player) new PersonBuilder(PLAYER_AMY).build();
        // set wins to MAX_VALUE first so any increment will overflow
        PlayerStats stats = new PlayerStats();
        stats.setMatchesWon(Integer.MAX_VALUE);
        Player maxPlayer = new Player(player, stats);
        ModelStubWithFilteredList modelStub = new ModelStubWithFilteredList(maxPlayer);

        UpdateCommand command = new UpdateCommand(INDEX_FIRST_PERSON, StatField.WINS, 1);

        assertThrows(CommandException.class,
                UpdateCommand.MESSAGE_STAT_OVERFLOW, () -> command.execute(modelStub));
    }

    @Test
    public void execute_validIndexPlayer_decrementStat() throws Exception {
        Player player = (Player) new PersonBuilder(PLAYER_AMY).build();
        ModelStubWithFilteredList modelStub = new ModelStubWithFilteredList(player);

        player.getStats().setMatchesWon(10);

        int oldWins = player.getStats().getMatchesWon();
        int increment = -2;
        int expectedWins = oldWins + increment;

        UpdateCommand command = new UpdateCommand(INDEX_FIRST_PERSON, StatField.WINS, increment);
        CommandResult commandResult = command.execute(modelStub);

        // command uses old player reference for the message, fetch updated from stub for stat assertion
        Player updatedPlayer = (Player) modelStub.filteredPersons.get(0);

        assertEquals(String.format(UpdateCommand.MESSAGE_SET_PLAYER_SUCCESS,
                        Messages.format(player), StatField.WINS, oldWins, expectedWins, increment), // without plus
                commandResult.getFeedbackToUser());
        assertEquals(expectedWins, updatedPlayer.getStats().getMatchesWon());
        assertFalse(modelStub.updateFilteredPersonListCalled);
        assertTrue(modelStub.setPersonCalled);
    }

    @Test
    public void equals() {
        UpdateCommand updateFirstGoals10 = new UpdateCommand(INDEX_FIRST_PERSON, StatField.GOALS, 10);
        UpdateCommand updateFirstGoals10Copy = new UpdateCommand(INDEX_FIRST_PERSON, StatField.GOALS, 10);
        UpdateCommand updateSecondGoals10 = new UpdateCommand(INDEX_SECOND_PERSON, StatField.GOALS, 10);
        UpdateCommand updateFirstWins10 = new UpdateCommand(INDEX_FIRST_PERSON, StatField.WINS, 10);
        UpdateCommand updateFirstGoals5 = new UpdateCommand(INDEX_FIRST_PERSON, StatField.GOALS, 5);

        assertTrue(updateFirstGoals10.equals(updateFirstGoals10));
        assertTrue(updateFirstGoals10.equals(updateFirstGoals10Copy));

        assertFalse(updateFirstGoals10.equals(1));
        assertFalse(updateFirstGoals10.equals(null));
        assertFalse(updateFirstGoals10.equals(updateSecondGoals10));
        assertFalse(updateFirstGoals10.equals(updateFirstWins10));
        assertFalse(updateFirstGoals10.equals(updateFirstGoals5));
    }

    @Test
    public void toStringMethod() {
        UpdateCommand updateCommand = new UpdateCommand(INDEX_FIRST_PERSON, StatField.GOALS, 10);
        String expected = UpdateCommand.class.getCanonicalName()
                + "{index=" + INDEX_FIRST_PERSON + ", stat=" + StatField.GOALS + ", value=10}";
        assertEquals(expected, updateCommand.toString());
    }

    /**
     * A default model stub that has all methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasTeam(Team team) {
            return false;
        }

        @Override
        public void addTeam(Team team) {

        }

        @Override
        public void deleteTeam(Team team) {

        }

        @Override
        public void setTeam(Team oldTeam, Team newTeam) {

        }

        @Override
        public boolean hasEvent(Event event) {
            return false;
        }

        @Override
        public void deleteEvent(Event target) {

        }

        @Override
        public void addEvent(Event event) {

        }

        @Override
        public void setEvent(Event target, Event editedEvent) {

        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public List<Person> getPersonsMatching(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Event> getEventList() {
            return null;
        }

        @Override
        public ObservableList<Team> getTeamList() {
            return null;
        }

        @Override
        public boolean hasPosition(Position position) {
            return false;
        }

        @Override
        public void addPosition(Position position) {

        }

        @Override
        public void deletePosition(Position position) {

        }

        @Override
        public void setPosition(Position oldPosition, Position newPosition) {

        }

        @Override
        public ObservableList<Position> getPositionList() {
            return null;
        }

        @Override
        public ObservableList<Status> getStatusList() {
            return null;
        }

        @Override
        public boolean hasStatus(Status status) {
            return false;
        }

        @Override
        public void addStatus(Status status) {

        }

        @Override
        public void deleteStatus(Status status) {

        }

        @Override
        public void setStatus(Status oldStatus, Status newStatus) {

        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateSortedPersonListComparator(Comparator<Person> comparator) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String getAttendanceReport() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void cascadeEditedPersonToEvent(Person personToEdit, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub with a controllable filtered person list.
     */
    private class ModelStubWithFilteredList extends ModelStub {
        private final ObservableList<Person> filteredPersons = FXCollections.observableArrayList();
        private boolean updateFilteredPersonListCalled = false;
        private boolean setPersonCalled = false;

        ModelStubWithFilteredList(Person... persons) {
            filteredPersons.addAll(Arrays.asList(persons));
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return filteredPersons;
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            int index = filteredPersons.indexOf(target);
            filteredPersons.set(index, editedPerson);
            setPersonCalled = true;
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            requireNonNull(predicate);
            updateFilteredPersonListCalled = true;
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}
