package seedu.address.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.match.Date;
import seedu.address.model.match.Match;
import seedu.address.model.match.MatchPlayerList;
import seedu.address.model.match.OpponentName;
import seedu.address.model.person.Person;
import seedu.address.model.person.Role;

/**
 * Jackson-friendly version of {@link Match}.
 */
class JsonAdaptedMatch {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Match's %s field is missing!";
    public static final String MISSING_PLAYER_MESSAGE_FORMAT = "%s does not exist!";
    public static final String NOT_A_PLAYER_MESSAGE_FORMAT = "%s is not a player!";

    private final String opponentName;
    private final String date;
    private final List<String> players = new ArrayList<>();

    /**
     * Constructs a {@code JsonAdaptedMatch} with the given match details.
     */
    @JsonCreator
    public JsonAdaptedMatch(@JsonProperty("opponentName") String opponentName, @JsonProperty("date") String date,
                             @JsonProperty("players") List<String> players) {
        this.opponentName = opponentName;
        this.date = date;
        if (players != null) {
            this.players.addAll(players);
        }
    }

    /**
     * Converts a given {@code Match} into this class for Jackson use.
     */
    public JsonAdaptedMatch(Match source) {
        opponentName = source.getOpponentName().toString();
        date = source.getMatchDate().getDateWithInputFormat();
        players.addAll(source.getMatchPlayerList().asUnmodifiableObservableList().stream()
                .map(person -> person.getName().toString())
                .toList());
    }

    /**
     * Converts this Jackson-friendly adapted match object into the model's {@code Match} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted match.
     */
    public Match toModelType(Map<String, Person> personMap) throws IllegalValueException {
        if (opponentName == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    OpponentName.class.getSimpleName()));
        }
        if (!OpponentName.isValidName(opponentName)) {
            throw new IllegalValueException(OpponentName.MESSAGE_CONSTRAINTS);
        }
        final OpponentName modelOpponentName = new OpponentName(opponentName);

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

        final MatchPlayerList modelPlayers = new MatchPlayerList(playerList);

        return new Match(modelOpponentName, modelDate, modelPlayers);
    }
}
