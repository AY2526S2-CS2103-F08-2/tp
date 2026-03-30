package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Position attribute that can be assigned to a player.
 * Guarantees: immutable; value is valid as declared in {@link #isValidPositionName(String)}.
 */
public class Position {

    public static final String DEFAULT_UNASSIGNED_POSITION = "Unassigned Position";
    public static final String MESSAGE_CONSTRAINTS =
            "Position names should only contain alphanumeric characters, spaces, and hyphens,"
                    + " and it should not be blank.";
    public static final String VALIDATION_REGEX = "[\\p{Alnum}](?:[\\p{Alnum} -]*[\\p{Alnum}])?";

    public final String value;

    /**
     * Constructs a {@code Position}.
     *
     * @param positionName A valid position name.
     */
    public Position(String positionName) {
        requireNonNull(positionName);
        String trimmedPositionName = positionName.trim();
        checkArgument(isValidPositionName(trimmedPositionName), MESSAGE_CONSTRAINTS);
        this.value = trimmedPositionName;
    }

    /**
     * Returns true if a given string is a valid position name.
     */
    public static boolean isValidPositionName(String test) {
        return test != null && test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns a case-insensitive key for catalog uniqueness checks.
     */
    public String toCanonicalForm() {
        return value.toLowerCase();
    }

    /**
     * Returns true if this position is the protected default position.
     */
    public boolean isDefaultUnassignedPosition() {
        return this.equals(new Position(DEFAULT_UNASSIGNED_POSITION));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Position)) {
            return false;
        }

        Position otherPosition = (Position) other;
        return toCanonicalForm().equals(otherPosition.toCanonicalForm());
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

