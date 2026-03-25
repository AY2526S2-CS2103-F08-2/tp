package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.PositionAddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Position;

/**
 * Parses input arguments and creates a new PositionAddCommand object.
 */
public class PositionAddCommandParser implements Parser<PositionAddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the PositionAddCommand
     * and returns a PositionAddCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public PositionAddCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, PositionAddCommand.MESSAGE_USAGE));
        }

        Position position = ParserUtil.parsePosition(trimmedArgs);
        return new PositionAddCommand(position);
    }
}

