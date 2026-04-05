package seedu.address.model.event.match;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.event.Date;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventName;
import seedu.address.model.event.EventPlayerList;
import seedu.address.model.event.EventType;

/**
 * Represents a Match in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public class Match extends Event {

    private final Map<String, Integer> playerGoals;
    private final int opponentGoals;

    public Match(EventName opponentName, Date matchDate, EventPlayerList eventPlayerList) {
        this(opponentName, matchDate, eventPlayerList, new HashMap<>(), 0);
    }

    public Match(EventName opponentName, Date matchDate, EventPlayerList eventPlayerList,
                 Map<String, Integer> playerGoals, int opponentGoals) {
        super(opponentName, matchDate, EventType.MATCH, eventPlayerList);
        this.playerGoals = new HashMap<>(playerGoals);
        this.opponentGoals = opponentGoals;
    }

    public Map<String, Integer> getPlayerGoals() {
        return Collections.unmodifiableMap(playerGoals);
    }

    public int getGoalsForPlayer(String playerName) {
        return playerGoals.getOrDefault(playerName, 0);
    }

    public int getOpponentGoals() {
        return opponentGoals;
    }

    public int getTeamGoals() {
        return playerGoals.values().stream().mapToInt(Integer::intValue).sum();
    }

    public MatchResult getResult() {
        if (getTeamGoals() > opponentGoals) {
            return MatchResult.WIN;
        }
        if (getTeamGoals() < opponentGoals) {
            return MatchResult.LOSS;
        }
        return MatchResult.DRAW;
    }

    public boolean isWin() {
        return getResult() == MatchResult.WIN;
    }

    public boolean isLoss() {
        return getResult() == MatchResult.LOSS;
    }

    public boolean isDraw() {
        return getResult() == MatchResult.DRAW;
    }

    public Match withPlayerGoal(String playerName, int goals) {
        Map<String, Integer> updatedGoals = new HashMap<>(playerGoals);
        updatedGoals.put(playerName, goals);
        return new Match(getEventName(), getEventDate(), getEventPlayerList(), updatedGoals, opponentGoals);
    }

    public Match withOpponentGoals(int opponentGoals) {
        return new Match(getEventName(), getEventDate(), getEventPlayerList(), playerGoals, opponentGoals);
    }

    public Match removePlayerGoals(String playerName) {
        Map<String, Integer> updatedGoals = new HashMap<>(playerGoals);
        updatedGoals.remove(playerName);
        return new Match(getEventName(), getEventDate(), getEventPlayerList(), updatedGoals, opponentGoals);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("opponent name", eventName)
                .add("match date", eventDate)
                .add("players", eventPlayerList)
                .add("player goals", playerGoals)
                .add("opponent goals", opponentGoals)
                .toString();
    }
}