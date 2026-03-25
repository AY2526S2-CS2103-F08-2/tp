package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Team;

/**
 * Jackson-friendly version of {@link Team}.
 */
class JsonAdaptedTeam {

    private final String teamName;

    /**
     * Constructs a {@code JsonAdaptedTeam} with the given {@code teamName}.
     */
    @JsonCreator
    public JsonAdaptedTeam(String teamName) {
        this.teamName = teamName;
    }

    /**
     * Converts a given {@code Team} into this class for Jackson use.
     */
    public JsonAdaptedTeam(Team source) {
        teamName = source.value;
    }

    @JsonValue
    public String getTeamName() {
        return teamName;
    }

    /**
     * Converts this Jackson-friendly adapted team object into the model's {@code Team} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted team.
     */
    public Team toModelType() throws IllegalValueException {
        if (!Team.isValidTeamName(teamName)) {
            throw new IllegalValueException(Team.MESSAGE_CONSTRAINTS);
        }
        return new Team(teamName);
    }
}

