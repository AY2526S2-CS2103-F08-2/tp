package seedu.address.model.match;

import static java.util.Objects.requireNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.model.person.Role;
import seedu.address.model.person.UniquePersonList;

/**
 * Represents a collection of Players that are part of a Match. Uses
 * a UniquePersonList to ensure all Players are unique.
 * Guarantees: all Persons are Players
 */
public class MatchPlayerList implements Iterable<Person> {
    public static final String MESSAGE_CONSTRAINTS = "%s is not a player!";

    private final UniquePersonList uniquePersonList = new UniquePersonList();

    public MatchPlayerList(List<Person> personList) {
        personList.forEach(this::add);
    }

    private UniquePersonList getUniquePersonList() {
        return uniquePersonList;
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

    public ObservableList<Person> asUnmodifiableObservableList() {
        return uniquePersonList.asUnmodifiableObservableList();
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
        if (!(other instanceof MatchPlayerList)) {
            return false;
        }

        MatchPlayerList otherMatchPlayerList = (MatchPlayerList) other;
        return uniquePersonList.equals(otherMatchPlayerList.uniquePersonList);
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
