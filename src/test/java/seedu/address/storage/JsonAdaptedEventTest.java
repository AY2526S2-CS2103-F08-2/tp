package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.Date;
import seedu.address.model.event.EventName;
import seedu.address.model.event.EventPlayerList;
import seedu.address.model.event.match.Match;
import seedu.address.model.person.Person;
import seedu.address.model.person.Role;
import seedu.address.testutil.MatchBuilder;
import seedu.address.testutil.PersonBuilder;

public class JsonAdaptedEventTest {

    private static final Person VALID_PLAYER =
            new PersonBuilder().withName("Alice").withRole(Role.PLAYER).build();
    private static final Person VALID_PLAYER_TWO =
            new PersonBuilder().withName("Bob").withRole(Role.PLAYER).build();
    private static final Person STAFF_PERSON =
            new PersonBuilder().withName("Charlie").withRole(Role.STAFF).build();

    private static final Map<String, Person> VALID_PERSON_MAP = Map.of(
            VALID_PLAYER.getName().toString(), VALID_PLAYER,
            VALID_PLAYER_TWO.getName().toString(), VALID_PLAYER_TWO
    );

    @Test
    public void toModelType_validMatchDetails_returnsMatch() throws Exception {

        JsonAdaptedEvent jsonAdaptedEvent = new JsonAdaptedEvent(
                "Liverpool",
                "MATCH",
                "2026-05-15 1600",
                List.of(VALID_PLAYER.getName().toString())
        );

        Match expectedMatch = new MatchBuilder()
                .withOpponentName("Liverpool")
                .withDate("2026-05-15 1600")
                .build();

        expectedMatch = new Match(
                expectedMatch.getEventName(),
                expectedMatch.getEventDate(),
                new EventPlayerList(Set.of(VALID_PLAYER))
        );

        assertEquals(expectedMatch, jsonAdaptedEvent.toModelType(VALID_PERSON_MAP));
    }

    @Test
    public void toModelType_nullOpponentName_throwsIllegalValueException() {
        JsonAdaptedEvent jsonAdaptedEvent = new JsonAdaptedEvent(
                null,
                "MATCH",
                "2026-05-15 1600",
                List.of(VALID_PLAYER.getName().toString())
        );

        assertThrows(IllegalValueException.class, () -> jsonAdaptedEvent.toModelType(VALID_PERSON_MAP));
    }

    @Test
    public void toModelType_invalidOpponentName_throwsIllegalValueException() {
        JsonAdaptedEvent jsonAdaptedEvent = new JsonAdaptedEvent(
                "@@@",
                "MATCH",
                "2026-05-15 1600",
                List.of(VALID_PLAYER.getName().toString())
        );

        IllegalValueException e = assertThrows(IllegalValueException.class, () ->
                jsonAdaptedEvent.toModelType(VALID_PERSON_MAP));
        assertEquals(EventName.MESSAGE_CONSTRAINTS, e.getMessage());
    }

    @Test
    public void toModelType_nullDate_throwsIllegalValueException() {
        JsonAdaptedEvent jsonAdaptedEvent = new JsonAdaptedEvent(
                "Liverpool",
                "MATCH",
                null,
                List.of(VALID_PLAYER.getName().toString())
        );

        assertThrows(IllegalValueException.class, () ->
                jsonAdaptedEvent.toModelType(VALID_PERSON_MAP));
    }

    @Test
    public void toModelType_invalidDate_throwsIllegalValueException() {
        JsonAdaptedEvent jsonAdaptedEvent = new JsonAdaptedEvent(
                "Liverpool",
                "MATCH",
                "2026/05/15 1600",
                List.of(VALID_PLAYER.getName().toString())
        );

        IllegalValueException e = assertThrows(IllegalValueException.class, () ->
                jsonAdaptedEvent.toModelType(VALID_PERSON_MAP));
        assertEquals(Date.MESSAGE_CONSTRAINTS, e.getMessage());
    }

    @Test
    public void toModelType_missingPlayer_throwsIllegalValueException() {
        JsonAdaptedEvent jsonAdaptedEvent = new JsonAdaptedEvent(
                "Liverpool",
                "MATCH",
                "2026-05-15 1600",
                List.of("Ghost Player")
        );

        IllegalValueException e = assertThrows(IllegalValueException.class, () ->
                jsonAdaptedEvent.toModelType(VALID_PERSON_MAP));
        assertEquals(String.format(JsonAdaptedEvent.MISSING_PLAYER_MESSAGE_FORMAT, "Ghost Player"),
                e.getMessage());
    }

    @Test
    public void toModelType_notAPlayer_throwsIllegalValueException() {
        Map<String, Person> personMapWithStaff = Map.of(
                STAFF_PERSON.getName().toString(), STAFF_PERSON
        );

        JsonAdaptedEvent jsonAdaptedEvent = new JsonAdaptedEvent(
                "Liverpool",
                "MATCH",
                "2026-05-15 1600",
                List.of(STAFF_PERSON.getName().toString())
        );

        IllegalValueException e = assertThrows(IllegalValueException.class, () ->
                        jsonAdaptedEvent.toModelType(personMapWithStaff));
        assertEquals(String.format(JsonAdaptedEvent.NOT_A_PLAYER_MESSAGE_FORMAT,
                STAFF_PERSON.getName().toString()), e.getMessage());
    }
}
