package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.person.exceptions.DuplicateStatusException;
import seedu.address.model.person.exceptions.StatusNotFoundException;

/**
 * A list of statuses that enforces uniqueness and does not allow nulls.
 */
public class UniqueStatusList implements Iterable<Status> {

    private final ObservableList<Status> internalList = FXCollections.observableArrayList();
    private final ObservableList<Status> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent status as the given argument.
     */
    public boolean contains(Status toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * Adds a status to the list.
     * The status must not already exist in the list.
     */
    public void add(Status toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateStatusException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the status {@code target} in the list with {@code editedStatus}.
     * {@code target} must exist in the list.
     * The status identity of {@code editedStatus} must not duplicate another existing status.
     */
    public void setStatus(Status target, Status editedStatus) {
        requireAllNonNull(target, editedStatus);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new StatusNotFoundException();
        }

        if (!target.equals(editedStatus) && contains(editedStatus)) {
            throw new DuplicateStatusException();
        }

        internalList.set(index, editedStatus);
    }

    /**
     * Removes the equivalent status from the list.
     * The status must exist in the list.
     */
    public void remove(Status toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new StatusNotFoundException();
        }
    }

    public void setStatuses(UniqueStatusList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code statuses}.
     * {@code statuses} must not contain duplicates.
     */
    public void setStatuses(List<Status> statuses) {
        requireAllNonNull(statuses);
        if (!statusesAreUnique(statuses)) {
            throw new DuplicateStatusException();
        }
        internalList.setAll(statuses);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Status> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Status> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof UniqueStatusList)) {
            return false;
        }

        UniqueStatusList otherUniqueStatusList = (UniqueStatusList) other;
        return internalList.equals(otherUniqueStatusList.internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        return internalList.toString();
    }

    private boolean statusesAreUnique(List<Status> statuses) {
        for (int i = 0; i < statuses.size() - 1; i++) {
            for (int j = i + 1; j < statuses.size(); j++) {
                if (statuses.get(i).equals(statuses.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}

