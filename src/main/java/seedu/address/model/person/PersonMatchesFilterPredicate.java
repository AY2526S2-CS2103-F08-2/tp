package seedu.address.model.person;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

/**
 * Tests that a {@link Person} matches all configured structured filter criteria.
 */
public class PersonMatchesFilterPredicate implements Predicate<Person> {
    private final Optional<Role> role;
    private final Optional<Team> team;
    private final Optional<Status> status;
    private final Optional<Position> position;
    private final Optional<NumericComparison> goals;
    private final Optional<NumericComparison> wins;
    private final Optional<NumericComparison> losses;

    /**
     * Creates a predicate with the given optional filter criteria.
     */
    public PersonMatchesFilterPredicate(Optional<Role> role, Optional<Team> team,
                                        Optional<Status> status, Optional<Position> position,
                                        Optional<NumericComparison> goals, Optional<NumericComparison> wins,
                                        Optional<NumericComparison> losses) {
        this.role = role;
        this.team = team;
        this.status = status;
        this.position = position;
        this.goals = goals;
        this.wins = wins;
        this.losses = losses;
    }

    @Override
    public boolean test(Person person) {
        return role.map(value -> person.getRole() == value).orElse(true)
                && team.map(value -> person.getTeam().equals(value)).orElse(true)
                && status.map(value -> person.getStatus().equals(value)).orElse(true)
                && position.map(value -> person.getPosition().equals(value)).orElse(true)
                && goals.map(value -> matchesPlayerStat(person, StatField.GOALS, value)).orElse(true)
                && wins.map(value -> matchesPlayerStat(person, StatField.WINS, value)).orElse(true)
                && losses.map(value -> matchesPlayerStat(person, StatField.LOSSES, value)).orElse(true);
    }

    public Optional<Role> getRole() {
        return role;
    }

    public Optional<Team> getTeam() {
        return team;
    }

    public Optional<Status> getStatus() {
        return status;
    }

    public Optional<Position> getPosition() {
        return position;
    }

    private boolean matchesPlayerStat(Person person, StatField statField, NumericComparison comparison) {
        if (!(person instanceof Player)) {
            return false;
        }

        return comparison.matches(statField.getValue(((Player) person).getStats()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof PersonMatchesFilterPredicate)) {
            return false;
        }

        PersonMatchesFilterPredicate otherPredicate = (PersonMatchesFilterPredicate) other;
        return role.equals(otherPredicate.role)
                && team.equals(otherPredicate.team)
                && status.equals(otherPredicate.status)
                && position.equals(otherPredicate.position)
                && goals.equals(otherPredicate.goals)
                && wins.equals(otherPredicate.wins)
                && losses.equals(otherPredicate.losses);
    }

    @Override
    public int hashCode() {
        return Objects.hash(role, team, status, position, goals, wins, losses);
    }

    /**
     * Represents a comparison against an integer threshold.
     */
    public static class NumericComparison {
        /**
         * Supported comparison operators for numeric filters.
         */
        public enum Operator {
            GREATER_THAN(">"),
            LESS_THAN("<"),
            EQUALS("=");

            private final String symbol;

            Operator(String symbol) {
                this.symbol = symbol;
            }

            public String getSymbol() {
                return symbol;
            }
        }

        private final Operator operator;
        private final int value;

        /**
         * Creates a numeric comparison using the given operator and threshold value.
         */
        public NumericComparison(Operator operator, int value) {
            this.operator = operator;
            this.value = value;
        }

        /**
         * Returns true if the supplied actual value satisfies this comparison.
         */
        public boolean matches(int actualValue) {
            return switch (operator) {
            case GREATER_THAN -> actualValue > value;
            case LESS_THAN -> actualValue < value;
            case EQUALS -> actualValue == value;
            };
        }

        public Operator getOperator() {
            return operator;
        }

        public int getValue() {
            return value;
        }

        @Override
        public boolean equals(Object other) {
            if (other == this) {
                return true;
            }

            if (!(other instanceof NumericComparison)) {
                return false;
            }

            NumericComparison otherComparison = (NumericComparison) other;
            return operator == otherComparison.operator
                    && value == otherComparison.value;
        }

        @Override
        public int hashCode() {
            return Objects.hash(operator, value);
        }

        @Override
        public String toString() {
            return operator.getSymbol() + value;
        }
    }
}
