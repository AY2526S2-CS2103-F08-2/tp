package seedu.address.logic.parser;

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
        Position position = ParserUtil.parsePosition(
                AttributeParserUtil.parseRequiredValue(args, PositionDeleteCommand.MESSAGE_USAGE));
        return new PositionDeleteCommand(position);
    }
}

