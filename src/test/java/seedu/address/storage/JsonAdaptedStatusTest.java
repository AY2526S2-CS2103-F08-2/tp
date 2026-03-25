package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Status;

public class JsonAdaptedStatusTest {

    private static final String VALID_STATUS = "Unknown";
    private static final String INVALID_STATUS = "#Status";

    @Test
    public void toModelType_validStatus_returnsStatus() throws Exception {
        JsonAdaptedStatus jsonAdaptedStatus = new JsonAdaptedStatus(VALID_STATUS);
        assertEquals(new Status(VALID_STATUS), jsonAdaptedStatus.toModelType());
    }

    @Test
    public void toModelType_invalidStatus_throwsIllegalValueException() {
        JsonAdaptedStatus jsonAdaptedStatus = new JsonAdaptedStatus(INVALID_STATUS);
        assertThrows(IllegalValueException.class, Status.MESSAGE_CONSTRAINTS, jsonAdaptedStatus::toModelType);
    }

    @Test
    public void toModelType_nullStatus_throwsIllegalValueException() {
        JsonAdaptedStatus jsonAdaptedStatus = new JsonAdaptedStatus((String) null);
        assertThrows(IllegalValueException.class, Status.MESSAGE_CONSTRAINTS, jsonAdaptedStatus::toModelType);
    }
}

