package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Team attribute that can be assigned to a person.
 * Guarantees: immutable; value is valid as declared in {@link #isValidTeamName(String)}.
 */
public class Team {

    public static final String MESSAGE_CONSTRAINTS =
            "Team names should only contain alphanumeric characters, spaces, and hyphens,"
                    + " and it should not be blank.";
    public static final String VALIDATION_REGEX = "[\\p{Alnum}](?:[\\p{Alnum} -]*[\\p{Alnum}])?";

    public final String value;

    /**
     * Constructs a {@code Team}.
     *
     * @param teamName A valid team name.
     */
    public Team(String teamName) {
        requireNonNull(teamName);
        String trimmedTeamName = teamName.trim();
        checkArgument(isValidTeamName(trimmedTeamName), MESSAGE_CONSTRAINTS);
        this.value = trimmedTeamName;
    }

    /**
     * Returns true if a given string is a valid team name.
     */
    public static boolean isValidTeamName(String test) {
        return test != null && test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns a case-insensitive key for catalog uniqueness checks.
     */
    public String toCanonicalForm() {
        return value.toLowerCase();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Team)) {
            return false;
        }

        Team otherTeam = (Team) other;
        return toCanonicalForm().equals(otherTeam.toCanonicalForm());
    }

    @Override
    public int hashCode() {
        return toCanonicalForm().hashCode();
    }

    @Override
    public String toString() {
        return value;
    }
}

