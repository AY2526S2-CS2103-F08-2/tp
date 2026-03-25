package seedu.address.model.event;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.event.exceptions.DuplicateEventException;
import seedu.address.model.event.exceptions.EventNotFoundException;
import seedu.address.model.event.match.Match;
import seedu.address.testutil.MatchBuilder;

public class UniqueEventListTest {

    private final Match matchA = new MatchBuilder()
            .withOpponentName("Team A")
            .withDate("2026-04-15 1600")
            .build();

    private final Match matchB = new MatchBuilder()
            .withOpponentName("Team B")
            .withDate("2026-04-16 1600")
            .build();

    private final Match sameIdentityAsMatchA = new MatchBuilder()
            .withOpponentName("Team A")
            .withDate("2026-04-15 1600")
            .build();

    @Test
    public void contains_nullMatch_throwsNullPointerException() {
        UniqueEventList uniqueEventList = new UniqueEventList();
        assertThrows(NullPointerException.class, () -> uniqueEventList.contains(null));
    }

    @Test
    public void contains_matchNotInList_returnsFalse() {
        UniqueEventList uniqueEventList = new UniqueEventList();
        assertFalse(uniqueEventList.contains(matchA));
    }

    @Test
    public void contains_matchInList_returnsTrue() {
        UniqueEventList uniqueEventList = new UniqueEventList();
        uniqueEventList.add(matchA);
        assertTrue(uniqueEventList.contains(matchA));
    }

    @Test
    public void contains_matchWithSameIdentityFieldsInList_returnsTrue() {
        UniqueEventList uniqueEventList = new UniqueEventList();
        uniqueEventList.add(matchA);
        assertTrue(uniqueEventList.contains(sameIdentityAsMatchA));
    }

    @Test
    public void add_nullMatch_throwsNullPointerException() {
        UniqueEventList uniqueEventList = new UniqueEventList();
        assertThrows(NullPointerException.class, () -> uniqueEventList.add(null));
    }

    @Test
    public void add_duplicateMatch_throwsDuplicateMatchException() {
        UniqueEventList uniqueEventList = new UniqueEventList();
        uniqueEventList.add(matchA);
        assertThrows(DuplicateEventException.class, () -> uniqueEventList.add(sameIdentityAsMatchA));
    }

    @Test
    public void add_validMatch_success() {
        UniqueEventList uniqueEventList = new UniqueEventList();
        uniqueEventList.add(matchA);
        assertTrue(uniqueEventList.contains(matchA));
        assertEquals(1, uniqueEventList.asUnmodifiableObservableList().size());
    }

    @Test
    public void setEvent_nullTarget_throwsNullPointerException() {
        UniqueEventList uniqueEventList = new UniqueEventList();
        assertThrows(NullPointerException.class, () -> uniqueEventList.setEvent(null, matchA));
    }

    @Test
    public void setMatch_nullEditedEvent_throwsNullPointerException() {
        UniqueEventList uniqueEventList = new UniqueEventList();
        assertThrows(NullPointerException.class, () -> uniqueEventList.setEvent(matchA, null));
    }

    @Test
    public void setMatch_targetMatchNotInList_throwsEventNotFoundException() {
        UniqueEventList uniqueEventList = new UniqueEventList();
        assertThrows(EventNotFoundException.class, () -> uniqueEventList.setEvent(matchA, matchB));
    }

    @Test
    public void setMatch_editedMatchIsSameEvent_success() {
        UniqueEventList uniqueEventList = new UniqueEventList();
        uniqueEventList.add(matchA);
        uniqueEventList.setEvent(matchA, sameIdentityAsMatchA);

        assertEquals(List.of(sameIdentityAsMatchA), uniqueEventList.asUnmodifiableObservableList());
    }

    @Test
    public void setMatch_editedEventHasDifferentIdentity_success() {
        UniqueEventList uniqueEventList = new UniqueEventList();
        uniqueEventList.add(matchA);
        uniqueEventList.setEvent(matchA, matchB);

        assertEquals(List.of(matchB), uniqueEventList.asUnmodifiableObservableList());
    }

    @Test
    public void setMatch_editedMatchHasNonUniqueIdentity_throwsDuplicateEventException() {
        UniqueEventList uniqueEventList = new UniqueEventList();
        uniqueEventList.add(matchA);
        uniqueEventList.add(matchB);

        Match duplicateIdentity = new MatchBuilder()
                .withOpponentName("Team B")
                .withDate("2026-04-16 1600")
                .build();

        assertThrows(DuplicateEventException.class, () -> uniqueEventList.setEvent(matchA, duplicateIdentity));
    }

    @Test
    public void remove_nullMatch_throwsNullPointerException() {
        UniqueEventList uniqueEventList = new UniqueEventList();
        assertThrows(NullPointerException.class, () -> uniqueEventList.remove(null));
    }

    @Test
    public void remove_matchDoesNotExist_throwsMatchNotFoundException() {
        UniqueEventList uniqueEventList = new UniqueEventList();
        assertThrows(EventNotFoundException.class, () -> uniqueEventList.remove(matchA));
    }

    @Test
    public void remove_existingMatch_removesMatch() {
        UniqueEventList uniqueEventList = new UniqueEventList();
        uniqueEventList.add(matchA);
        uniqueEventList.remove(matchA);

        assertFalse(uniqueEventList.contains(matchA));
        assertTrue(uniqueEventList.asUnmodifiableObservableList().isEmpty());
    }

    @Test
    public void setEvents_nullUniqueEventList_throwsNullPointerException() {
        UniqueEventList uniqueEventList = new UniqueEventList();
        assertThrows(NullPointerException.class, () -> uniqueEventList.setEvents((UniqueEventList) null));
    }

    @Test
    public void setEvents_uniqueMatchList_replacesOwnListWithProvidedUniqueEventList() {
        UniqueEventList uniqueEventList = new UniqueEventList();
        uniqueEventList.add(matchA);

        UniqueEventList replacement = new UniqueEventList();
        replacement.add(matchB);

        uniqueEventList.setEvents(replacement);
        assertEquals(List.of(matchB), uniqueEventList.asUnmodifiableObservableList());
    }

    @Test
    public void setEvents_nullList_throwsNullPointerException() {
        UniqueEventList uniqueEventList = new UniqueEventList();
        assertThrows(NullPointerException.class, () -> uniqueEventList.setEvents((List<Event>) null));
    }

    @Test
    public void setEvents_list_replacesOwnListWithProvidedList() {
        UniqueEventList uniqueEventList = new UniqueEventList();
        uniqueEventList.setEvents(List.of(matchA, matchB));

        assertEquals(List.of(matchA, matchB), uniqueEventList.asUnmodifiableObservableList());
    }

    @Test
    public void setMatches_listWithDuplicateEvents_throwsDuplicateEventException() {
        UniqueEventList uniqueEventList = new UniqueEventList();
        List<Event> duplicateList = List.of(matchA, sameIdentityAsMatchA);

        assertThrows(DuplicateEventException.class, () -> uniqueEventList.setEvents(duplicateList));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueEventList uniqueEventList = new UniqueEventList();
        uniqueEventList.add(matchA);

        assertThrows(UnsupportedOperationException.class, () ->
                uniqueEventList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void iterator_iteratesThroughMatches() {
        UniqueEventList uniqueEventList = new UniqueEventList();
        uniqueEventList.add(matchA);
        uniqueEventList.add(matchB);

        int count = 0;
        for (Event event : uniqueEventList) {
            assertTrue(event.equals(matchA) || event.equals(matchB));
            count++;
        }

        assertEquals(2, count);
    }

    @Test
    public void equals() {
        UniqueEventList uniqueEventList = new UniqueEventList();
        uniqueEventList.add(matchA);

        UniqueEventList sameList = new UniqueEventList();
        sameList.add(matchA);

        UniqueEventList differentList = new UniqueEventList();
        differentList.add(matchB);

        assertTrue(uniqueEventList.equals(uniqueEventList));
        assertTrue(uniqueEventList.equals(sameList));
        assertFalse(uniqueEventList.equals(null));
        assertFalse(uniqueEventList.equals("not a list"));
        assertFalse(uniqueEventList.equals(differentList));
    }

    @Test
    public void hashCode_sameValues_sameHashCode() {
        UniqueEventList uniqueEventList = new UniqueEventList();
        uniqueEventList.add(matchA);

        UniqueEventList sameList = new UniqueEventList();
        sameList.add(matchA);

        assertEquals(uniqueEventList.hashCode(), sameList.hashCode());
    }

    @Test
    public void toStringMethod() {
        UniqueEventList uniqueEventList = new UniqueEventList();
        uniqueEventList.add(matchA);

        assertEquals(uniqueEventList.asUnmodifiableObservableList().toString(), uniqueEventList.toString());
    }
}
