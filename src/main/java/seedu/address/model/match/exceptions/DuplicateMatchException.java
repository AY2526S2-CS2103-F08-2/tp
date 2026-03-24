package seedu.address.model.match.exceptions;


/**
 * Signals that the operation will result in duplicate Matches (Matches are considered duplicates if they have the same
 * identity).
 */
public class DuplicateMatchException extends RuntimeException {
    public DuplicateMatchException() {
        super("Operation would result in duplicate matches");;
    }
}
