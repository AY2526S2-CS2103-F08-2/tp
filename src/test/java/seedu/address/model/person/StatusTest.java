package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class StatusTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new Status(null));
    }

    @Test
    public void constructor_invalidStatus_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Status(" "));
    }

    @Test
    public void constructor_validStatus_trimsWhitespace() {
        Status status = new Status("  Active  ");
        assertTrue(status.equals(new Status("Active")));
    }

    @Test
    public void isValidStatusName() {
        assertFalse(Status.isValidStatusName(""));
        assertFalse(Status.isValidStatusName(" "));
        assertFalse(Status.isValidStatusName("#Active"));

        assertTrue(Status.isValidStatusName("Unknown"));
        assertTrue(Status.isValidStatusName("Active"));
        assertTrue(Status.isValidStatusName("On-Leave"));
    }

    @Test
    public void equals_caseInsensitive_returnsTrue() {
        Status lower = new Status("unknown");
        Status upper = new Status("Unknown");

        assertTrue(lower.equals(upper));
        assertTrue(lower.hashCode() == upper.hashCode());
    }
}

