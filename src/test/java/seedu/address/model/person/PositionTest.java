package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class PositionTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Position(null));
    }

    @Test
    public void constructor_invalidPosition_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Position(" "));
    }

    @Test
    public void constructor_validPosition_trimsWhitespace() {
        Position position = new Position("  Midfielder  ");
        assertTrue(position.equals(new Position("Midfielder")));
    }

    @Test
    public void isValidPositionName() {
        assertFalse(Position.isValidPositionName(""));
        assertFalse(Position.isValidPositionName(" "));
        assertFalse(Position.isValidPositionName("#Forward"));

        assertTrue(Position.isValidPositionName("Forward"));
        assertTrue(Position.isValidPositionName("Center Back"));
        assertTrue(Position.isValidPositionName("Right-Wing"));
    }

    @Test
    public void equals_caseInsensitive_returnsTrue() {
        Position lower = new Position("goalkeeper");
        Position upper = new Position("Goalkeeper");

        assertTrue(lower.equals(upper));
        assertTrue(lower.hashCode() == upper.hashCode());
    }
}

