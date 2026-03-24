package seedu.address.model.training;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.training.exceptions.DuplicateTrainingException;
import seedu.address.model.training.exceptions.TrainingNotFoundException;

/**
 * A list of Trainings that enforces uniqueness between its elements and does not allow nulls.
 * A Training is considered unique by comparing using {@code Training#isSameTraining(Training)}. As such, adding and updating of
 * Trainings uses Training#isSameTraining(Training) for equality so as to ensure that the Training being added or updated is
 * unique in terms of identity in the UniqueTrainingList. However, the removal of a Training uses Training#equals(Object) so
 * as to ensure that the Training with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 */
public class UniqueTrainingList implements Iterable<Training> {

    private final ObservableList<Training> internalList = FXCollections.observableArrayList();
    private final ObservableList<Training> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent Training as the given argument.
     */
    public boolean contains(Training toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameTraining);
    }

    /**
     * Adds a Training to the list.
     * The Training must not already exist in the list.
     */
    public void add(Training toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateTrainingException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the Training {@code target} in the list with {@code editedTraining}.
     * {@code target} must exist in the list.
     * The Training identity of {@code editedTraining} must not be the same as another existing Training in the list.
     */
    public void setTraining(Training target, Training editedTraining) {
        requireAllNonNull(target, editedTraining);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new TrainingNotFoundException();
        }

        if (!target.isSameTraining(editedTraining) && contains(editedTraining)) {
            throw new DuplicateTrainingException();
        }

        internalList.set(index, editedTraining);
    }

    /**
     * Removes the equivalent Training from the list.
     * The Training must exist in the list.
     */
    public void remove(Training toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new TrainingNotFoundException();
        }
    }

    public void setTrainings(UniqueTrainingList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code Trainings}.
     * {@code Trainings} must not contain duplicate Trainings.
     */
    public void setTrainings(List<Training> Trainings) {
        requireAllNonNull(Trainings);
        if (!TrainingsAreUnique(Trainings)) {
            throw new DuplicateTrainingException();
        }

        internalList.setAll(Trainings);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Training> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Training> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UniqueTrainingList)) {
            return false;
        }

        UniqueTrainingList otherUniqueTrainingList = (UniqueTrainingList) other;
        return internalList.equals(otherUniqueTrainingList.internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        return internalList.toString();
    }

    /**
     * Returns true if {@code Trainings} contains only unique Trainings.
     */
    private boolean TrainingsAreUnique(List<Training> Trainings) {
        for (int i = 0; i < Trainings.size() - 1; i++) {
            for (int j = i + 1; j < Trainings.size(); j++) {
                if (Trainings.get(i).isSameTraining(Trainings.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}