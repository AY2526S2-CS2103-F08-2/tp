package seedu.address.model.event.training;


import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.event.Date;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventName;
import seedu.address.model.event.EventPlayerList;
import seedu.address.model.event.EventType;

/**
 * Represents a training session.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Training extends Event {
    /**
     * Every field must be present and not null.
     */
    public Training(EventName trainingName, Date matchDate, EventPlayerList eventPlayerList) {
        super(trainingName, matchDate, EventType.TRAINING, eventPlayerList);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("training name", eventName)
                .add("training date", eventDate)
                .add("players", eventPlayerList)
                .toString();
    }
}
