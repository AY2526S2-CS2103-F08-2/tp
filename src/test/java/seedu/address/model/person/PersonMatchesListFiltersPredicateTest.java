package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class PersonMatchesListFiltersPredicateTest {

    @Test
    public void test_matchingAndMismatchingStatusAndPosition() {
        Person person = new PersonBuilder()
                .withRole(Role.PLAYER)
                .withTeam("First Team")
                .withStatus("Active")
                .withPosition("Defender")
                .build();

        PersonMatchesListFiltersPredicate matchingPredicate = new PersonMatchesListFiltersPredicate(
                Optional.of(Role.PLAYER),
                Optional.of(new Team("First Team")),
                Optional.of(new Status("Active")),
                Optional.of(new Position("Defender")));
        PersonMatchesListFiltersPredicate mismatchingStatusPredicate = new PersonMatchesListFiltersPredicate(
                Optional.of(Role.PLAYER),
                Optional.of(new Team("First Team")),
                Optional.of(new Status("Unavailable")),
                Optional.of(new Position("Defender")));
        PersonMatchesListFiltersPredicate mismatchingPositionPredicate = new PersonMatchesListFiltersPredicate(
                Optional.of(Role.PLAYER),
                Optional.of(new Team("First Team")),
                Optional.of(new Status("Active")),
                Optional.of(new Position("Forward")));

        assertTrue(matchingPredicate.test(person));
        assertFalse(mismatchingStatusPredicate.test(person));
        assertFalse(mismatchingPositionPredicate.test(person));
    }

    @Test
    public void equals() {
        PersonMatchesListFiltersPredicate first = new PersonMatchesListFiltersPredicate(
                Optional.of(Role.PLAYER),
                Optional.of(new Team("First Team")),
                Optional.of(new Status("Active")),
                Optional.of(new Position("Defender")));
        PersonMatchesListFiltersPredicate second = new PersonMatchesListFiltersPredicate(
                Optional.of(Role.PLAYER),
                Optional.of(new Team("First Team")),
                Optional.of(new Status("Active")),
                Optional.of(new Position("Defender")));
        PersonMatchesListFiltersPredicate different = new PersonMatchesListFiltersPredicate(
                Optional.of(Role.STAFF),
                Optional.empty(),
                Optional.of(new Status("Active")),
                Optional.empty());
        PersonMatchesListFiltersPredicate differentTeam = new PersonMatchesListFiltersPredicate(
                Optional.of(Role.PLAYER),
                Optional.of(new Team("Second Team")),
                Optional.of(new Status("Active")),
                Optional.of(new Position("Defender")));
        PersonMatchesListFiltersPredicate differentStatus = new PersonMatchesListFiltersPredicate(
                Optional.of(Role.PLAYER),
                Optional.of(new Team("First Team")),
                Optional.of(new Status("Unavailable")),
                Optional.of(new Position("Defender")));
        PersonMatchesListFiltersPredicate differentPosition = new PersonMatchesListFiltersPredicate(
                Optional.of(Role.PLAYER),
                Optional.of(new Team("First Team")),
                Optional.of(new Status("Active")),
                Optional.of(new Position("Forward")));

        assertTrue(first.equals(first));
        assertTrue(first.equals(second));
        assertFalse(first.equals(different));
        assertFalse(first.equals(differentTeam));
        assertFalse(first.equals(differentStatus));
        assertFalse(first.equals(differentPosition));
        assertFalse(first.equals(1));
        assertFalse(first.equals(null));
    }

    @Test
    public void hashCode_sameValues_sameHashCode() {
        PersonMatchesListFiltersPredicate first = new PersonMatchesListFiltersPredicate(
                Optional.of(Role.PLAYER),
                Optional.of(new Team("First Team")),
                Optional.of(new Status("Active")),
                Optional.of(new Position("Defender")));
        PersonMatchesListFiltersPredicate second = new PersonMatchesListFiltersPredicate(
                Optional.of(Role.PLAYER),
                Optional.of(new Team("First Team")),
                Optional.of(new Status("Active")),
                Optional.of(new Position("Defender")));

        assertEquals(first.hashCode(), second.hashCode());
    }
}
