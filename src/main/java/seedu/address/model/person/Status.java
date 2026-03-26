package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents a Status attribute that can be assigned to a person.
 * Guarantees: immutable; value is valid as declared in {@link #isValidStatusName(String)}.
 */
public class Status {

    public static final String DEFAULT_UNKNOWN_STATUS = "Unknown";
    public static final String MESSAGE_CONSTRAINTS =
            "Status names should only contain alphanumeric characters, spaces, and hyphens,"
                    + " and it should not be blank.";
    public static final String VALIDATION_REGEX = "[\\p{Alnum}](?:[\\p{Alnum} -]*[\\p{Alnum}])?";

    public final String value;

    /**
     * Constructs a {@code Status}.
     *
     * @param statusName A valid status name.
     */
    public Status(String statusName) {
        requireNonNull(statusName);
        String trimmedStatusName = statusName.trim();
        checkArgument(isValidStatusName(trimmedStatusName), MESSAGE_CONSTRAINTS);
        this.value = trimmedStatusName;
    }

    /**
     * Returns true if a given string is a valid status name.
     */
    public static boolean isValidStatusName(String test) {
        return test != null && test.matches(VALIDATION_REGEX);
    }

    /**
     * Returns a case-insensitive key for catalog uniqueness checks.
     */
    public String toCanonicalForm() {
        return value.toLowerCase();
    }

    /**
     * Returns true if this status is the protected default status.
     */
    public boolean isDefaultUnknownStatus() {
        return this.equals(new Status(DEFAULT_UNKNOWN_STATUS));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Status)) {
            return false;
        }

        Status otherStatus = (Status) other;
        return toCanonicalForm().equals(otherStatus.toCanonicalForm());
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

