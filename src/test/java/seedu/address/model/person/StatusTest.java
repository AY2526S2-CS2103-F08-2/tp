package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class StatusTest {

    @Test
    // INVALID_CASE + EP_INVALID (null input)
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Status(null));
    }

    @Test
    // INVALID_CASE + BOUNDARY (blank input)
    public void constructor_invalidStatus_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Status(" "));
    }

    @Test
    // BOUNDARY (trimming preserves canonical value)
    public void constructor_validStatus_trimsWhitespace() {
        Status status = new Status("  Active  ");
        assertTrue(status.equals(new Status("Active")));
    }

    @Test
    // EP_VALID + EP_INVALID (name format partitions)
    public void isValidStatusName() {
        assertFalse(Status.isValidStatusName(""));
        assertFalse(Status.isValidStatusName(" "));
        assertFalse(Status.isValidStatusName("#Active"));

        assertTrue(Status.isValidStatusName("Unknown"));
        assertTrue(Status.isValidStatusName("Active"));
        assertTrue(Status.isValidStatusName("On-Leave"));
    }

    @Test
    // REGRESSION_GUARD (case-insensitive equality contract)
    public void equals_caseInsensitive_returnsTrue() {
        Status lower = new Status("unknown");
        Status upper = new Status("Unknown");

        assertTrue(lower.equals(upper));
        assertTrue(lower.hashCode() == upper.hashCode());
    }
}
