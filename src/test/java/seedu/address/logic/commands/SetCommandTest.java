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
import seedu.address.model.person.Position;
import seedu.address.model.person.Role;
import seedu.address.model.person.StatField;
import seedu.address.model.person.Status;
import seedu.address.model.person.Team;
import seedu.address.testutil.PersonBuilder;

public class SetCommandTest {

    @Test
    public void constructor_nullIndex_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SetCommand(null, StatField.GOALS, 5));
    }

    @Test
    public void constructor_nullStat_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new SetCommand(INDEX_FIRST_PERSON, null, 5));
    }

    @Test
    public void execute_validIndexPlayer_success() throws Exception {
        Player player = (Player) new PersonBuilder(PLAYER_AMY).build();
        ModelStubWithFilteredList modelStub = new ModelStubWithFilteredList(player);

        int oldGoals = player.getStats().getGoalsScored();
        int newGoals = oldGoals + 1;

        SetCommand command = new SetCommand(INDEX_FIRST_PERSON, StatField.GOALS, newGoals);
        CommandResult commandResult = command.execute(modelStub);

        assertEquals(String.format(SetCommand.MESSAGE_SET_PLAYER_SUCCESS,
                        Messages.format(player), StatField.GOALS, oldGoals, newGoals),
                commandResult.getFeedbackToUser());
        assertEquals(newGoals, player.getStats().getGoalsScored());
        assertTrue(modelStub.updateFilteredPersonListCalled);
    }

    @Test
    public void execute_invalidIndex_throwsCommandException() {
        Player player = (Player) new PersonBuilder(PLAYER_AMY).build();
        ModelStubWithFilteredList modelStub = new ModelStubWithFilteredList(player);

        SetCommand command = new SetCommand(INDEX_SECOND_PERSON, StatField.GOALS, 10);

        assertThrows(CommandException.class,
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, () -> command.execute(modelStub));
    }

    @Test
    public void execute_nonPlayer_throwsCommandException() {
        Person staff = new PersonBuilder().withRole(Role.STAFF).build();
        ModelStubWithFilteredList modelStub = new ModelStubWithFilteredList(staff);

        SetCommand command = new SetCommand(INDEX_FIRST_PERSON, StatField.GOALS, 10);

        assertThrows(CommandException.class,
                SetCommand.MESSAGE_NOT_PLAYER, () -> command.execute(modelStub));
    }

    @Test
    public void equals() {
        SetCommand setFirstGoals10 = new SetCommand(INDEX_FIRST_PERSON, StatField.GOALS, 10);
        SetCommand setFirstGoals10Copy = new SetCommand(INDEX_FIRST_PERSON, StatField.GOALS, 10);
        SetCommand setSecondGoals10 = new SetCommand(INDEX_SECOND_PERSON, StatField.GOALS, 10);
        SetCommand setFirstWins10 = new SetCommand(INDEX_FIRST_PERSON, StatField.WINS, 10);
        SetCommand setFirstGoals5 = new SetCommand(INDEX_FIRST_PERSON, StatField.GOALS, 5);

        assertTrue(setFirstGoals10.equals(setFirstGoals10));
        assertTrue(setFirstGoals10.equals(setFirstGoals10Copy));

        assertFalse(setFirstGoals10.equals(1));
        assertFalse(setFirstGoals10.equals(null));
        assertFalse(setFirstGoals10.equals(setSecondGoals10));
        assertFalse(setFirstGoals10.equals(setFirstWins10));
        assertFalse(setFirstGoals10.equals(setFirstGoals5));
    }

    @Test
    public void toStringMethod() {
        SetCommand setCommand = new SetCommand(INDEX_FIRST_PERSON, StatField.GOALS, 10);
        String expected = SetCommand.class.getCanonicalName()
                + "{index=" + INDEX_FIRST_PERSON + ", stat=" + StatField.GOALS + ", value=10}";
        assertEquals(expected, setCommand.toString());
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
    }

    /**
     * A Model stub with a controllable filtered person list.
     */
    private class ModelStubWithFilteredList extends ModelStub {
        private final ObservableList<Person> filteredPersons = FXCollections.observableArrayList();
        private boolean updateFilteredPersonListCalled = false;

        ModelStubWithFilteredList(Person... persons) {
            filteredPersons.addAll(Arrays.asList(persons));
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            return filteredPersons;
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
