package seedu.address.logic.parser;

import seedu.address.logic.commands.StatusDeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Status;

/**
 * Parses input arguments and creates a new StatusDeleteCommand object.
 */
public class StatusDeleteCommandParser implements Parser<StatusDeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the StatusDeleteCommand
     * and returns a StatusDeleteCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public StatusDeleteCommand parse(String args) throws ParseException {
        Status status = ParserUtil.parseStatus(
                AttributeParserUtil.parseRequiredValue(args, StatusDeleteCommand.MESSAGE_USAGE));
        return new StatusDeleteCommand(status);
    }
}
