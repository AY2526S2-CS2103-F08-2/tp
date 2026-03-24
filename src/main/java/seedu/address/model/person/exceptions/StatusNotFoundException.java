package seedu.address.model.person.exceptions;

/**
 * Signals that the operation is unable to find the specified status.
 */
public class StatusNotFoundException extends RuntimeException {
    public StatusNotFoundException() {
        super("Status not found in catalog");
    }
}
