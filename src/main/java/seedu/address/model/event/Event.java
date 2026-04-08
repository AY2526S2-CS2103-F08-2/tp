package seedu.address.model.event;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.event.match.Match;
import seedu.address.model.event.training.Training;

/**
 * Represents an Event in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public abstract class Event {
    // Identity fields
    protected final EventName eventName;
    protected final Date eventDate;
    protected final EventType eventType;

    // Data fields
    protected final EventPlayerList eventPlayerList;
    protected final EventPlayerList attendedPlayerList;

    /**
     * Every field must be present and not null. Attendee list is default empty.
     */
    protected Event(EventName eventName, Date eventDate, EventType eventType, EventPlayerList eventPlayerList) {
        requireAllNonNull(eventName, eventDate, eventType, eventPlayerList);
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventType = eventType;
        this.eventPlayerList = eventPlayerList;
        this.attendedPlayerList = new EventPlayerList(new HashSet<>());
    }

    /**
     * Overloaded constructor. Every field must be present and not null. Attendee list is passed to constructor.
     */
    public Event(EventName eventName, Date eventDate, EventType eventType, EventPlayerList eventPlayerList,
            EventPlayerList attendedPlayerList) {
        requireAllNonNull(eventName, eventDate, eventType, eventPlayerList, attendedPlayerList);
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventType = eventType;
        this.eventPlayerList = eventPlayerList;
        this.attendedPlayerList = attendedPlayerList;
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
        case TRAINING -> new Training(eventName, eventDate, eventPlayerList);
        };
    }

    /**
     * Factory method for creating event, with existing attendees.
     * @param eventName
     * @param eventDate
     * @param eventType
     * @param eventPlayerList
     * @param attendedPlayerList
     * @return {@code Event}
     */
    public static Event createEventWithAttendees(EventName eventName, Date eventDate, EventType eventType,
            EventPlayerList eventPlayerList, EventPlayerList attendedPlayerList) {
        return switch (eventType) {
        case MATCH -> new Match(eventName, eventDate, eventPlayerList, attendedPlayerList);
        case TRAINING -> new Training(eventName, eventDate, eventPlayerList, attendedPlayerList);
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

    public EventPlayerList getAttendedPlayerList() {
        return attendedPlayerList;
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
        if (!(other instanceof Event)) {
            return false;
        }

        Event otherEvent = (Event) other;
        return eventDate.equals(otherEvent.eventDate)
                && eventName.equals(otherEvent.eventName)
                && eventPlayerList.equals(otherEvent.eventPlayerList)
                && eventType.equals(otherEvent.eventType);
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
