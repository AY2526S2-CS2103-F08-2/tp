package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.PLAYER_AMY;

import java.nio.file.Path;
import java.util.ArrayList;
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
import seedu.address.model.person.Position;
import seedu.address.model.person.Role;
import seedu.address.model.person.Status;
import seedu.address.model.person.Team;
import seedu.address.testutil.PersonBuilder;

public class AddCommandTest {

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddCommand(null));
    }

    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Person validPerson = new PersonBuilder().build();

        CommandResult commandResult = new AddCommand(validPerson).execute(modelStub);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS,
                        validPerson.getRole().toString(), Messages.format(validPerson)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validPerson), modelStub.personsAdded);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() {
        Person validPerson = new PersonBuilder().build();
        AddCommand addCommand = new AddCommand(validPerson);
        ModelStub modelStub = new ModelStubWithPerson(validPerson);

        assertThrows(CommandException.class, AddCommand.MESSAGE_DUPLICATE_PERSON, () -> addCommand.execute(modelStub));
    }

    @Test
    public void execute_teamNotInCatalog_throwsCommandException() {
        Person person = new PersonBuilder().withTeam("Ghost Team").build();
        AddCommand addCommand = new AddCommand(person);
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        modelStub.hasTeam = false;

        assertThrows(CommandException.class,
                AddCommand.MESSAGE_TEAM_NOT_IN_CATALOG, () -> addCommand.execute(modelStub));
    }

    @Test
    public void execute_statusNotInCatalog_throwsCommandException() {
        Person person = new PersonBuilder().withStatus("Ghost Status").build();
        AddCommand addCommand = new AddCommand(person);
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        modelStub.hasStatus = false;

        assertThrows(CommandException.class,
                AddCommand.MESSAGE_STATUS_NOT_IN_CATALOG, () -> addCommand.execute(modelStub));
    }

    @Test
    public void execute_positionNotInCatalog_throwsCommandException() {
        Person person = new PersonBuilder().withPosition("Ghost Position").build();
        AddCommand addCommand = new AddCommand(person);
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        modelStub.hasPosition = false;

        assertThrows(CommandException.class,
                AddCommand.MESSAGE_POSITION_NOT_IN_CATALOG, () -> addCommand.execute(modelStub));
    }

    @Test
    public void execute_staffWithNonDefaultPosition_throwsCommandException() {
        Person person = new PersonBuilder().withRole(Role.STAFF).withPosition("Forward").build();
        AddCommand addCommand = new AddCommand(person);
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();

        assertThrows(CommandException.class,
                AddCommand.MESSAGE_POSITION_NOT_APPLICABLE_TO_STAFF, () -> addCommand.execute(modelStub));
    }

    @Test
    public void execute_attributeCaseDiffersFromCatalog_usesCatalogCasing() throws Exception {
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Person person = new PersonBuilder()
                .withTeam("first team")
                .withStatus("active")
                .withPosition("forward")
                .build();

        new AddCommand(person).execute(modelStub);
        Person added = modelStub.personsAdded.get(0);

        assertEquals("First Team", added.getTeam().value);
        assertEquals("Active", added.getStatus().value);
        assertEquals("Forward", added.getPosition().value);
    }

    @Test
    public void equals() {
        Person amy = new PersonBuilder().withName("Amy Tan").build();
        Person ben = new PersonBuilder().withName("Ben Lim").build();
        AddCommand addAmyCommand = new AddCommand(amy);
        AddCommand addBenCommand = new AddCommand(ben);

        // same object -> returns true
        assertTrue(addAmyCommand.equals(addAmyCommand));

        // same values -> returns true
        AddCommand addAmyCommandCopy = new AddCommand(amy);
        assertTrue(addAmyCommand.equals(addAmyCommandCopy));

        // different types -> returns false
        assertFalse(addAmyCommand.equals(1));

        // null -> returns false
        assertFalse(addAmyCommand.equals(null));

        // different person -> returns false
        assertFalse(addAmyCommand.equals(addBenCommand));
    }

    @Test
    public void toStringMethod() {
        AddCommand addCommand = new AddCommand(PLAYER_AMY);
        String expected = AddCommand.class.getCanonicalName() + "{toAdd=" + PLAYER_AMY + "}";
        assertEquals(expected, addCommand.toString());
    }

    /**
     * A default model stub that have all of the methods failing.
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
            return true;
        }

        @Override
        public void addTeam(Team team) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteTeam(Team team) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setTeam(Team oldTeam, Team newTeam) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasStatus(Status status) {
            return true;
        }

        @Override
        public void addStatus(Status status) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteStatus(Status status) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setStatus(Status oldStatus, Status newStatus) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasEvent(Event event) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteEvent(Event target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addEvent(Event event) {
            throw new AssertionError("This method should not be called.");
        };

        @Override
        public void setEvent(Event target, Event editedEvent) {
            throw new AssertionError("This method should not be called.");
        };

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Team> getTeamList() {
            return FXCollections.observableArrayList(
                    new Team(Team.DEFAULT_UNASSIGNED_TEAM),
                    new Team("First Team"),
                    new Team("Second Team"));
        }

        @Override
        public boolean hasPosition(Position position) {
            return true;
        }

        @Override
        public void addPosition(Position position) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePosition(Position position) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setPosition(Position oldPosition, Position newPosition) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Position> getPositionList() {
            return FXCollections.observableArrayList(
                    new Position(Position.DEFAULT_UNASSIGNED_POSITION),
                    new Position("Forward"),
                    new Position("Defender"));
        }

        @Override
        public ObservableList<Status> getStatusList() {
            return FXCollections.observableArrayList(
                    new Status(Status.DEFAULT_UNKNOWN_STATUS),
                    new Status("Active"),
                    new Status("Unavailable"));
        }

        @Override
        public ObservableList<Event> getEventList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public List<Person> getPersonsMatching(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
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
    }

    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithPerson extends ModelStub {
        private final Person person;

        ModelStubWithPerson(Person person) {
            requireNonNull(person);
            this.person = person;
        }

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return this.person.isSamePerson(person);
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingPersonAdded extends ModelStub {
        final ArrayList<Person> personsAdded = new ArrayList<>();
        private boolean hasTeam = true;
        private boolean hasStatus = true;
        private boolean hasPosition = true;

        @Override
        public boolean hasPerson(Person person) {
            requireNonNull(person);
            return personsAdded.stream().anyMatch(person::isSamePerson);
        }

        @Override
        public void addPerson(Person person) {
            requireNonNull(person);
            personsAdded.add(person);
        }

        @Override
        public boolean hasTeam(Team team) {
            requireNonNull(team);
            return hasTeam;
        }

        @Override
        public boolean hasStatus(Status status) {
            requireNonNull(status);
            return hasStatus;
        }

        @Override
        public boolean hasPosition(Position position) {
            requireNonNull(position);
            return hasPosition;
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
