package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

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
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, StatusAddCommand.MESSAGE_USAGE));
        }

        Status status = ParserUtil.parseStatus(trimmedArgs);
        return new StatusAddCommand(status);
    }
}

