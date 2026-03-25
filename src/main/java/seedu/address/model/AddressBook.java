package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.event.Event;
import seedu.address.model.event.UniqueEventList;
import seedu.address.model.person.Person;
import seedu.address.model.person.Position;
import seedu.address.model.person.Status;
import seedu.address.model.person.Team;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.person.UniquePositionList;
import seedu.address.model.person.UniqueStatusList;
import seedu.address.model.person.UniqueTeamList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSamePerson comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;
    private final UniqueEventList events;
    private final UniqueTeamList teams;
    private final UniquePositionList positions;
    private final UniqueStatusList statuses;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
        events = new UniqueEventList();
        teams = new UniqueTeamList();
        positions = new UniquePositionList();
        statuses = new UniqueStatusList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the data in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the person list with {@code persons}.
     * {@code persons} must not contain duplicate persons.
     */
    public void setPersons(List<Person> persons) {
        this.persons.setPersons(persons);
    }

    /**
     * Replaces the contents of the event list with {@code events}.
     * {@code events} must not contain duplicate events.
     */
    public void setEvents(List<Event> events) {
        this.events.setEvents(events);
    }
    /*
     * Replaces the contents of the team catalog with {@code teams}.
     * {@code teams} must not contain duplicates.
     */
    public void setTeams(List<Team> teams) {
        this.teams.setTeams(teams);
    }

    /**
     * Replaces the contents of the position catalog with {@code positions}.
     * {@code positions} must not contain duplicates.
     */
    public void setPositions(List<Position> positions) {
        this.positions.setPositions(positions);
    }

    /**
     * Replaces the contents of the status catalog with {@code statuses}.
     * {@code statuses} must not contain duplicates.
     */
    public void setStatuses(List<Status> statuses) {
        this.statuses.setStatuses(statuses);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setPersons(newData.getPersonList());
        setEvents(newData.getEventList());
        setTeams(newData.getTeamList());
        setPositions(newData.getPositionList());
        setStatuses(newData.getStatusList());
    }

    //// person-level operations

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return persons.contains(person);
    }

    /**
     * Adds a person to the address book.
     * The person must not already exist in the address book.
     */
    public void addPerson(Person p) {
        persons.add(p);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    public void setPerson(Person target, Person editedPerson) {
        requireNonNull(editedPerson);

        persons.setPerson(target, editedPerson);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removePerson(Person key) {
        persons.remove(key);
    }

    //// match-level operations

    /**
     * Returns true if a match with the same identity as {@code match} exists in the address book.
     */
    public boolean hasEvent(Event event) {
        requireNonNull(event);
        return events.contains(event);
    }

    /**
     * Adds an event to the address book.
     * The event must not already exist in the address book.
     */
    public void addEvent(Event e) {
        events.add(e);
    }

    /**
     * Replaces the given event {@code target} in the list with {@code editedEvent}.
     * {@code target} must exist in the address book.
     * The event identity of {@code editedEvent} must not be the same as another existing event in the address book.
     */
    public void setEvent(Event target, Event editedEvent) {
        requireNonNull(editedEvent);

        events.setEvent(target, editedEvent);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeEvent(Event key) {
        events.remove(key);
    }

    //// team-level operations

    /**
     * Returns true if a team with the same identity as {@code team} exists in the address book.
     */
    public boolean hasTeam(Team team) {
        requireNonNull(team);
        return teams.contains(team);
    }

    /**
     * Adds a team to the address book catalog.
     * The team must not already exist.
     */
    public void addTeam(Team team) {
        teams.add(team);
    }

    /**
     * Replaces {@code oldTeam} in the catalog with {@code newTeam}.
     * {@code oldTeam} must exist in the catalog.
     * {@code newTeam} must not duplicate an existing team.
     */
    public void setTeam(Team oldTeam, Team newTeam) {
        requireNonNull(newTeam);
        teams.setTeam(oldTeam, newTeam);
    }

    /**
     * Removes {@code team} from the address book catalog.
     * {@code team} must exist in the catalog.
     */
    public void removeTeam(Team team) {
        teams.remove(team);
    }

    //// position-level operations

    /**
     * Returns true if a position with the same identity as {@code position} exists in the address book.
     */
    public boolean hasPosition(Position position) {
        requireNonNull(position);
        return positions.contains(position);
    }

    /**
     * Adds a position to the address book catalog.
     * The position must not already exist.
     */
    public void addPosition(Position position) {
        positions.add(position);
    }

    /**
     * Removes {@code position} from the address book catalog.
     * {@code position} must exist in the catalog.
     */
    public void removePosition(Position position) {
        positions.remove(position);
    }

    //// status-level operations

    /**
     * Returns true if a status with the same identity as {@code status} exists in the address book.
     */
    public boolean hasStatus(Status status) {
        requireNonNull(status);
        return statuses.contains(status);
    }

    /**
     * Adds a status to the address book catalog.
     * The status must not already exist.
     */
    public void addStatus(Status status) {
        statuses.add(status);
    }

    /**
     * Replaces {@code oldStatus} in the catalog with {@code newStatus}.
     * {@code oldStatus} must exist in the catalog.
     * {@code newStatus} must not duplicate an existing status.
     */
    public void setStatus(Status oldStatus, Status newStatus) {
        requireNonNull(newStatus);
        statuses.setStatus(oldStatus, newStatus);
    }

    /**
     * Removes {@code status} from the address book catalog.
     * {@code status} must exist in the catalog.
     */
    public void removeStatus(Status status) {
        statuses.remove(status);
    }

    //// util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("persons", persons)
                .add("events", events)
                .add("teams", teams)
                .add("positions", positions)
                .add("statuses", statuses)
                .toString();
    }

    @Override
    public ObservableList<Person> getPersonList() {
        return persons.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Event> getEventList() {
        return events.asUnmodifiableObservableList();
    }

    public ObservableList<Team> getTeamList() {
        return teams.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Position> getPositionList() {
        return positions.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Status> getStatusList() {
        return statuses.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddressBook)) {
            return false;
        }

        AddressBook otherAddressBook = (AddressBook) other;
        return persons.equals(otherAddressBook.persons)
                && events.equals(otherAddressBook.events)
                && teams.equals(otherAddressBook.teams)
                && positions.equals(otherAddressBook.positions)
                && statuses.equals(otherAddressBook.statuses);
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(persons, events, teams, positions, statuses);
    }
}
