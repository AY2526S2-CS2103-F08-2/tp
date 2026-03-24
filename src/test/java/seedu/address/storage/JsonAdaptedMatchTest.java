package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.match.Match;
import seedu.address.model.person.Person;
import seedu.address.model.person.Role;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.testutil.MatchBuilder;
import seedu.address.testutil.PersonBuilder;

public class JsonAdaptedMatchTest {

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
        Match match = new MatchBuilder()
                .withOpponentName("Liverpool")
                .withDate("2026-05-15 1600")
                .build();

        JsonAdaptedMatch jsonAdaptedMatch = new JsonAdaptedMatch(
                "Liverpool",
                "2026-05-15 1600",
                List.of(VALID_PLAYER.getName().toString(), VALID_PLAYER_TWO.getName().toString())
        );

        Match expectedMatch = new MatchBuilder()
                .withOpponentName("Liverpool")
                .withDate("2026-05-15 1600")
                .build();

        expectedMatch = new Match(
                expectedMatch.getOpponentName(),
                expectedMatch.getMatchDate(),
                new seedu.address.model.match.MatchPlayerList(List.of(VALID_PLAYER, VALID_PLAYER_TWO))
        );

        assertEquals(expectedMatch, jsonAdaptedMatch.toModelType(VALID_PERSON_MAP));
    }

    @Test
    public void toModelType_nullOpponentName_throwsIllegalValueException() {
        JsonAdaptedMatch jsonAdaptedMatch = new JsonAdaptedMatch(
                null,
                "2026-05-15 1600",
                List.of(VALID_PLAYER.getName().toString())
        );

        assertThrows(IllegalValueException.class, () -> jsonAdaptedMatch.toModelType(VALID_PERSON_MAP));
    }

    @Test
    public void toModelType_invalidOpponentName_throwsIllegalValueException() {
        JsonAdaptedMatch jsonAdaptedMatch = new JsonAdaptedMatch(
                "@@@",
                "2026-05-15 1600",
                List.of(VALID_PLAYER.getName().toString())
        );

        IllegalValueException e = assertThrows(IllegalValueException.class, () ->
                jsonAdaptedMatch.toModelType(VALID_PERSON_MAP));
        assertEquals(seedu.address.model.match.OpponentName.MESSAGE_CONSTRAINTS, e.getMessage());
    }

    @Test
    public void toModelType_nullDate_throwsIllegalValueException() {
        JsonAdaptedMatch jsonAdaptedMatch = new JsonAdaptedMatch(
                "Liverpool",
                null,
                List.of(VALID_PLAYER.getName().toString())
        );

        assertThrows(IllegalValueException.class, () ->
                jsonAdaptedMatch.toModelType(VALID_PERSON_MAP));
    }

    @Test
    public void toModelType_invalidDate_throwsIllegalValueException() {
        JsonAdaptedMatch jsonAdaptedMatch = new JsonAdaptedMatch(
                "Liverpool",
                "2026/05/15 1600",
                List.of(VALID_PLAYER.getName().toString())
        );

        IllegalValueException e = assertThrows(IllegalValueException.class, () ->
                jsonAdaptedMatch.toModelType(VALID_PERSON_MAP));
        assertEquals(seedu.address.model.match.Date.MESSAGE_CONSTRAINTS, e.getMessage());
    }

    @Test
    public void toModelType_missingPlayer_throwsIllegalValueException() {
        JsonAdaptedMatch jsonAdaptedMatch = new JsonAdaptedMatch(
                "Liverpool",
                "2026-05-15 1600",
                List.of("Ghost Player")
        );

        IllegalValueException e = assertThrows(IllegalValueException.class, () ->
                jsonAdaptedMatch.toModelType(VALID_PERSON_MAP));
        assertEquals(String.format(JsonAdaptedMatch.MISSING_PLAYER_MESSAGE_FORMAT, "Ghost Player"),
                e.getMessage());
    }

    @Test
    public void toModelType_notAPlayer_throwsIllegalValueException() {
        Map<String, Person> personMapWithStaff = Map.of(
                STAFF_PERSON.getName().toString(), STAFF_PERSON
        );

        JsonAdaptedMatch jsonAdaptedMatch = new JsonAdaptedMatch(
                "Liverpool",
                "2026-05-15 1600",
                List.of(STAFF_PERSON.getName().toString())
        );

        IllegalValueException e = assertThrows(IllegalValueException.class, () ->
                        jsonAdaptedMatch.toModelType(personMapWithStaff));
        assertEquals(String.format(JsonAdaptedMatch.NOT_A_PLAYER_MESSAGE_FORMAT,
                STAFF_PERSON.getName().toString()), e.getMessage());
    }

    @Test
    public void toModelType_duplicatePlayers_throwsException() {
        JsonAdaptedMatch jsonAdaptedMatch = new JsonAdaptedMatch(
                "Liverpool",
                "2026-05-15 1600",
                List.of(VALID_PLAYER.getName().toString(), VALID_PLAYER.getName().toString())
        );

        assertThrows(DuplicatePersonException.class, () -> jsonAdaptedMatch.toModelType(VALID_PERSON_MAP));
    }
}
