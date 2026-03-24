package seedu.address.model.person.exceptions;

/**
 * Signals that the operation is unable to find the specified position.
 */
public class PositionNotFoundException extends RuntimeException {
    public PositionNotFoundException() {
        super("Position not found in catalog");
    }
}
