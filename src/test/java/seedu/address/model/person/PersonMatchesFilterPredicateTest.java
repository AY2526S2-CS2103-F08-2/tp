package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.PersonMatchesFilterPredicate.NumericComparison;
import seedu.address.model.person.PersonMatchesFilterPredicate.NumericComparison.Operator;
import seedu.address.testutil.PersonBuilder;

public class PersonMatchesFilterPredicateTest {

    @Test
    public void test_attributeAndLossFilters() {
        Person player = new PersonBuilder()
                .withRole(Role.PLAYER)
                .withTeam("First Team")
                .withStatus("Active")
                .withPosition("Forward")
                .build();
        Player playerAsPlayer = (Player) player;
        playerAsPlayer.getStats().setMatchesLost(0);

        PersonMatchesFilterPredicate matchingPredicate = new PersonMatchesFilterPredicate(
                Optional.of(Role.PLAYER),
                Optional.of(new Team("First Team")),
                Optional.of(new Status("Active")),
                Optional.of(new Position("Forward")),
                Optional.empty(),
                Optional.empty(),
                Optional.of(new NumericComparison(Operator.EQUALS, 0)));
        PersonMatchesFilterPredicate mismatchingStatus = new PersonMatchesFilterPredicate(
                Optional.of(Role.PLAYER),
                Optional.of(new Team("First Team")),
                Optional.of(new Status("Unavailable")),
                Optional.of(new Position("Forward")),
                Optional.empty(),
                Optional.empty(),
                Optional.of(new NumericComparison(Operator.EQUALS, 0)));
        PersonMatchesFilterPredicate mismatchingPosition = new PersonMatchesFilterPredicate(
                Optional.of(Role.PLAYER),
                Optional.of(new Team("First Team")),
                Optional.of(new Status("Active")),
                Optional.of(new Position("Defender")),
                Optional.empty(),
                Optional.empty(),
                Optional.of(new NumericComparison(Operator.EQUALS, 0)));
        PersonMatchesFilterPredicate mismatchingLosses = new PersonMatchesFilterPredicate(
                Optional.of(Role.PLAYER),
                Optional.of(new Team("First Team")),
                Optional.of(new Status("Active")),
                Optional.of(new Position("Forward")),
                Optional.empty(),
                Optional.empty(),
                Optional.of(new NumericComparison(Operator.EQUALS, 1)));

        assertTrue(matchingPredicate.test(player));
        assertFalse(mismatchingStatus.test(player));
        assertFalse(mismatchingPosition.test(player));
        assertFalse(mismatchingLosses.test(player));
    }

    @Test
    public void test_statFilterNonPlayer_returnsFalse() {
        Person staff = new PersonBuilder()
                .withRole(Role.STAFF)
                .build();
        PersonMatchesFilterPredicate predicate = new PersonMatchesFilterPredicate(
                Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(),
                Optional.of(new NumericComparison(Operator.GREATER_THAN, 0)),
                Optional.empty(), Optional.empty());

        assertFalse(predicate.test(staff));
    }

    @Test
    public void equalsAndHashCode() {
        PersonMatchesFilterPredicate first = new PersonMatchesFilterPredicate(
                Optional.of(Role.PLAYER),
                Optional.of(new Team("First Team")),
                Optional.of(new Status("Active")),
                Optional.of(new Position("Forward")),
                Optional.of(new NumericComparison(Operator.GREATER_THAN, 5)),
                Optional.of(new NumericComparison(Operator.LESS_THAN, 3)),
                Optional.of(new NumericComparison(Operator.EQUALS, 0)));
        PersonMatchesFilterPredicate second = new PersonMatchesFilterPredicate(
                Optional.of(Role.PLAYER),
                Optional.of(new Team("First Team")),
                Optional.of(new Status("Active")),
                Optional.of(new Position("Forward")),
                Optional.of(new NumericComparison(Operator.GREATER_THAN, 5)),
                Optional.of(new NumericComparison(Operator.LESS_THAN, 3)),
                Optional.of(new NumericComparison(Operator.EQUALS, 0)));
        PersonMatchesFilterPredicate differentTeam = new PersonMatchesFilterPredicate(
                Optional.of(Role.PLAYER),
                Optional.of(new Team("Second Team")),
                Optional.of(new Status("Active")),
                Optional.of(new Position("Forward")),
                Optional.of(new NumericComparison(Operator.GREATER_THAN, 5)),
                Optional.of(new NumericComparison(Operator.LESS_THAN, 3)),
                Optional.of(new NumericComparison(Operator.EQUALS, 0)));
        PersonMatchesFilterPredicate differentStatus = new PersonMatchesFilterPredicate(
                Optional.of(Role.PLAYER),
                Optional.of(new Team("First Team")),
                Optional.of(new Status("Unavailable")),
                Optional.of(new Position("Forward")),
                Optional.of(new NumericComparison(Operator.GREATER_THAN, 5)),
                Optional.of(new NumericComparison(Operator.LESS_THAN, 3)),
                Optional.of(new NumericComparison(Operator.EQUALS, 0)));
        PersonMatchesFilterPredicate differentPosition = new PersonMatchesFilterPredicate(
                Optional.of(Role.PLAYER),
                Optional.of(new Team("First Team")),
                Optional.of(new Status("Active")),
                Optional.of(new Position("Defender")),
                Optional.of(new NumericComparison(Operator.GREATER_THAN, 5)),
                Optional.of(new NumericComparison(Operator.LESS_THAN, 3)),
                Optional.of(new NumericComparison(Operator.EQUALS, 0)));
        PersonMatchesFilterPredicate differentGoals = new PersonMatchesFilterPredicate(
                Optional.of(Role.PLAYER),
                Optional.of(new Team("First Team")),
                Optional.of(new Status("Active")),
                Optional.of(new Position("Forward")),
                Optional.of(new NumericComparison(Operator.GREATER_THAN, 6)),
                Optional.of(new NumericComparison(Operator.LESS_THAN, 3)),
                Optional.of(new NumericComparison(Operator.EQUALS, 0)));
        PersonMatchesFilterPredicate differentWins = new PersonMatchesFilterPredicate(
                Optional.of(Role.PLAYER),
                Optional.of(new Team("First Team")),
                Optional.of(new Status("Active")),
                Optional.of(new Position("Forward")),
                Optional.of(new NumericComparison(Operator.GREATER_THAN, 5)),
                Optional.of(new NumericComparison(Operator.LESS_THAN, 2)),
                Optional.of(new NumericComparison(Operator.EQUALS, 0)));
        PersonMatchesFilterPredicate differentLosses = new PersonMatchesFilterPredicate(
                Optional.of(Role.PLAYER),
                Optional.of(new Team("First Team")),
                Optional.of(new Status("Active")),
                Optional.of(new Position("Forward")),
                Optional.of(new NumericComparison(Operator.GREATER_THAN, 5)),
                Optional.of(new NumericComparison(Operator.LESS_THAN, 3)),
                Optional.of(new NumericComparison(Operator.EQUALS, 1)));

        assertTrue(first.equals(first));
        assertTrue(first.equals(second));
        assertEquals(first.hashCode(), second.hashCode());
        assertFalse(first.equals(differentTeam));
        assertFalse(first.equals(differentStatus));
        assertFalse(first.equals(differentPosition));
        assertFalse(first.equals(differentGoals));
        assertFalse(first.equals(differentWins));
        assertFalse(first.equals(differentLosses));
        assertFalse(first.equals(1));
        assertFalse(first.equals(null));
    }

    @Test
    public void numericComparison_behaviourAndHelpers() {
        NumericComparison equalsComparison = new NumericComparison(Operator.EQUALS, 0);
        NumericComparison greaterComparison = new NumericComparison(Operator.GREATER_THAN, 2);
        NumericComparison lessComparison = new NumericComparison(Operator.LESS_THAN, 3);

        assertTrue(equalsComparison.matches(0));
        assertTrue(greaterComparison.matches(3));
        assertTrue(lessComparison.matches(2));
        assertEquals(Operator.EQUALS, equalsComparison.getOperator());
        assertEquals(0, equalsComparison.getValue());
        assertEquals("=", Operator.EQUALS.getSymbol());
        assertEquals("=0", equalsComparison.toString());
        assertTrue(equalsComparison.equals(equalsComparison));
        assertTrue(equalsComparison.equals(new NumericComparison(Operator.EQUALS, 0)));
        assertFalse(equalsComparison.equals(new NumericComparison(Operator.EQUALS, 1)));
        assertFalse(equalsComparison.equals(new NumericComparison(Operator.GREATER_THAN, 0)));
        assertFalse(equalsComparison.equals(1));
        assertFalse(equalsComparison.equals(null));
        assertEquals(equalsComparison.hashCode(), new NumericComparison(Operator.EQUALS, 0).hashCode());
    }
}
