package seedu.address.logic.parser;

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
        Position position = ParserUtil.parsePosition(
                AttributeParserUtil.parseRequiredValue(args, PositionAddCommand.MESSAGE_USAGE));
        return new PositionAddCommand(position);
    }
}
