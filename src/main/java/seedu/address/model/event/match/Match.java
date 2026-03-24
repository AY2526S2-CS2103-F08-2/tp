package seedu.address.model.event.match;

import seedu.address.model.event.Date;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventName;
import seedu.address.model.event.EventPlayerList;
import seedu.address.model.event.EventType;

/**
 * Represents a Match in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Match extends Event {

    /**
     * Every field must be present and not null.
     */
    public Match(EventName opponentName, Date matchDate, EventPlayerList eventPlayerList) {
        super(opponentName, matchDate, EventType.MATCH, eventPlayerList);
    }
}
