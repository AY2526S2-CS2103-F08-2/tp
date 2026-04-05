package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.event.match.Match;
import seedu.address.model.event.match.MatchResult;
import seedu.address.model.person.Person;
import seedu.address.model.person.Player;
import seedu.address.model.person.PlayerStats;
import seedu.address.model.person.Role;

public class RecordGoalCommand extends Command {

    public static final String COMMAND_WORD = "recordgoal";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Records goals scored by a player in a match.\n"
            + "Parameters: INDEX pl/PLAYER_NAME g/GOALS\n"
            + "Example: " + COMMAND_WORD + " 2 pl/Alex Yeoh g/2";

    public static final String MESSAGE_SUCCESS =
            "Recorded %1$d goal(s) for %2$s in match %3$s";

    public static final String MESSAGE_NOT_A_MATCH =
            "Selected event is not a match.";

    public static final String MESSAGE_PLAYER_DOES_NOT_EXIST =
            "Player does not exist: %1$s";

    public static final String MESSAGE_NOT_A_PLAYER =
            "%1$s is not a player.";

    public static final String MESSAGE_PLAYER_NOT_IN_MATCH =
            "Player is not in this match: %1$s";

    private final Index index;
    private final String playerName;
    private final int goals;

    public RecordGoalCommand(Index index, String playerName, int goals) {
        requireNonNull(index);
        requireNonNull(playerName);

        this.index = index;
        this.playerName = playerName.trim();
        this.goals = goals;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Event> eventList = model.getEventList();
        if (index.getZeroBased() >= eventList.size()) {
            throw new CommandException(MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        Event event = eventList.get(index.getZeroBased());
        if (!(event instanceof Match)) {
            throw new CommandException(MESSAGE_NOT_A_MATCH);
        }

        Match match = (Match) event;

        Person person = model.getAddressBook().getPersonList().stream()
                .filter(p -> p.getName().toString().equals(playerName))
                .findFirst()
                .orElseThrow(() -> new CommandException(
                        String.format(MESSAGE_PLAYER_DOES_NOT_EXIST, playerName)));

        if (person.getRole() != Role.PLAYER) {
            throw new CommandException(String.format(MESSAGE_NOT_A_PLAYER, playerName));
        }

        if (!match.getEventPlayerList().getPlayerNames().contains(playerName)) {
            throw new CommandException(String.format(MESSAGE_PLAYER_NOT_IN_MATCH, playerName));
        }

        Player player = (Player) person;

        int oldGoalsInMatch = match.getGoalsForPlayer(playerName);
        int deltaGoals = goals - oldGoalsInMatch;

        MatchResult oldResult = match.getResult();
        Match editedMatch = match.withPlayerGoal(playerName, goals);
        MatchResult newResult = editedMatch.getResult();

        Player editedPlayer = createEditedPlayerWithGoalDelta(player, deltaGoals);

        model.setEvent(match, editedMatch);
        model.setPerson(player, editedPlayer);

        if (oldResult != newResult) {
            updateAllPlayersWinLossStats(model, match, oldResult, newResult, playerName);
        }

        return new CommandResult(String.format(
                MESSAGE_SUCCESS, goals, playerName, match.getEventName()));
    }

    private Player createEditedPlayerWithGoalDelta(Player player, int deltaGoals) {
        PlayerStats oldStats = player.getStats();

        PlayerStats newStats = new PlayerStats();
        newStats.setGoalsScored(oldStats.getGoalsScored() + deltaGoals);
        newStats.setMatchesWon(oldStats.getMatchesWon());
        newStats.setMatchesLost(oldStats.getMatchesLost());

        return new Player(
                player.getName(),
                player.getPhone(),
                player.getEmail(),
                player.getAddress(),
                player.getTags(),
                newStats,
                player.getTeam(),
                player.getStatus(),
                player.getPosition()
        );
    }

    /**
     * Updates match win/loss stats for all players in the match.
     * Skips the player already replaced in execute(), and re-fetches that player from model if needed.
     */
    private void updateAllPlayersWinLossStats(Model model, Match oldMatch,
                                              MatchResult oldResult, MatchResult newResult,
                                              String alreadyUpdatedPlayerName) {
        for (String currentPlayerName : oldMatch.getEventPlayerList().getPlayerNames()) {
            Person foundPerson = model.getAddressBook().getPersonList().stream()
                    .filter(p -> p.getName().toString().equals(currentPlayerName))
                    .findFirst()
                    .orElse(null);

            if (!(foundPerson instanceof Player)) {
                continue;
            }

            Player player = (Player) foundPerson;
            Player updatedPlayer = createEditedPlayerWithResultDelta(player, oldResult, newResult);
            model.setPerson(player, updatedPlayer);
        }
    }

    private Player createEditedPlayerWithResultDelta(Player player,
                                                     MatchResult oldResult,
                                                     MatchResult newResult) {
        PlayerStats oldStats = player.getStats();

        int wins = oldStats.getMatchesWon();
        int losses = oldStats.getMatchesLost();

        if (oldResult == MatchResult.WIN) {
            wins--;
        } else if (oldResult == MatchResult.LOSS) {
            losses--;
        }

        if (newResult == MatchResult.WIN) {
            wins++;
        } else if (newResult == MatchResult.LOSS) {
            losses++;
        }

        PlayerStats newStats = new PlayerStats();
        newStats.setGoalsScored(oldStats.getGoalsScored());
        newStats.setMatchesWon(wins);
        newStats.setMatchesLost(losses);

        return new Player(
                player.getName(),
                player.getPhone(),
                player.getEmail(),
                player.getAddress(),
                player.getTags(),
                newStats,
                player.getTeam(),
                player.getStatus(),
                player.getPosition()
        );
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof RecordGoalCommand)) {
            return false;
        }

        RecordGoalCommand otherCommand = (RecordGoalCommand) other;
        return index.equals(otherCommand.index)
                && playerName.equals(otherCommand.playerName)
                && goals == otherCommand.goals;
    }
}