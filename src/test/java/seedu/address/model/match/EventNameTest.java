package seedu.address.model.match;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.event.EventName;

public class EventNameTest {

    @Test
    public void isValidName_validNames_returnsTrue() {
        assertTrue(EventName.isValidName("Manchester United"));
        assertTrue(EventName.isValidName("Team123"));
        assertTrue(EventName.isValidName("A"));
        assertTrue(EventName.isValidName("Real Madrid 2"));
    }

    @Test
    public void constructor_validName_success() {
        EventName name = new EventName("Liverpool");
        assertEquals("Liverpool", name.toString());
    }

    @Test
    public void isValidName_invalidNames_returnsFalse() {
        // empty / blank
        assertFalse(EventName.isValidName(""));
        assertFalse(EventName.isValidName(" "));
        assertFalse(EventName.isValidName("   "));

        // starts with space
        assertFalse(EventName.isValidName(" Team"));

        // invalid characters
        assertFalse(EventName.isValidName("Team@123"));
        assertFalse(EventName.isValidName("Team#Name"));
        assertFalse(EventName.isValidName("Team!"));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new EventName(""));
        assertThrows(IllegalArgumentException.class, () -> new EventName(" "));
        assertThrows(IllegalArgumentException.class, () -> new EventName(" Team"));
        assertThrows(IllegalArgumentException.class, () -> new EventName("Team@123"));
    }

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new EventName(null));
    }

    @Test
    public void equals() {
        EventName name = new EventName("Chelsea");
        EventName sameName = new EventName("Chelsea");
        EventName differentName = new EventName("Arsenal");

        // same values
        assertTrue(name.equals(sameName));

        // same object
        assertTrue(name.equals(name));

        // null
        assertFalse(name.equals(null));

        // different type
        assertFalse(name.equals("string"));

        // different value
        assertFalse(name.equals(differentName));
    }
}
