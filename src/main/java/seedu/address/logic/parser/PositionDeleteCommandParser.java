package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.PositionDeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Position;

/**
 * Parses input arguments and creates a new PositionDeleteCommand object.
 */
public class PositionDeleteCommandParser implements Parser<PositionDeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the PositionDeleteCommand
     * and returns a PositionDeleteCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public PositionDeleteCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, PositionDeleteCommand.MESSAGE_USAGE));
        }

        Position position = ParserUtil.parsePosition(trimmedArgs);
        return new PositionDeleteCommand(position);
    }
}


