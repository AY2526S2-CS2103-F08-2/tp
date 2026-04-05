package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Position;

public class JsonAdaptedPositionTest {

    private static final String VALID_POSITION = "Midfielder";
    private static final String INVALID_POSITION = "#Position";

    @Test
    // VALID_CASE + EP_VALID
    public void toModelType_validPosition_returnsPosition() throws Exception {
        JsonAdaptedPosition jsonAdaptedPosition = new JsonAdaptedPosition(VALID_POSITION);
        assertEquals(new Position(VALID_POSITION), jsonAdaptedPosition.toModelType());
    }

    @Test
    // INVALID_CASE + EP_INVALID (invalid token format)
    public void toModelType_invalidPosition_throwsIllegalValueException() {
        JsonAdaptedPosition jsonAdaptedPosition = new JsonAdaptedPosition(INVALID_POSITION);
        assertThrows(IllegalValueException.class, Position.MESSAGE_CONSTRAINTS, jsonAdaptedPosition::toModelType);
    }

    @Test
    // INVALID_CASE + EP_INVALID (null input)
    public void toModelType_nullPosition_throwsIllegalValueException() {
        JsonAdaptedPosition jsonAdaptedPosition = new JsonAdaptedPosition((String) null);
        assertThrows(IllegalValueException.class, Position.MESSAGE_CONSTRAINTS, jsonAdaptedPosition::toModelType);
    }
}
