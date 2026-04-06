package seedu.address.model.event;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.model.person.Role;
import seedu.address.model.person.UniquePersonList;

/**
 * Represents a collection of Players that are part of a Match. Uses
 * a UniquePersonList to ensure all Players are unique.
 * Guarantees: all Persons are Players
 */
public class EventPlayerList implements Iterable<Person> {
    public static final String MESSAGE_CONSTRAINTS = "%s is not a player!";
    public static final String MESSAGE_NOT_FOUND = "%s is not part of the event!";

    private final UniquePersonList uniquePersonList = new UniquePersonList();

    public EventPlayerList(Set<Person> personSet) {
        personSet.forEach(this::add);
    }

    /**
     * Returns true if the player list contains an equivalent player as the given argument.
     */
    public boolean contains(Person toCheck) {
        requireNonNull(toCheck);

        if (toCheck.getRole() != Role.PLAYER) {
            throw new IllegalArgumentException(String.format(MESSAGE_CONSTRAINTS, toCheck));
        }

        return uniquePersonList.contains(toCheck);
    }

    /**
     * Adds a person to the UniquePersonList. Person must be a player and cannot exist in the list.
     * @param person A valid person.
     */
    public void add(Person person) {
        requireNonNull(person);

        if (person.getRole() != Role.PLAYER) {
            throw new IllegalArgumentException(String.format(MESSAGE_CONSTRAINTS, person));
        }

        uniquePersonList.add(person);
    }

    /**
     * Removes a person from the UniquePersonList. Person must exist in the list.
     * @param person
     */
    public void remove(Person person) {
        requireNonNull(person);

        if (!uniquePersonList.contains(person)) {
            throw new IllegalArgumentException(String.format(MESSAGE_NOT_FOUND, person));
        }

        uniquePersonList.remove(person);
    }

    public ObservableList<Person> asUnmodifiableObservableList() {
        return uniquePersonList.asUnmodifiableObservableList();
    }

    /**
     * Gets player names in a set. Used for editing events.
     */
    public Set<String> getPlayerNames() {
        Set<String> playerNames = new HashSet<>();
        for (Person person : uniquePersonList) {
            playerNames.add(person.getName().toString());
        }
        return playerNames;
    }

    @Override
    public Iterator<Person> iterator() {
        return uniquePersonList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EventPlayerList)) {
            return false;
        }

        EventPlayerList otherEventPlayerList = (EventPlayerList) other;
        return new HashSet<>(uniquePersonList.asUnmodifiableObservableList())
                .equals(new HashSet<>(otherEventPlayerList.asUnmodifiableObservableList()));
    }

    @Override
    public int hashCode() {
        return uniquePersonList.hashCode();
    }

    @Override
    public String toString() {
        return uniquePersonList.toString();
    }
}
