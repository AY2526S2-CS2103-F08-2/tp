package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PLAYER;

import java.util.List;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AttendanceMarkCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new AttendanceMarkCommand object
 */
public class AttendanceMarkCommandParser implements Parser<AttendanceMarkCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the AttendanceMarkCommand
     * and returns a AttendanceMarkCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AttendanceMarkCommand parse(String args) throws ParseException {
        Index index;
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PLAYER);

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AttendanceMarkCommand.MESSAGE_USAGE),
                    pe);
        }

        if (!arePrefixesPresent(argMultimap, PREFIX_PLAYER)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AttendanceMarkCommand.MESSAGE_USAGE));
        }

        List<String> playerNames = ParserUtil.parsePlayers(argMultimap.getAllValues(PREFIX_PLAYER));

        return new AttendanceMarkCommand(index, playerNames);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
