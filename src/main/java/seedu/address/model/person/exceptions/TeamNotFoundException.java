package seedu.address.model.person.exceptions;

/**
 * Signals that the operation is unable to find the specified team.
 */
public class TeamNotFoundException extends RuntimeException {
    public TeamNotFoundException() {
        super("Team not found in catalog");
    }
}
