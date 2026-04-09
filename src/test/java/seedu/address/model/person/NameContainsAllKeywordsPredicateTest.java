package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.testutil.PersonBuilder;

public class NameContainsAllKeywordsPredicateTest {

    @Test
    public void equals() {
        NameContainsAllKeywordsPredicate firstPredicate =
                new NameContainsAllKeywordsPredicate(Collections.singletonList("first"));
        NameContainsAllKeywordsPredicate secondPredicate =
                new NameContainsAllKeywordsPredicate(Collections.singletonList("second"));

        assertTrue(firstPredicate.equals(firstPredicate));
        assertTrue(firstPredicate.equals(new NameContainsAllKeywordsPredicate(Collections.singletonList("first"))));
        assertFalse(firstPredicate.equals(1));
        assertFalse(firstPredicate.equals(null));
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsAllKeywords_returnsTrue() {
        NameContainsAllKeywordsPredicate predicate =
                new NameContainsAllKeywordsPredicate(Arrays.asList("Alex", "Neo"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alex Neo").build()));

        predicate = new NameContainsAllKeywordsPredicate(Arrays.asList("aLeX", "nEo"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alex Neo").build()));
    }

    @Test
    public void test_nameDoesNotContainAllKeywords_returnsFalse() {
        NameContainsAllKeywordsPredicate predicate = new NameContainsAllKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withName("Alex Neo").build()));

        predicate = new NameContainsAllKeywordsPredicate(Arrays.asList("Alex", "Neo"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alex Yeoh").build()));

        predicate = new NameContainsAllKeywordsPredicate(Arrays.asList("Neo", "12345"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alex Neo").build()));
    }
}
