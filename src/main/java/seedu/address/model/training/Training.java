package seedu.address.model.training;


/**
 * Represents a training session.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Training {

    private final Date trainingDate;

    public Training(Date trainingDate, TrainingPlayerList trainingPlayerList) {
        this.trainingDate = trainingDate;
    }
}
