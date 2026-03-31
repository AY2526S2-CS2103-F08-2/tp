package seedu.address.logic.parser;

import seedu.address.logic.commands.StatusAddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Status;

/**
 * Parses input arguments and creates a new StatusAddCommand object.
 */
public class StatusAddCommandParser implements Parser<StatusAddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the StatusAddCommand
     * and returns a StatusAddCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public StatusAddCommand parse(String args) throws ParseException {
        Status status = ParserUtil.parseStatus(
                AttributeParserUtil.parseRequiredValue(args, StatusAddCommand.MESSAGE_USAGE));
        return new StatusAddCommand(status);
    }
}
