package seedu.address.storage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Jackson-friendly version of one player-goals entry in a Match.
 */
public class JsonAdaptedGoalEntry {

    private final String playerName;
    private final int goals;

    @JsonCreator
    public JsonAdaptedGoalEntry(@JsonProperty("playerName") String playerName,
                                @JsonProperty("goals") int goals) {
        this.playerName = playerName;
        this.goals = goals;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getGoals() {
        return goals;
    }
}