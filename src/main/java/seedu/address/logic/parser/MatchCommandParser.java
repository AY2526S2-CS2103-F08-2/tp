package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PLAYER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POSITION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEAM;

import java.util.List;
import java.util.stream.Stream;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.MatchCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.Date;
import seedu.address.model.event.EventName;
import seedu.address.model.person.Position;
import seedu.address.model.person.Status;
import seedu.address.model.person.Team;

/**
 * Parses input arguments and creates a new MatchCommand object
 */
public class MatchCommandParser implements Parser<Command> {

    /**
     * Parses the given {@code String} of arguments in the context of the MatchCommand
     * and returns a MatchCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public MatchCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_DATE, PREFIX_PLAYER, PREFIX_STATUS,
                        PREFIX_POSITION, PREFIX_TEAM);
        String preamble = argMultimap.getPreamble();

        if (!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_DATE)
                || (!preamble.isEmpty())) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, MatchCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_DATE);
        EventName eventName = ParserUtil.parseEventName(argMultimap.getValue(PREFIX_NAME).get());
        Date date = ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get());
        List<String> playerNames = ParserUtil.parsePlayers(argMultimap.getAllValues(PREFIX_PLAYER));

        Team team = null;
        if (argMultimap.getValue(PREFIX_TEAM).isPresent()) {
            team = ParserUtil.parseTeam(argMultimap.getValue(PREFIX_TEAM).get());
        }
        Status status = null;
        if (argMultimap.getValue(PREFIX_STATUS).isPresent()) {
            status = ParserUtil.parseStatus(argMultimap.getValue(PREFIX_STATUS).get());
        }
        Position position = null;
        if (argMultimap.getValue(PREFIX_POSITION).isPresent()) {
            position = ParserUtil.parsePosition(argMultimap.getValue(PREFIX_POSITION).get());
        }

        return new MatchCommand(eventName, date, status, position, team, playerNames);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
