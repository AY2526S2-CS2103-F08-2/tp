package seedu.address.testutil;

import static seedu.address.testutil.TypicalPersons.PLAYER_AMY;

import java.util.Set;

import seedu.address.model.event.Date;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventName;
import seedu.address.model.event.EventPlayerList;
import seedu.address.model.event.EventType;
import seedu.address.model.person.Person;

/**
 * A utility class to help with building Event objects.
 */
public class EventBuilder {

    public static final String DEFAULT_MATCH_NAME = "Barcelona";
    public static final String DEFAULT_MATCH_TYPE = "MATCH";
    public static final String DEFAULT_DATE = "2025-01-01 1000";
    public static final EventPlayerList DEFAULT_EVENT_PLAYER_LIST = new EventPlayerList(Set.of(PLAYER_AMY));

    private EventName eventName;
    private EventType eventType;
    private Date eventDate;
    private EventPlayerList eventPlayerList;

    /**
     * Creates an EventBuilder with the default details. Event type is match by default
     */
    public EventBuilder() {
        eventName = new EventName(DEFAULT_MATCH_NAME);
        eventType = EventType.valueOf(DEFAULT_MATCH_TYPE);
        eventDate = new Date(DEFAULT_DATE);
        eventPlayerList = DEFAULT_EVENT_PLAYER_LIST;
    }

    /**
     * Initializes the EventBuilder with the data of {@code eventToCopy}.
     */
    public EventBuilder(Event eventToCopy) {
        eventName = eventToCopy.getEventName();
        eventType = eventToCopy.getEventType();
        eventDate = eventToCopy.getEventDate();
        eventPlayerList = eventToCopy.getEventPlayerList();
    }

    /**
     * Sets the {@code EventName} of the {@code Event} that we are building.
     */
    public EventBuilder withEventName(String name) {
        this.eventName = new EventName(name);
        return this;
    }

    /**
     * Sets the {@code EventType} of the {@code Event} that we are building.
     */
    public EventBuilder withEventType(String type) {
        this.eventType = EventType.valueOf(type);
        return this;
    }

    /**
     * Sets the {@code EventDate} of the {@code Event} that we are building.
     */
    public EventBuilder withDate(String date) {
        this.eventDate = new Date(date);
        return this;
    }

    /**
     * Sets the {@code EventPlayersList} of the {@code Event} that we are building.
     */
    public EventBuilder withPlayers(Set<Person> persons) {
        this.eventPlayerList = new EventPlayerList(persons);
        return this;
    }

    public Event build() {
        return Event.createEvent(eventName, eventDate, eventType, eventPlayerList);
    }
}
