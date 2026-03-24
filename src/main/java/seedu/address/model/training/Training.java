package seedu.address.model.training;


import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

/**
 * Represents a training session.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Training {

    private final Date trainingDate;
    private final TrainingPlayerList trainingPlayerList;
    private final TrainingName trainingName;

    public Training(TrainingName trainingName, Date trainingDate, TrainingPlayerList trainingPlayerList) {
        requireAllNonNull(trainingDate, trainingPlayerList, trainingName);
        this.trainingName = trainingName;
        this.trainingDate = trainingDate;
        this.trainingPlayerList = trainingPlayerList;
    }

    public TrainingName getTrainingName() {
        return trainingName;
    }

    public Date getTrainingDate() {
        return trainingDate;
    }

    public TrainingPlayerList getTrainingPlayerList() {
        return trainingPlayerList;
    }

    /**
     * Returns true if both trainings have the same date and name.
     * This defines a weaker notion of equality between two trainings.
     */
    public boolean isSameTraining(Training otherTraining) {
        if (otherTraining == this) {
            return true;
        }

        return otherTraining != null
               && otherTraining.getTrainingDate().equals(getTrainingDate())
               && otherTraining.getTrainingName().equals(getTrainingName());
    }

    /**
     * Returns true if both trainings have the same identity and data fields.
     * This defines a stronger notion of equality between two trainings.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;

        Training training = (Training) o;
        return training.trainingDate.equals(training.getTrainingDate()) &&
               trainingPlayerList.equals(training.trainingPlayerList) &&
               trainingName.equals(training.trainingName);
    }
}
