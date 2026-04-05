package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class PositionTest {

    @Test
    // INVALID_CASE + EP_INVALID (null input)
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Position(null));
    }

    @Test
    // INVALID_CASE + BOUNDARY (blank input)
    public void constructor_invalidPosition_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Position(" "));
    }

    @Test
    // BOUNDARY (trimming preserves canonical value)
    public void constructor_validPosition_trimsWhitespace() {
        Position position = new Position("  Midfielder  ");
        assertTrue(position.equals(new Position("Midfielder")));
    }

    @Test
    // EP_VALID + EP_INVALID (name format partitions)
    public void isValidPositionName() {
        assertFalse(Position.isValidPositionName(""));
        assertFalse(Position.isValidPositionName(" "));
        assertFalse(Position.isValidPositionName("#Forward"));

        assertTrue(Position.isValidPositionName("Forward"));
        assertTrue(Position.isValidPositionName("Center Back"));
        assertTrue(Position.isValidPositionName("Right-Wing"));
    }

    @Test
    // REGRESSION_GUARD (case-insensitive equality contract)
    public void equals_caseInsensitive_returnsTrue() {
        Position lower = new Position("goalkeeper");
        Position upper = new Position("Goalkeeper");

        assertTrue(lower.equals(upper));
        assertTrue(lower.hashCode() == upper.hashCode());
    }
}
