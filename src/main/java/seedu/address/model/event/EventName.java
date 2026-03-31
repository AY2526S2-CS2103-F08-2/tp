package seedu.address.model.event;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents an Event name in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidName(String)}
 */
public class EventName {

    public static final String MESSAGE_CONSTRAINTS =
            "Event name should only contain alphanumeric characters and spaces, and it should not be blank";

    /*
     * The first character of the name must not be a whitespace,
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String VALIDATION_REGEX = "[\\p{Alnum}][\\p{Alnum} ]*";

    public final String fullName;

    /**
     * Constructs an {@code EventName}.
     *
     * @param eventName A valid name.
     */
    public EventName(String eventName) {
        requireNonNull(eventName);
        checkArgument(isValidName(eventName), MESSAGE_CONSTRAINTS);
        fullName = eventName;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidName(String test) {
        return test.matches(VALIDATION_REGEX);
    }


    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EventName)) {
            return false;
        }

        EventName otherName = (EventName) other;
        return fullName.equals(otherName.fullName);
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }
}
