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
    public Training(EventName eventName, Date eventDate, EventPlayerList eventPlayerList) {
        super(eventName, eventDate, EventType.TRAINING, eventPlayerList);
    }

    /**
     * Overloaded constructor.
     *
     * @param eventName
     * @param eventDate
     * @param eventPlayerList
     * @param attendedPlayerList
     */
    public Training(EventName eventName, Date eventDate, EventPlayerList eventPlayerList,
            EventPlayerList attendedPlayerList) {
        super(eventName, eventDate, EventType.TRAINING, eventPlayerList, attendedPlayerList);
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
