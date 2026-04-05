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

public class RecordOpponentGoalCommand extends Command {

    public static final String COMMAND_WORD = "recordoppgoal";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Records opponent goals for a match.\n"
            + "Parameters: INDEX og/OPPONENT_GOALS\n"
            + "Example: " + COMMAND_WORD + " 2 og/1";

    public static final String MESSAGE_NOT_A_MATCH =
            "Selected event is not a match.";

    public static final String MESSAGE_SUCCESS =
            "Recorded opponent goals: %1$d for match %2$s";

    private final Index index;
    private final int opponentGoals;

    public RecordOpponentGoalCommand(Index index, int opponentGoals) {
        requireNonNull(index);
        this.index = index;
        this.opponentGoals = opponentGoals;
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

        MatchResult oldResult = match.getResult();
        Match editedMatch = match.withOpponentGoals(opponentGoals);
        MatchResult newResult = editedMatch.getResult();

        model.setEvent(match, editedMatch);

        if (oldResult != newResult) {
            updateAllPlayersWinLossStats(model, match, oldResult, newResult);
        }

        return new CommandResult(String.format(
                MESSAGE_SUCCESS, opponentGoals, match.getEventName()));
    }

    private void updateAllPlayersWinLossStats(Model model, Match oldMatch,
                                              MatchResult oldResult, MatchResult newResult) {
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

        if (!(other instanceof RecordOpponentGoalCommand)) {
            return false;
        }

        RecordOpponentGoalCommand otherCommand = (RecordOpponentGoalCommand) other;
        return index.equals(otherCommand.index)
                && opponentGoals == otherCommand.opponentGoals;
    }
}