package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EventDeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EventDeleteCommand object
 */
public class EventDeleteCommandParser implements Parser<EventDeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EventDeleteCommand
     * and returns a EventDeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EventDeleteCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new EventDeleteCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EventDeleteCommand.MESSAGE_USAGE), pe);
        }
    }
}
