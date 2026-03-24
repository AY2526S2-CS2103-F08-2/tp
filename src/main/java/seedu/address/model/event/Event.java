package seedu.address.model.event;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.event.match.Match;

/**
 * Represents an Event in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public abstract class Event {
    // Identity fields
    private final EventName eventName;
    private final Date eventDate;
    private final EventType eventType;

    // Data fields
    private final EventPlayerList eventPlayerList;

    /**
     * Every field must be present and not null.
     */
    protected Event(EventName eventName, Date eventDate, EventType eventType, EventPlayerList eventPlayerList) {
        requireAllNonNull(eventName, eventDate, eventType, eventPlayerList);
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventType = eventType;
        this.eventPlayerList = eventPlayerList;
    }

    /**
     * Factory method for creating event.
     * @param eventName
     * @param eventDate
     * @param eventType
     * @param eventPlayerList
     * @return {@code Event}
     */
    public static Event createEvent(EventName eventName, Date eventDate, EventType eventType,
                                    EventPlayerList eventPlayerList) {
        return switch (eventType) {
        case MATCH -> new Match(eventName, eventDate, eventPlayerList);
        };
    }

    public Date getEventDate() {
        return eventDate;
    }

    public EventName getEventName() {
        return eventName;
    }

    public EventPlayerList getEventPlayerList() {
        return eventPlayerList;
    }

    public EventType getEventType() {
        return eventType;
    }

    /**
     * Returns true if both events have the same date, name, and event type.
     * This defines a weaker notion of equality between two events.
     */
    public boolean isSameEvent(Event otherEvent) {
        if (otherEvent == this) {
            return true;
        }

        return otherEvent != null
                && otherEvent.getEventDate().equals(getEventDate())
                && otherEvent.getEventName().equals(getEventName())
                && otherEvent.getEventType().equals(getEventType());
    }

    /**
     * Returns true if both events have the same identity and data fields.
     * This defines a stronger notion of equality between two events.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Match)) {
            return false;
        }

        Event otherEvent = (Event) other;
        return eventDate.equals(otherEvent.eventDate)
                && eventName.equals(otherEvent.eventName)
                && eventPlayerList.equals(otherEvent.eventPlayerList);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(eventName, eventDate, eventPlayerList);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("Event name", eventName)
                .add("Event date", eventDate)
                .add("players", eventPlayerList)
                .toString();
    }
}
