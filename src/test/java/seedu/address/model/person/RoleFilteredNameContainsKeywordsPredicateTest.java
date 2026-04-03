package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class RoleFilteredNameContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        RoleFilteredNameContainsKeywordsPredicate firstPredicate =
                new RoleFilteredNameContainsKeywordsPredicate(Role.PLAYER, Collections.singletonList("first"));
        RoleFilteredNameContainsKeywordsPredicate secondPredicate =
                new RoleFilteredNameContainsKeywordsPredicate(Role.STAFF, Collections.singletonList("first"));
        RoleFilteredNameContainsKeywordsPredicate thirdPredicate =
                new RoleFilteredNameContainsKeywordsPredicate(Role.PLAYER, Arrays.asList("first", "second"));

        assertTrue(firstPredicate.equals(firstPredicate));
        assertTrue(firstPredicate.equals(
                new RoleFilteredNameContainsKeywordsPredicate(Role.PLAYER, Collections.singletonList("first"))));
        assertFalse(firstPredicate.equals(1));
        assertFalse(firstPredicate.equals(null));
        assertFalse(firstPredicate.equals(secondPredicate));
        assertFalse(firstPredicate.equals(thirdPredicate));
    }

    @Test
    // COMBO + EP_VALID (role + keyword must both match)
    public void test_matchingRoleAndName_returnsTrue() {
        RoleFilteredNameContainsKeywordsPredicate predicate =
                new RoleFilteredNameContainsKeywordsPredicate(Role.PLAYER, Arrays.asList("Amy", "Tan"));

        assertTrue(predicate.test(new PersonBuilder().withName("Amy Bee").withRole(Role.PLAYER).build()));
        assertTrue(predicate.test(new PersonBuilder().withName("Ben Tan").withRole(Role.PLAYER).build()));
    }

    @Test
    // COMBO + INVALID_CASE (fail if role or keyword partition mismatches)
    public void test_nonMatchingRoleOrName_returnsFalse() {
        RoleFilteredNameContainsKeywordsPredicate predicate =
                new RoleFilteredNameContainsKeywordsPredicate(Role.STAFF, Collections.singletonList("Amy"));

        assertFalse(predicate.test(new PersonBuilder().withName("Amy Bee").withRole(Role.PLAYER).build()));
        assertFalse(predicate.test(new PersonBuilder().withName("Ida Mueller").withRole(Role.STAFF).build()));
    }

    @Test
    public void toStringMethod() {
        RoleFilteredNameContainsKeywordsPredicate predicate =
                new RoleFilteredNameContainsKeywordsPredicate(Role.STAFF, Arrays.asList("Ida", "Mueller"));

        String expected = RoleFilteredNameContainsKeywordsPredicate.class.getCanonicalName()
                + "{rolePredicate=" + new PersonHasRolePredicate(Role.STAFF)
                + ", namePredicate=" + new NameContainsKeywordsPredicate(Arrays.asList("Ida", "Mueller")) + "}";
        assertEquals(expected, predicate.toString());
    }
}
