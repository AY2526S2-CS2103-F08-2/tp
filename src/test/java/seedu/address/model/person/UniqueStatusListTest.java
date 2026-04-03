package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.exceptions.DuplicateStatusException;
import seedu.address.model.person.exceptions.StatusNotFoundException;

public class UniqueStatusListTest {

    private final UniqueStatusList uniqueStatusList = new UniqueStatusList();

    @Test
    // INVALID_CASE + EP_INVALID (null input)
    public void contains_nullStatus_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueStatusList.contains(null));
    }

    @Test
    // EP_VALID (absent-item partition)
    public void contains_statusNotInList_returnsFalse() {
        assertFalse(uniqueStatusList.contains(new Status("Active")));
    }

    @Test
    // EP_VALID (present-item partition)
    public void contains_statusInList_returnsTrue() {
        Status status = new Status("Active");
        uniqueStatusList.add(status);
        assertTrue(uniqueStatusList.contains(new Status("active")));
    }

    @Test
    // INVALID_CASE + EP_INVALID (duplicate value)
    public void add_duplicateStatus_throwsDuplicateStatusException() {
        uniqueStatusList.add(new Status("Active"));
        assertThrows(DuplicateStatusException.class, () -> uniqueStatusList.add(new Status("active")));
    }

    @Test
    // INVALID_CASE + EP_INVALID (remove missing value)
    public void remove_statusDoesNotExist_throwsStatusNotFoundException() {
        assertThrows(StatusNotFoundException.class, () -> uniqueStatusList.remove(new Status("Active")));
    }

    @Test
    // VALID_CASE + COMBO (bulk replace list state)
    public void setStatuses_list_replacesOwnListWithProvidedList() {
        uniqueStatusList.add(new Status("Active"));
        List<Status> statusList = Collections.singletonList(new Status("Unavailable"));
        uniqueStatusList.setStatuses(statusList);

        UniqueStatusList expected = new UniqueStatusList();
        expected.add(new Status("Unavailable"));
        assertEquals(expected, uniqueStatusList);
    }

    @Test
    // INVALID_CASE + EP_INVALID (duplicate values in batch input)
    public void setStatuses_listWithDuplicateStatuses_throwsDuplicateStatusException() {
        List<Status> duplicateStatuses = Arrays.asList(new Status("Unknown"), new Status("unknown"));
        assertThrows(DuplicateStatusException.class, () -> uniqueStatusList.setStatuses(duplicateStatuses));
    }

    @Test
    // REGRESSION_GUARD (defensive immutability of exposed list)
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () ->
                uniqueStatusList.asUnmodifiableObservableList().remove(0));
    }
}
