package seedu.address.model.event.match;

import seedu.address.commons.util.ToStringBuilder;
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

    /**
     * Overloaded constructor.
     *
     * @param opponentName
     * @param matchDate
     * @param eventPlayerList
     * @param attendedPlayerList
     */
    public Match(EventName opponentName, Date matchDate, EventPlayerList eventPlayerList,
            EventPlayerList attendedPlayerList) {
        super(opponentName, matchDate, EventType.MATCH, eventPlayerList, attendedPlayerList);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("opponent name", eventName)
                .add("match date", eventDate)
                .add("players", eventPlayerList)
                .toString();
    }
}
