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
    public void contains_nullPosition_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniquePositionList.contains(null));
    }

    @Test
    public void contains_positionNotInList_returnsFalse() {
        assertFalse(uniquePositionList.contains(new Position("Forward")));
    }

    @Test
    public void contains_positionInList_returnsTrue() {
        Position position = new Position("Forward");
        uniquePositionList.add(position);
        assertTrue(uniquePositionList.contains(new Position("forward")));
    }

    @Test
    public void add_duplicatePosition_throwsDuplicatePositionException() {
        uniquePositionList.add(new Position("Forward"));
        assertThrows(DuplicatePositionException.class, () -> uniquePositionList.add(new Position("forward")));
    }

    @Test
    public void remove_positionDoesNotExist_throwsPositionNotFoundException() {
        assertThrows(PositionNotFoundException.class, () -> uniquePositionList.remove(new Position("Forward")));
    }

    @Test
    public void setPositions_list_replacesOwnListWithProvidedList() {
        uniquePositionList.add(new Position("Forward"));
        List<Position> positionList = Collections.singletonList(new Position("Defender"));
        uniquePositionList.setPositions(positionList);

        UniquePositionList expected = new UniquePositionList();
        expected.add(new Position("Defender"));
        assertEquals(expected, uniquePositionList);
    }

    @Test
    public void setPositions_listWithDuplicatePositions_throwsDuplicatePositionException() {
        List<Position> duplicatePositions = Arrays.asList(new Position("Forward"), new Position("forward"));
        assertThrows(DuplicatePositionException.class, () -> uniquePositionList.setPositions(duplicatePositions));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () ->
                uniquePositionList.asUnmodifiableObservableList().remove(0));
    }
}

