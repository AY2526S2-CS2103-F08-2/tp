package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.exceptions.DuplicatePositionException;
import seedu.address.model.person.exceptions.PositionNotFoundException;

/**
 * A list of positions that enforces uniqueness and does not allow nulls.
 */
public class UniquePositionList implements Iterable<Position> {

    private final ObservableList<Position> internalList = FXCollections.observableArrayList();
    private final ObservableList<Position> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent position as the given argument.
     */
    public boolean contains(Position toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a position to the list.
     * The position must not already exist in the list.
     */
    public void add(Position toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicatePositionException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the position {@code target} in the list with {@code editedPosition}.
     * {@code target} must exist in the list.
     * The position identity of {@code editedPosition} must not duplicate another existing position.
     */
    public void setPosition(Position target, Position editedPosition) {
        requireAllNonNull(target, editedPosition);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new PositionNotFoundException();
        }

        if (!target.equals(editedPosition) && contains(editedPosition)) {
            throw new DuplicatePositionException();
        }

        internalList.set(index, editedPosition);
    }

    /**
     * Removes the equivalent position from the list.
     * The position must exist in the list.
     */
    public void remove(Position toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new PositionNotFoundException();
        }
    }

    public void setPositions(UniquePositionList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code positions}.
     * {@code positions} must not contain duplicates.
     */
    public void setPositions(List<Position> positions) {
        requireAllNonNull(positions);
        if (!positionsAreUnique(positions)) {
            throw new DuplicatePositionException();
        }
        internalList.setAll(positions);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Position> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Position> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof UniquePositionList)) {
            return false;
        }

        UniquePositionList otherUniquePositionList = (UniquePositionList) other;
        return internalList.equals(otherUniquePositionList.internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        return internalList.toString();
    }

    private boolean positionsAreUnique(List<Position> positions) {
        for (int i = 0; i < positions.size() - 1; i++) {
            for (int j = i + 1; j < positions.size(); j++) {
                if (positions.get(i).equals(positions.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}

