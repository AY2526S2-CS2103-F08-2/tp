package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.exceptions.DuplicatePositionException;
import seedu.address.model.person.exceptions.PositionNotFoundException;

public class UniquePositionListTest {

    private final UniquePositionList uniquePositionList = new UniquePositionList();

    @Test
    // INVALID_CASE + EP_INVALID (null input)
    public void contains_nullPosition_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePositionList.contains(null));
    }

    @Test
    // EP_VALID (absent-item partition)
    public void contains_positionNotInList_returnsFalse() {
        assertFalse(uniquePositionList.contains(new Position("Forward")));
    }

    @Test
    // EP_VALID (present-item partition)
    public void contains_positionInList_returnsTrue() {
        Position position = new Position("Forward");
        uniquePositionList.add(position);
        assertTrue(uniquePositionList.contains(new Position("forward")));
    }

    @Test
    // INVALID_CASE + EP_INVALID (duplicate value)
    public void add_duplicatePosition_throwsDuplicatePositionException() {
        uniquePositionList.add(new Position("Forward"));
        assertThrows(DuplicatePositionException.class, () -> uniquePositionList.add(new Position("forward")));
    }

    @Test
    // INVALID_CASE + EP_INVALID (remove missing value)
    public void remove_positionDoesNotExist_throwsPositionNotFoundException() {
        assertThrows(PositionNotFoundException.class, () -> uniquePositionList.remove(new Position("Forward")));
    }

    @Test
    // VALID_CASE + COMBO (bulk replace list state)
    public void setPositions_list_replacesOwnListWithProvidedList() {
        uniquePositionList.add(new Position("Forward"));
        List<Position> positionList = Collections.singletonList(new Position("Defender"));
        uniquePositionList.setPositions(positionList);

        UniquePositionList expected = new UniquePositionList();
        expected.add(new Position("Defender"));
        assertEquals(expected, uniquePositionList);
    }

    @Test
    // INVALID_CASE + EP_INVALID (duplicate values in batch input)
    public void setPositions_listWithDuplicatePositions_throwsDuplicatePositionException() {
        List<Position> duplicatePositions = Arrays.asList(new Position("Forward"), new Position("forward"));
        assertThrows(DuplicatePositionException.class, () -> uniquePositionList.setPositions(duplicatePositions));
    }

    @Test
    // REGRESSION_GUARD (defensive immutability of exposed list)
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () ->
                uniquePositionList.asUnmodifiableObservableList().remove(0));
    }
}
