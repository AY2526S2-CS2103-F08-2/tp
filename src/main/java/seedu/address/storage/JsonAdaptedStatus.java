package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Status;

/**
 * Jackson-friendly version of {@link Status}.
 */
class JsonAdaptedStatus {

    private final String statusName;

    /**
     * Constructs a {@code JsonAdaptedStatus} with the given {@code statusName}.
     */
    @JsonCreator
    public JsonAdaptedStatus(String statusName) {
        this.statusName = statusName;
    }

    /**
     * Converts a given {@code Status} into this class for Jackson use.
     */
    public JsonAdaptedStatus(Status source) {
        statusName = source.value;
    }

    @JsonValue
    public String getStatusName() {
        return statusName;
    }

    /**
     * Converts this Jackson-friendly adapted status object into the model's {@code Status} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted status.
     */
    public Status toModelType() throws IllegalValueException {
        if (!Status.isValidStatusName(statusName)) {
            throw new IllegalValueException(Status.MESSAGE_CONSTRAINTS);
        }
        return new Status(statusName);
    }
}

