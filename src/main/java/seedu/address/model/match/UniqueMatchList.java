package seedu.address.model.match;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.match.exceptions.DuplicateMatchException;
import seedu.address.model.match.exceptions.MatchNotFoundException;

/**
 * A list of matches that enforces uniqueness between its elements and does not allow nulls.
 * A match is considered unique by comparing using {@code Match#isSameMatch(Match)}. As such, adding and updating of
 * matches uses Match#isSameMatch(Match) for equality so as to ensure that the match being added or updated is
 * unique in terms of identity in the UniqueMatchList. However, the removal of a match uses Match#equals(Object) so
 * as to ensure that the match with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Match#isSameMatch(Match)
 */
public class UniqueMatchList implements Iterable<Match> {

    private final ObservableList<Match> internalList = FXCollections.observableArrayList();
    private final ObservableList<Match> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent match as the given argument.
     */
    public boolean contains(Match toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameMatch);
    }

    /**
     * Adds a match to the list.
     * The match must not already exist in the list.
     */
    public void add(Match toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateMatchException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the match {@code target} in the list with {@code editedMatch}.
     * {@code target} must exist in the list.
     * The match identity of {@code editedMatch} must not be the same as another existing match in the list.
     */
    public void setMatch(Match target, Match editedMatch) {
        requireAllNonNull(target, editedMatch);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new MatchNotFoundException();
        }

        if (!target.isSameMatch(editedMatch) && contains(editedMatch)) {
            throw new DuplicateMatchException();
        }

        internalList.set(index, editedMatch);
    }

    /**
     * Removes the equivalent match from the list.
     * The match must exist in the list.
     */
    public void remove(Match toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new MatchNotFoundException();
        }
    }

    public void setMatches(UniqueMatchList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code matches}.
     * {@code matches} must not contain duplicate matches.
     */
    public void setMatches(List<Match> matches) {
        requireAllNonNull(matches);
        if (!matchesAreUnique(matches)) {
            throw new DuplicateMatchException();
        }

        internalList.setAll(matches);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Match> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Match> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UniqueMatchList)) {
            return false;
        }

        UniqueMatchList otherUniqueMatchList = (UniqueMatchList) other;
        return internalList.equals(otherUniqueMatchList.internalList);
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
     * Returns true if {@code Matches} contains only unique matches.
     */
    private boolean matchesAreUnique(List<Match> matches) {
        for (int i = 0; i < matches.size() - 1; i++) {
            for (int j = i + 1; j < matches.size(); j++) {
                if (matches.get(i).isSameMatch(matches.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
