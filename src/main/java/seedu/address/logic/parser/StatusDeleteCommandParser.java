package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

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
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, StatusDeleteCommand.MESSAGE_USAGE));
        }

        Status status = ParserUtil.parseStatus(trimmedArgs);
        return new StatusDeleteCommand(status);
    }
}

