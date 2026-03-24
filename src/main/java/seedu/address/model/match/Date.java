package seedu.address.model.match;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

/**
 * Represents a Match's date in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidDate(String)}
 */
public class Date {
    public static final String MESSAGE_CONSTRAINTS =
            "Date should follow the following format: yyyy-MM-dd HHmm";

    public static final DateTimeFormatter INPUT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm",
            Locale.ENGLISH);
    public static final DateTimeFormatter DISPLAY_FORMATTER = DateTimeFormatter.ofPattern("d MMMM y, h:mm a",
            Locale.ENGLISH);


    public final LocalDateTime matchDate;

    /**
     * Constructs a {@code Date}.
     *
     * @param date A valid date.
     */
    public Date(String date) {
        requireNonNull(date);
        checkArgument(isValidDate(date), MESSAGE_CONSTRAINTS);
        matchDate = parse(date);
    }
    /**
     * Returns true if a given string is a valid date.
     */
    public static boolean isValidDate(String date) {
        try {
            LocalDateTime.parse(date, INPUT_FORMATTER);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
    /**
     * Parses a string into a LocalDateTime object with the correct format.
     *
     * @param date A valid date.
     */
    public LocalDateTime parse(String date) {
        return LocalDateTime.parse(date, INPUT_FORMATTER);
    }

    /**
     * Returns the date with input format, used for storage in JSON file.
     */
    public String getDateWithInputFormat() {
        return matchDate.format(INPUT_FORMATTER);
    }

    @Override
    public String toString() {
        return matchDate.format(DISPLAY_FORMATTER);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Date)) {
            return false;
        }

        Date otherDate = (Date) other;
        return matchDate.equals(otherDate.matchDate);
    }

    @Override
    public int hashCode() {
        return matchDate.hashCode();
    }

}
