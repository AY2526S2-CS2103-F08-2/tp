package seedu.address.model.person;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * Tests that a {@link Person} matches all configured list filters.
 */
public class PersonMatchesListFiltersPredicate implements Predicate<Person> {
    private final Optional<Role> role;
    private final Optional<Team> team;
    private final Optional<Status> status;
    private final Optional<Position> position;

    /**
     * Creates a predicate with the given optional filters.
     */
    public PersonMatchesListFiltersPredicate(Optional<Role> role, Optional<Team> team,
                                             Optional<Status> status, Optional<Position> position) {
        this.role = role;
        this.team = team;
        this.status = status;
        this.position = position;
    }

    @Override
    public boolean test(Person person) {
        return role.map(value -> person.getRole() == value).orElse(true)
                && team.map(value -> person.getTeam().equals(value)).orElse(true)
                && status.map(value -> person.getStatus().equals(value)).orElse(true)
                && position.map(value -> person.getPosition().equals(value)).orElse(true);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof PersonMatchesListFiltersPredicate)) {
            return false;
        }

        PersonMatchesListFiltersPredicate otherPredicate = (PersonMatchesListFiltersPredicate) other;
        return role.equals(otherPredicate.role)
                && team.equals(otherPredicate.team)
                && status.equals(otherPredicate.status)
                && position.equals(otherPredicate.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(role, team, status, position);
    }
}
