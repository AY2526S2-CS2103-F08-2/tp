package seedu.address.model.match;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class OpponentNameTest {

    @Test
    public void isValidName_validNames_returnsTrue() {
        assertTrue(OpponentName.isValidName("Manchester United"));
        assertTrue(OpponentName.isValidName("Team123"));
        assertTrue(OpponentName.isValidName("A"));
        assertTrue(OpponentName.isValidName("Real Madrid 2"));
    }

    @Test
    public void constructor_validName_success() {
        OpponentName name = new OpponentName("Liverpool");
        assertEquals("Liverpool", name.toString());
    }

    @Test
    public void isValidName_invalidNames_returnsFalse() {
        // empty / blank
        assertFalse(OpponentName.isValidName(""));
        assertFalse(OpponentName.isValidName(" "));
        assertFalse(OpponentName.isValidName("   "));

        // starts with space
        assertFalse(OpponentName.isValidName(" Team"));

        // invalid characters
        assertFalse(OpponentName.isValidName("Team@123"));
        assertFalse(OpponentName.isValidName("Team#Name"));
        assertFalse(OpponentName.isValidName("Team!"));
    }

    @Test
    public void constructor_invalidName_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new OpponentName(""));
        assertThrows(IllegalArgumentException.class, () -> new OpponentName(" "));
        assertThrows(IllegalArgumentException.class, () -> new OpponentName(" Team"));
        assertThrows(IllegalArgumentException.class, () -> new OpponentName("Team@123"));
    }

    @Test
    public void constructor_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new OpponentName(null));
    }

    @Test
    public void equals() {
        OpponentName name = new OpponentName("Chelsea");
        OpponentName sameName = new OpponentName("Chelsea");
        OpponentName differentName = new OpponentName("Arsenal");

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
