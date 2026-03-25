package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEW_ATTRIBUTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OLD_ATTRIBUTE;

import seedu.address.logic.commands.StatusEditCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Status;

/**
 * Parses input arguments and creates a new StatusEditCommand object.
 */
public class StatusEditCommandParser implements Parser<StatusEditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the StatusEditCommand
     * and returns a StatusEditCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public StatusEditCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_OLD_ATTRIBUTE, PREFIX_NEW_ATTRIBUTE);

        if (!argMultimap.getValue(PREFIX_OLD_ATTRIBUTE).isPresent()
                || !argMultimap.getValue(PREFIX_NEW_ATTRIBUTE).isPresent()
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, StatusEditCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_OLD_ATTRIBUTE, PREFIX_NEW_ATTRIBUTE);

        Status oldStatus = ParserUtil.parseStatus(argMultimap.getValue(PREFIX_OLD_ATTRIBUTE).get());
        Status newStatus = ParserUtil.parseStatus(argMultimap.getValue(PREFIX_NEW_ATTRIBUTE).get());

        return new StatusEditCommand(oldStatus, newStatus);
    }
}


