package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;

/**
 * Tests that a {@code Person} has the target role and name matches any of the keywords given.
 */
public class RoleFilteredNameContainsKeywordsPredicate implements Predicate<Person> {
    private final PersonHasRolePredicate rolePredicate;
    private final NameContainsKeywordsPredicate namePredicate;

    /**
     * Creates a predicate that matches persons with the given {@code role} whose names
     * contain at least one of the given {@code keywords}.
     */
    public RoleFilteredNameContainsKeywordsPredicate(Role role, List<String> keywords) {
        requireNonNull(role);
        requireNonNull(keywords);
        this.rolePredicate = new PersonHasRolePredicate(role);
        this.namePredicate = new NameContainsKeywordsPredicate(keywords);
    }

    @Override
    public boolean test(Person person) {
        requireNonNull(person);
        return rolePredicate.test(person) && namePredicate.test(person);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof RoleFilteredNameContainsKeywordsPredicate)) {
            return false;
        }

        RoleFilteredNameContainsKeywordsPredicate otherPredicate = (RoleFilteredNameContainsKeywordsPredicate) other;
        return rolePredicate.equals(otherPredicate.rolePredicate)
                && namePredicate.equals(otherPredicate.namePredicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("rolePredicate", rolePredicate)
                .add("namePredicate", namePredicate)
                .toString();
    }
}

