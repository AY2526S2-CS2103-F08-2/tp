package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.model.person.Role;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.testutil.PersonBuilder;

public class EventPlayerListTest {

    private final Person playerA = new PersonBuilder().withName("Alice").withRole(Role.PLAYER).build();
    private final Person playerB = new PersonBuilder().withName("Bob").withRole(Role.PLAYER).build();
    private final Person staff = new PersonBuilder().withName("Charlie").withRole(Role.STAFF).build();

    @Test
    public void constructor_validPlayers_success() {
        EventPlayerList eventPlayerList = new EventPlayerList(List.of(playerA, playerB));

        assertEquals(2, eventPlayerList.asUnmodifiableObservableList().size());
        assertTrue(eventPlayerList.asUnmodifiableObservableList().contains(playerA));
        assertTrue(eventPlayerList.asUnmodifiableObservableList().contains(playerB));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        EventPlayerList eventPlayerList = new EventPlayerList(List.of());
        assertThrows(NullPointerException.class, () -> eventPlayerList.add(null));
    }

    @Test
    public void add_nonPlayer_throwsIllegalArgumentException() {
        EventPlayerList eventPlayerList = new EventPlayerList(List.of(playerA));

        assertThrows(IllegalArgumentException.class, () -> eventPlayerList.add(staff));
    }

    @Test
    public void add_duplicatePlayer_throwsException() {
        EventPlayerList eventPlayerList = new EventPlayerList(List.of(playerA));

        assertThrows(DuplicatePersonException.class, () -> eventPlayerList.add(playerA));
    }

    @Test
    public void iterator_iteratesThroughPlayers() {
        EventPlayerList eventPlayerList = new EventPlayerList(List.of(playerA, playerB));

        int count = 0;
        for (Person person : eventPlayerList) {
            assertTrue(person.equals(playerA) || person.equals(playerB));
            count++;
        }

        assertEquals(2, count);
    }

    @Test
    public void equals() {
        EventPlayerList list = new EventPlayerList(List.of(playerA, playerB));
        EventPlayerList sameList = new EventPlayerList(List.of(playerA, playerB));
        EventPlayerList differentList = new EventPlayerList(List.of(playerA));

        assertTrue(list.equals(list));
        assertTrue(list.equals(sameList));
        assertFalse(list.equals(null));
        assertFalse(list.equals("not a list"));
        assertFalse(list.equals(differentList));
    }

    @Test
    public void hashCode_sameValues_sameHashCode() {
        EventPlayerList list = new EventPlayerList(List.of(playerA, playerB));
        EventPlayerList sameList = new EventPlayerList(List.of(playerA, playerB));

        assertEquals(list.hashCode(), sameList.hashCode());
    }

    @Test
    public void toStringMethod() {
        EventPlayerList eventPlayerList = new EventPlayerList(List.of(playerA, playerB));
        UniquePersonList uniquePersonList = new UniquePersonList();
        uniquePersonList.add(playerA);
        uniquePersonList.add(playerB);

        assertEquals(uniquePersonList.toString(), eventPlayerList.toString());
    }
}
