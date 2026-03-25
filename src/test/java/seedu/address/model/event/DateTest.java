package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.Assert;

public class DateTest {

    @Test
    public void constructor_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> new Date(null));
    }


    @Test
    public void isValidDate_validDate_returnsTrue() {
        assertTrue(Date.isValidDate("2026-04-15 1600"));
        assertTrue(Date.isValidDate("2021-01-01 0000"));
    }

    @Test
    public void constructor_validDate_success() {
        Date date = new Date("2026-04-15 1600");
        assertEquals("15 April 2026, 4:00 PM", date.toString());
    }

    @Test
    public void isValidDate_invalidFormat_returnsFalse() {
        assertFalse(Date.isValidDate("15-04-2026 1600")); // wrong order
        assertFalse(Date.isValidDate("2026/04/15 1600")); // wrong separator
        assertFalse(Date.isValidDate("2026-04-15 16:00")); // colon instead of HHmm
        assertFalse(Date.isValidDate("random string"));
    }

    @Test
    public void constructor_invalidDate_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Date("invalid-date"));
    }


    @Test
    public void isValidDate_invalidTime_returnsFalse() {
        assertFalse(Date.isValidDate("2026-04-15 2460")); // invalid hour/minute
        assertFalse(Date.isValidDate("2026-04-15 9999"));
    }

    @Test
    public void isValidDate_invalidDate_returnsFalse() {
        assertFalse(Date.isValidDate("2026-02-33 1200")); // invalid day
        assertFalse(Date.isValidDate("2026-13-01 1200")); // invalid month
    }

    @Test
    public void equals() {
        Date date1 = new Date("2026-04-15 1600");
        Date date2 = new Date("2026-04-15 1600");
        Date differentDate = new Date("2026-04-16 1600");

        // same values
        assertTrue(date1.equals(date2));

        // same object
        assertTrue(date1.equals(date1));

        // null
        assertFalse(date1.equals(null));

        // different type
        assertFalse(date1.equals("string"));

        // different value
        assertFalse(date1.equals(differentDate));
    }
}
