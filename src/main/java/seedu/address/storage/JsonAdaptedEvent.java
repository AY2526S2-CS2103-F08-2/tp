package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
    public static final String NEGATIVE_GOALS_MESSAGE_FORMAT = "Goals cannot be negative!";
    public static final String PLAYER_GOALS_NOT_IN_MATCH_MESSAGE_FORMAT =
            "Goal entry refers to a player not in the match: %s";

    private final String eventName;
    private final String eventType;
    private final String date;
    private final List<String> players = new ArrayList<>();

    // Match-only fields
    private final List<JsonAdaptedGoalEntry> playerGoals = new ArrayList<>();
    private final Integer opponentGoals;

    /**
     * Constructs a {@code JsonAdaptedEvent} with the given match details.
     */
    @JsonCreator
    public JsonAdaptedEvent(@JsonProperty("eventName") String eventName,
                            @JsonProperty("eventType") String eventType,
                            @JsonProperty("date") String date,
                            @JsonProperty("players") List<String> players,
                            @JsonProperty("playerGoals") List<JsonAdaptedGoalEntry> playerGoals,
                            @JsonProperty("opponentGoals") Integer opponentGoals) {
        this.eventName = eventName;
        this.eventType = eventType;
        this.date = date;
        if (players != null) {
            this.players.addAll(players);
        }
        if (playerGoals != null) {
            this.playerGoals.addAll(playerGoals);
        }
        this.opponentGoals = opponentGoals;
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

        if (source instanceof Match match) {
            match.getPlayerGoals().forEach((playerName, goals) ->
                    playerGoals.add(new JsonAdaptedGoalEntry(playerName, goals)));
            this.opponentGoals = match.getOpponentGoals();
        } else {
            this.opponentGoals = null;
        }
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

        final Set<Person> playerList = new HashSet<>();
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

        // Special handling for Match
        if (modelEventType == EventType.MATCH) {
            Map<String, Integer> goalMap = new HashMap<>();

            for (JsonAdaptedGoalEntry entry : playerGoals) {
                if (entry.getGoals() < 0) {
                    throw new IllegalValueException(NEGATIVE_GOALS_MESSAGE_FORMAT);
                }
                if (!players.contains(entry.getPlayerName())) {
                    throw new IllegalValueException(String.format(
                            PLAYER_GOALS_NOT_IN_MATCH_MESSAGE_FORMAT, entry.getPlayerName()));
                }
                goalMap.put(entry.getPlayerName(), entry.getGoals());
            }

            int modelOpponentGoals = opponentGoals == null ? 0 : opponentGoals;
            if (modelOpponentGoals < 0) {
                throw new IllegalValueException(NEGATIVE_GOALS_MESSAGE_FORMAT);
            }

            return new Match(modelEventName, modelDate, modelPlayers, goalMap, modelOpponentGoals);
        }

        return Event.createEvent(modelEventName, modelDate, modelEventType, modelPlayers);
    }
}
