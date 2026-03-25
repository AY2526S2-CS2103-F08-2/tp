package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.nio.file.Path;
import java.util.List;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.EventEditCommand;
import seedu.address.logic.commands.EventEditCommand.EditEventDescriptor;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventType;
import seedu.address.model.person.Person;
import seedu.address.model.person.Player;
import seedu.address.model.person.Position;
import seedu.address.model.person.Role;
import seedu.address.model.person.Status;
import seedu.address.model.person.Team;

/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Person> filteredPersons;
    private final FilteredList<Event> eventList;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        eventList = new FilteredList<>(this.addressBook.getEventList());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    //=========== UserPrefs ==================================================================================

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    //=========== AddressBook ================================================================================

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return addressBook.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) throws CommandException {
        addressBook.removePerson(target);
        for (Event e : addressBook.getEventList()) {
            if (e.getEventPlayerList().contains((Player) target)) {
                EditEventDescriptor descriptor = new EditEventDescriptor();
                descriptor.setEventType(e.getEventType());
                descriptor.setEventName(e.getEventName());
                descriptor.setEventDate(e.getEventDate());
                Set<String> updatedPlayerNames = new HashSet<>(e.getEventPlayerList().getPlayerNames());
                updatedPlayerNames.remove(target.getName().toString());
                descriptor.setEventPlayerNames(updatedPlayerNames);
                Event editedEvent = EventEditCommand.createEditedEvent(e, descriptor, this);
                this.setEvent(e, editedEvent);
            }
        }

    }

    @Override
    public void addPerson(Person person) {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void setPerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        addressBook.setPerson(target, editedPerson);
    }

    @Override
    public boolean hasEvent(Event event) {
        requireNonNull(event);
        return addressBook.hasEvent(event);
    }

    @Override
    public boolean hasTeam(Team team) {
        requireNonNull(team);
        return addressBook.hasTeam(team);
    }

    @Override
    public void addTeam(Team team) {
        requireNonNull(team);
        addressBook.addTeam(team);
    }

    @Override
    public void deleteTeam(Team team) {
        requireNonNull(team);
        addressBook.removeTeam(team);
    }

    @Override
    public void setTeam(Team oldTeam, Team newTeam) {
        requireAllNonNull(oldTeam, newTeam);
        addressBook.setTeam(oldTeam, newTeam);
    }

    @Override
    public void deleteEvent(Event target) {
        addressBook.removeEvent(target);
    }

    @Override
    public void addEvent(Event event) {
        addressBook.addEvent(event);
    }

    @Override
    public void setEvent(Event target, Event editedEvent) {
        requireAllNonNull(target, editedEvent);

        addressBook.setEvent(target, editedEvent);
    }

    @Override
    public String getAttendanceReport() {
        StringBuilder sb = new StringBuilder();
        ObservableList<Event> trainingList =
                addressBook.getEventList().filtered(e -> e.getEventType() == EventType.TRAINING);

        for (Person p : addressBook.getPersonList().filtered(p -> p.getRole() == Role.PLAYER)) {
            long appearances = trainingList.stream().filter(e -> e.getEventPlayerList().contains((Player) p)).count();
            double rate = (double) appearances / trainingList.size() * 100;
            sb.append(String.format("%s: %.1f%%\n", p.getName(), rate));
        }
        return sb.toString();
    }
    //=========== Match List Accessors =======================================================================

    /**
     * Returns an unmodifiable view of the list of {@code Match} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Event> getEventList() {
        return eventList;
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return filteredPersons;
    }

    @Override
    public List<Person> getPersonsMatching(Predicate<Person> predicate) {
        requireNonNull(predicate);
        return addressBook.getPersonList().stream()
                .filter(predicate)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public ObservableList<Team> getTeamList() {
        return addressBook.getTeamList();
    }

    @Override
    public boolean hasPosition(Position position) {
        requireNonNull(position);
        return addressBook.hasPosition(position);
    }

    @Override
    public void addPosition(Position position) {
        requireNonNull(position);
        addressBook.addPosition(position);
    }

    @Override
    public void deletePosition(Position position) {
        requireNonNull(position);
        addressBook.removePosition(position);
    }

    @Override
    public void setPosition(Position oldPosition, Position newPosition) {
        requireAllNonNull(oldPosition, newPosition);
        addressBook.setPosition(oldPosition, newPosition);
    }

    @Override
    public ObservableList<Position> getPositionList() {
        return addressBook.getPositionList();
    }

    @Override
    public ObservableList<Status> getStatusList() {
        return addressBook.getStatusList();
    }

    @Override
    public boolean hasStatus(Status status) {
        requireNonNull(status);
        return addressBook.hasStatus(status);
    }

    @Override
    public void addStatus(Status status) {
        requireNonNull(status);
        addressBook.addStatus(status);
    }

    @Override
    public void deleteStatus(Status status) {
        requireNonNull(status);
        addressBook.removeStatus(status);
    }

    @Override
    public void setStatus(Status oldStatus, Status newStatus) {
        requireAllNonNull(oldStatus, newStatus);
        addressBook.setStatus(oldStatus, newStatus);
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return addressBook.equals(otherModelManager.addressBook) && userPrefs.equals(otherModelManager.userPrefs)
               && filteredPersons.equals(otherModelManager.filteredPersons);
    }

}
