package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.event.Date;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventName;
import seedu.address.model.event.EventPlayerList;
import seedu.address.model.event.EventType;
import seedu.address.model.person.Person;
import seedu.address.model.person.Position;
import seedu.address.model.person.Role;
import seedu.address.model.person.Status;
import seedu.address.model.person.Team;
import seedu.address.testutil.PersonBuilder;

/**
 * A class to test the training command.
 */
public class TrainingCommandTest {

    @Test
    public void execute_trainingAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingTrainingAdded modelStub = new ModelStubAcceptingTrainingAdded();

        // Setup: A player exists in the model
        Person validPlayer = new PersonBuilder().withName("Alex Yeoh").withRole(Role.PLAYER).build();
        modelStub.addPerson(validPlayer);

        EventName name = new EventName("Drills");
        Date date = new Date("2026-04-15 1600");
        List<String> players = List.of("Alex Yeoh");

        CommandResult commandResult = new TrainingCommand(name, date,
                null, null, null, players).execute(modelStub);

        assertEquals(String.format(TrainingCommand.MESSAGE_SUCCESS,
                        "Training: Drills; Date: 15 April 2026, 4:00 PM; Players: Alex Yeoh"),
                commandResult.getFeedbackToUser());
        assertEquals(
                List.of(Event.createEvent(name, date, EventType.TRAINING, new EventPlayerList(Set.of(validPlayer)))),
                modelStub.eventsAdded);
    }

    @Test
    public void execute_personNotFound_throwsCommandException() throws CommandException {
        // Model has no persons
        ModelStubAcceptingTrainingAdded modelStub = new ModelStubAcceptingTrainingAdded();

        TrainingCommand trainingCommand =
                new TrainingCommand(new EventName("Drills"), new Date("2026-04-15 1600"),
                        null, null, null, List.of("Unknown Player"));

        assertThrows(CommandException.class,
                String.format(TrainingCommand.MESSAGE_PERSON_DOES_NOT_EXIST,
                        "Unknown Player"), () -> trainingCommand.execute(modelStub));
    }

    @Test
    public void execute_notAPlayer_throwsCommandException() {
        ModelStub modelStub = new ModelStubAcceptingTrainingAdded();
        Person coach = new PersonBuilder().withName("Coach Bob").withRole(Role.STAFF).build();
        modelStub.addPerson(coach);

        TrainingCommand trainingCommand =
                new TrainingCommand(new EventName("Drills"), new Date("2026-04-15 1600"),
                        null, null, null, List.of("Coach Bob"));

        assertThrows(CommandException.class,
                String.format(TrainingCommand.MESSAGE_NOT_A_PLAYER,
                        "Coach Bob"), () -> trainingCommand.execute(modelStub));
    }

    /**
     * A default model stub that has all methods throwing an error.
     */
    private abstract static class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("Should not be called.");
        }

        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("Should not be called.");
        }

        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("Should not be called.");
        }

        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("Should not be called.");
        }

        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("Should not be called.");
        }

        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("Should not be called.");
        }

        @Override
        public void setAddressBook(ReadOnlyAddressBook addressBook) {
            throw new AssertionError("Should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("Should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("Should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("Should not be called.");
        }

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("Should not be called.");
        }

        @Override
        public void setPerson(Person target, Person editedPerson) {
            throw new AssertionError("Should not be called.");
        }

        @Override
        public boolean hasTeam(Team team) {
            throw new AssertionError("Should not be called.");
        }

        @Override
        public void addTeam(Team team) {
            throw new AssertionError("Should not be called.");
        }

        @Override
        public void deleteTeam(Team team) {
            throw new AssertionError("Should not be called.");
        }

        @Override
        public void setTeam(Team oldTeam, Team newTeam) {
            throw new AssertionError("Should not be called.");
        }

        @Override
        public boolean hasEvent(Event event) {
            throw new AssertionError("Should not be called.");
        }

        @Override
        public void deleteEvent(Event target) {
            throw new AssertionError("Should not be called.");
        }

        @Override
        public void addEvent(Event event) {
            throw new AssertionError("Should not be called.");
        }

        @Override
        public void setEvent(Event target, Event editedEvent) {
            throw new AssertionError("Should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("Should not be called.");
        }

        @Override
        public List<Person> getPersonsMatching(Predicate<Person> predicate) {
            throw new AssertionError("Should not be called.");
        }

        @Override
        public ObservableList<Event> getEventList() {
            throw new AssertionError("Should not be called.");
        }

        @Override
        public ObservableList<Team> getTeamList() {
            throw new AssertionError("Should not be called.");
        }

        @Override
        public boolean hasPosition(Position position) {
            throw new AssertionError("Should not be called.");
        }

        @Override
        public void addPosition(Position position) {
            throw new AssertionError("Should not be called.");
        }

        @Override
        public void deletePosition(Position position) {
            throw new AssertionError("Should not be called.");
        }

        @Override
        public void setPosition(Position oldPosition, Position newPosition) {
            throw new AssertionError("Should not be called.");
        }

        @Override
        public ObservableList<Position> getPositionList() {
            throw new AssertionError("Should not be called.");
        }

        @Override
        public ObservableList<Status> getStatusList() {
            throw new AssertionError("Should not be called.");
        }

        @Override
        public boolean hasStatus(Status status) {
            throw new AssertionError("Should not be called.");
        }

        @Override
        public void addStatus(Status status) {
            throw new AssertionError("Should not be called.");
        }

        @Override
        public void deleteStatus(Status status) {
            throw new AssertionError("Should not be called.");
        }

        @Override
        public void setStatus(Status oldStatus, Status newStatus) {
            throw new AssertionError("Should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("Should not be called.");
        }

        @Override
        public void updateSortedPersonListComparator(Comparator<Person> comparator) {
            throw new AssertionError("Should not be called.");
        }

        @Override
        public void cascadeEditedPersonToEvent(Person personToEdit, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String getAttendanceReport() {
            throw new AssertionError("Should not be called.");
        }
    }

    /**
     * A Model stub that always accepts the training being added.
     */
    private static class ModelStubAcceptingTrainingAdded extends ModelStub {
        final ArrayList<Event> eventsAdded = new ArrayList<>();
        final AddressBook addressBook = new AddressBook();

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return addressBook;
        }

        @Override
        public void addPerson(Person person) {
            addressBook.addPerson(person);
        }

        @Override
        public boolean hasEvent(Event event) {
            return eventsAdded.stream().anyMatch(event::isSameEvent);
        }

        @Override
        public void addEvent(Event event) {
            eventsAdded.add(event);
        }
    }
}
