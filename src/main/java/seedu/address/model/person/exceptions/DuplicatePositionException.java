package seedu.address.model.person.exceptions;

/**
 * Signals that the operation will result in duplicate positions.
 */
public class DuplicatePositionException extends RuntimeException {
    public DuplicatePositionException() {
        super("Operation would result in duplicate positions");
    }
}
