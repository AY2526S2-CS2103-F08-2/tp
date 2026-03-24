package seedu.address.model.match;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.match.exceptions.DuplicateMatchException;
import seedu.address.model.match.exceptions.MatchNotFoundException;
import seedu.address.testutil.MatchBuilder;

public class UniqueMatchListTest {

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
        UniqueMatchList uniqueMatchList = new UniqueMatchList();
        assertThrows(NullPointerException.class, () -> uniqueMatchList.contains(null));
    }

    @Test
    public void contains_matchNotInList_returnsFalse() {
        UniqueMatchList uniqueMatchList = new UniqueMatchList();
        assertFalse(uniqueMatchList.contains(matchA));
    }

    @Test
    public void contains_matchInList_returnsTrue() {
        UniqueMatchList uniqueMatchList = new UniqueMatchList();
        uniqueMatchList.add(matchA);
        assertTrue(uniqueMatchList.contains(matchA));
    }

    @Test
    public void contains_matchWithSameIdentityFieldsInList_returnsTrue() {
        UniqueMatchList uniqueMatchList = new UniqueMatchList();
        uniqueMatchList.add(matchA);
        assertTrue(uniqueMatchList.contains(sameIdentityAsMatchA));
    }

    @Test
    public void add_nullMatch_throwsNullPointerException() {
        UniqueMatchList uniqueMatchList = new UniqueMatchList();
        assertThrows(NullPointerException.class, () -> uniqueMatchList.add(null));
    }

    @Test
    public void add_duplicateMatch_throwsDuplicateMatchException() {
        UniqueMatchList uniqueMatchList = new UniqueMatchList();
        uniqueMatchList.add(matchA);
        assertThrows(DuplicateMatchException.class, () -> uniqueMatchList.add(sameIdentityAsMatchA));
    }

    @Test
    public void add_validMatch_success() {
        UniqueMatchList uniqueMatchList = new UniqueMatchList();
        uniqueMatchList.add(matchA);
        assertTrue(uniqueMatchList.contains(matchA));
        assertEquals(1, uniqueMatchList.asUnmodifiableObservableList().size());
    }

    @Test
    public void setMatch_nullTarget_throwsNullPointerException() {
        UniqueMatchList uniqueMatchList = new UniqueMatchList();
        assertThrows(NullPointerException.class, () -> uniqueMatchList.setMatch(null, matchA));
    }

    @Test
    public void setMatch_nullEditedMatch_throwsNullPointerException() {
        UniqueMatchList uniqueMatchList = new UniqueMatchList();
        assertThrows(NullPointerException.class, () -> uniqueMatchList.setMatch(matchA, null));
    }

    @Test
    public void setMatch_targetMatchNotInList_throwsMatchNotFoundException() {
        UniqueMatchList uniqueMatchList = new UniqueMatchList();
        assertThrows(MatchNotFoundException.class, () -> uniqueMatchList.setMatch(matchA, matchB));
    }

    @Test
    public void setMatch_editedMatchIsSameMatch_success() {
        UniqueMatchList uniqueMatchList = new UniqueMatchList();
        uniqueMatchList.add(matchA);
        uniqueMatchList.setMatch(matchA, sameIdentityAsMatchA);

        assertEquals(List.of(sameIdentityAsMatchA), uniqueMatchList.asUnmodifiableObservableList());
    }

    @Test
    public void setMatch_editedMatchHasDifferentIdentity_success() {
        UniqueMatchList uniqueMatchList = new UniqueMatchList();
        uniqueMatchList.add(matchA);
        uniqueMatchList.setMatch(matchA, matchB);

        assertEquals(List.of(matchB), uniqueMatchList.asUnmodifiableObservableList());
    }

    @Test
    public void setMatch_editedMatchHasNonUniqueIdentity_throwsDuplicateMatchException() {
        UniqueMatchList uniqueMatchList = new UniqueMatchList();
        uniqueMatchList.add(matchA);
        uniqueMatchList.add(matchB);

        Match duplicateIdentity = new MatchBuilder()
                .withOpponentName("Team B")
                .withDate("2026-04-16 1600")
                .build();

        assertThrows(DuplicateMatchException.class, () -> uniqueMatchList.setMatch(matchA, duplicateIdentity));
    }

    @Test
    public void remove_nullMatch_throwsNullPointerException() {
        UniqueMatchList uniqueMatchList = new UniqueMatchList();
        assertThrows(NullPointerException.class, () -> uniqueMatchList.remove(null));
    }

    @Test
    public void remove_matchDoesNotExist_throwsMatchNotFoundException() {
        UniqueMatchList uniqueMatchList = new UniqueMatchList();
        assertThrows(MatchNotFoundException.class, () -> uniqueMatchList.remove(matchA));
    }

    @Test
    public void remove_existingMatch_removesMatch() {
        UniqueMatchList uniqueMatchList = new UniqueMatchList();
        uniqueMatchList.add(matchA);
        uniqueMatchList.remove(matchA);

        assertFalse(uniqueMatchList.contains(matchA));
        assertTrue(uniqueMatchList.asUnmodifiableObservableList().isEmpty());
    }

    @Test
    public void setMatches_nullUniqueMatchList_throwsNullPointerException() {
        UniqueMatchList uniqueMatchList = new UniqueMatchList();
        assertThrows(NullPointerException.class, () -> uniqueMatchList.setMatches((UniqueMatchList) null));
    }

    @Test
    public void setMatches_uniqueMatchList_replacesOwnListWithProvidedUniqueMatchList() {
        UniqueMatchList uniqueMatchList = new UniqueMatchList();
        uniqueMatchList.add(matchA);

        UniqueMatchList replacement = new UniqueMatchList();
        replacement.add(matchB);

        uniqueMatchList.setMatches(replacement);
        assertEquals(List.of(matchB), uniqueMatchList.asUnmodifiableObservableList());
    }

    @Test
    public void setMatches_nullList_throwsNullPointerException() {
        UniqueMatchList uniqueMatchList = new UniqueMatchList();
        assertThrows(NullPointerException.class, () -> uniqueMatchList.setMatches((List<Match>) null));
    }

    @Test
    public void setMatches_list_replacesOwnListWithProvidedList() {
        UniqueMatchList uniqueMatchList = new UniqueMatchList();
        uniqueMatchList.setMatches(List.of(matchA, matchB));

        assertEquals(List.of(matchA, matchB), uniqueMatchList.asUnmodifiableObservableList());
    }

    @Test
    public void setMatches_listWithDuplicateMatches_throwsDuplicateMatchException() {
        UniqueMatchList uniqueMatchList = new UniqueMatchList();
        List<Match> duplicateList = List.of(matchA, sameIdentityAsMatchA);

        assertThrows(DuplicateMatchException.class, () -> uniqueMatchList.setMatches(duplicateList));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueMatchList uniqueMatchList = new UniqueMatchList();
        uniqueMatchList.add(matchA);

        assertThrows(UnsupportedOperationException.class, () ->
                uniqueMatchList.asUnmodifiableObservableList().remove(0));
    }

    @Test
    public void iterator_iteratesThroughMatches() {
        UniqueMatchList uniqueMatchList = new UniqueMatchList();
        uniqueMatchList.add(matchA);
        uniqueMatchList.add(matchB);

        int count = 0;
        for (Match match : uniqueMatchList) {
            assertTrue(match.equals(matchA) || match.equals(matchB));
            count++;
        }

        assertEquals(2, count);
    }

    @Test
    public void equals() {
        UniqueMatchList uniqueMatchList = new UniqueMatchList();
        uniqueMatchList.add(matchA);

        UniqueMatchList sameList = new UniqueMatchList();
        sameList.add(matchA);

        UniqueMatchList differentList = new UniqueMatchList();
        differentList.add(matchB);

        assertTrue(uniqueMatchList.equals(uniqueMatchList));
        assertTrue(uniqueMatchList.equals(sameList));
        assertFalse(uniqueMatchList.equals(null));
        assertFalse(uniqueMatchList.equals("not a list"));
        assertFalse(uniqueMatchList.equals(differentList));
    }

    @Test
    public void hashCode_sameValues_sameHashCode() {
        UniqueMatchList uniqueMatchList = new UniqueMatchList();
        uniqueMatchList.add(matchA);

        UniqueMatchList sameList = new UniqueMatchList();
        sameList.add(matchA);

        assertEquals(uniqueMatchList.hashCode(), sameList.hashCode());
    }

    @Test
    public void toStringMethod() {
        UniqueMatchList uniqueMatchList = new UniqueMatchList();
        uniqueMatchList.add(matchA);

        assertEquals(uniqueMatchList.asUnmodifiableObservableList().toString(), uniqueMatchList.toString());
    }
}
