package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PLAYER;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.match.Date;
import seedu.address.model.match.Match;
import seedu.address.model.match.MatchPlayerList;
import seedu.address.model.match.OpponentName;
import seedu.address.model.person.Person;
import seedu.address.model.person.Role;

/**
 * Adds a match to the address book.
 */
public class MatchCommand extends Command {

    public static final String COMMAND_WORD = "match";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a match to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "OPPONENT NAME "
            + PREFIX_DATE + "DATE "
            + "[" + PREFIX_PLAYER + "PLAYER]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Manchester United "
            + PREFIX_DATE + "2026-04-15 1600 "
            + PREFIX_PLAYER + "Alex Yeoh ";

    public static final String MESSAGE_SUCCESS = "New Match added: %1$s";
    public static final String MESSAGE_DUPLICATE_MATCH = "This match already exists in the address book!";
    public static final String MESSAGE_PERSON_DOES_NOT_EXIST = "%s does not exist in the address book!";
    public static final String MESSAGE_NOT_A_PLAYER = "%s is not a player!";
    public static final String MESSAGE_ADD_DUPLICATE_PLAYER = "Cannot add same player twice!";

    private final OpponentName opponentName;
    private final Date date;
    private final List<String> playerNames;

    /**
     * Creates a MatchCommand. {@code Match} is not created yet as {@code playerNames} has not been validated.
     */
    public MatchCommand(OpponentName opponentName, Date date, List<String> playerNames) {
        requireNonNull(opponentName);
        requireNonNull(date);
        requireNonNull(playerNames);
        this.opponentName = opponentName;
        this.date = date;
        this.playerNames = playerNames;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException, RuntimeException {
        requireNonNull(model);

        List<Person> playerList = new ArrayList<>();

        for (String playerName : playerNames) {
            Person person = model.getAddressBook().getPersonList().stream()
                    .filter(p -> p.getName().toString().equals(playerName.trim()))
                    .findFirst()
                    .orElseThrow(() -> new CommandException(String.format(MESSAGE_PERSON_DOES_NOT_EXIST, playerName)));

            if (person.getRole() != Role.PLAYER) {
                throw new CommandException(String.format(MESSAGE_NOT_A_PLAYER, playerName));
            }

            playerList.add(person);
        }

        if (playerList.size() != new HashSet<>(playerList).size()) {
            throw new CommandException(MESSAGE_ADD_DUPLICATE_PLAYER);
        }

        MatchPlayerList matchPlayerList = new MatchPlayerList(playerList);
        Match toAdd = new Match(opponentName, date, matchPlayerList);

        if (model.hasMatch(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_MATCH);
        }

        model.addMatch(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof MatchCommand)) {
            return false;
        }

        MatchCommand otherMatchCommand = (MatchCommand) other;
        return opponentName.equals(otherMatchCommand.opponentName)
                && date.equals(otherMatchCommand.date)
                && playerNames.equals(otherMatchCommand.playerNames);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("Opponent Name: ", opponentName)
                .add("Date: ", date)
                .add("Player Names: ", playerNames)
                .toString();
    }
}
