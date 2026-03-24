package seedu.address.model.training.exceptions;

/**
 * Exception class for duplicate trainings.
 */
public class DuplicateTrainingException extends RuntimeException {
    public DuplicateTrainingException() {
        super("Operation would result in duplicate matches");;
    }
}
