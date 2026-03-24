package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.Date;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventName;
import seedu.address.model.event.EventPlayerList;
import seedu.address.model.event.EventType;
import seedu.address.model.event.match.Match;
import seedu.address.model.person.Person;
import seedu.address.model.person.Role;

/**
 * Jackson-friendly version of {@link Match}.
 */
class JsonAdaptedEvent {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Event's %s field is missing!";
    public static final String MISSING_PLAYER_MESSAGE_FORMAT = "%s does not exist!";
    public static final String NOT_A_PLAYER_MESSAGE_FORMAT = "%s is not a player!";

    private final String eventName;
    private final String eventType;
    private final String date;
    private final List<String> players = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedEvent} with the given match details.
     */
    @JsonCreator
    public JsonAdaptedEvent(@JsonProperty("eventName") String eventName, @JsonProperty("eventType") String eventType,
                            @JsonProperty("date") String date,
                            @JsonProperty("players") List<String> players) {
        this.eventName = eventName;
        this.eventType = eventType;
        this.date = date;
        if (players != null) {
            this.players.addAll(players);
        }
    }

    /**
     * Converts a given {@code Event} into this class for Jackson use.
     */
    public JsonAdaptedEvent(Event source) {
        eventName = source.getEventName().toString();
        eventType = source.getEventType().name();
        date = source.getEventDate().getDateWithInputFormat();

        players.addAll(source.getEventPlayerList().asUnmodifiableObservableList().stream()
                .map(person -> person.getName().toString())
                .toList());
    }

    /**
     * Converts this Jackson-friendly adapted match object into the model's {@code Match} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted match.
     */
    public Event toModelType(Map<String, Person> personMap) throws IllegalValueException {
        if (eventName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    EventName.class.getSimpleName()));
        }
        if (!EventName.isValidName(eventName)) {
            throw new IllegalValueException(EventName.MESSAGE_CONSTRAINTS);
        }
        final EventName modelEventName = new EventName(eventName);

        if (eventType == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    EventType.class.getSimpleName()));
        }

        if (!EventType.isValidEventType(eventType)) {
            throw new IllegalValueException((EventType.MESSAGE_CONSTRAINTS));
        }

        final EventType modelEventType = EventType.valueOf(eventType);

        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName()));
        }
        if (!Date.isValidDate(date)) {
            throw new IllegalValueException(Date.MESSAGE_CONSTRAINTS);
        }
        final Date modelDate = new Date(date);

        final List<Person> playerList = new ArrayList<>();
        for (String playerName : players) {
            Person person = personMap.get(playerName);
            if (person == null) {
                throw new IllegalValueException(String.format(MISSING_PLAYER_MESSAGE_FORMAT, playerName));
            }
            if (person.getRole() != Role.PLAYER) {
                throw new IllegalValueException(String.format(NOT_A_PLAYER_MESSAGE_FORMAT, playerName));
            }
            playerList.add(person);
        }

        final EventPlayerList modelPlayers = new EventPlayerList(playerList);

        return Event.createEvent(modelEventName, modelDate, modelEventType, modelPlayers);
    }
}
