package seedu.address.model.match;

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

public class MatchPlayerListTest {

    private final Person playerA = new PersonBuilder().withName("Alice").withRole(Role.PLAYER).build();
    private final Person playerB = new PersonBuilder().withName("Bob").withRole(Role.PLAYER).build();
    private final Person staff = new PersonBuilder().withName("Charlie").withRole(Role.STAFF).build();

    @Test
    public void constructor_validPlayers_success() {
        MatchPlayerList matchPlayerList = new MatchPlayerList(List.of(playerA, playerB));

        assertEquals(2, matchPlayerList.asUnmodifiableObservableList().size());
        assertTrue(matchPlayerList.asUnmodifiableObservableList().contains(playerA));
        assertTrue(matchPlayerList.asUnmodifiableObservableList().contains(playerB));
    }

    @Test
    public void add_nullPerson_throwsNullPointerException() {
        MatchPlayerList matchPlayerList = new MatchPlayerList(List.of());
        assertThrows(NullPointerException.class, () -> matchPlayerList.add(null));
    }

    @Test
    public void add_nonPlayer_throwsIllegalArgumentException() {
        MatchPlayerList matchPlayerList = new MatchPlayerList(List.of(playerA));

        assertThrows(IllegalArgumentException.class, () -> matchPlayerList.add(staff));
    }

    @Test
    public void add_duplicatePlayer_throwsException() {
        MatchPlayerList matchPlayerList = new MatchPlayerList(List.of(playerA));

        assertThrows(DuplicatePersonException.class, () -> matchPlayerList.add(playerA));
    }

    @Test
    public void iterator_iteratesThroughPlayers() {
        MatchPlayerList matchPlayerList = new MatchPlayerList(List.of(playerA, playerB));

        int count = 0;
        for (Person person : matchPlayerList) {
            assertTrue(person.equals(playerA) || person.equals(playerB));
            count++;
        }

        assertEquals(2, count);
    }

    @Test
    public void equals() {
        MatchPlayerList list = new MatchPlayerList(List.of(playerA, playerB));
        MatchPlayerList sameList = new MatchPlayerList(List.of(playerA, playerB));
        MatchPlayerList differentList = new MatchPlayerList(List.of(playerA));

        assertTrue(list.equals(list));
        assertTrue(list.equals(sameList));
        assertFalse(list.equals(null));
        assertFalse(list.equals("not a list"));
        assertFalse(list.equals(differentList));
    }

    @Test
    public void hashCode_sameValues_sameHashCode() {
        MatchPlayerList list = new MatchPlayerList(List.of(playerA, playerB));
        MatchPlayerList sameList = new MatchPlayerList(List.of(playerA, playerB));

        assertEquals(list.hashCode(), sameList.hashCode());
    }

    @Test
    public void toStringMethod() {
        MatchPlayerList matchPlayerList = new MatchPlayerList(List.of(playerA, playerB));
        UniquePersonList uniquePersonList = new UniquePersonList();
        uniquePersonList.add(playerA);
        uniquePersonList.add(playerB);

        assertEquals(uniquePersonList.toString(), matchPlayerList.toString());
    }
}
