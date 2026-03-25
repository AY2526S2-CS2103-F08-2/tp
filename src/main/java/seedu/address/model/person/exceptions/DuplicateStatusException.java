package seedu.address.model.person.exceptions;

/**
 * Signals that the operation will result in duplicate statuses.
 */
public class DuplicateStatusException extends RuntimeException {
    public DuplicateStatusException() {
        super("Operation would result in duplicate statuses");
    }
}
