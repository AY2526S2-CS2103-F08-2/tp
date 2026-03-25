package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEW_ATTRIBUTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OLD_ATTRIBUTE;

import seedu.address.logic.commands.PositionEditCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Position;

/**
 * Parses input arguments and creates a new PositionEditCommand object.
 */
public class PositionEditCommandParser implements Parser<PositionEditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the PositionEditCommand
     * and returns a PositionEditCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public PositionEditCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_OLD_ATTRIBUTE, PREFIX_NEW_ATTRIBUTE);

        if (!argMultimap.getValue(PREFIX_OLD_ATTRIBUTE).isPresent()
                || !argMultimap.getValue(PREFIX_NEW_ATTRIBUTE).isPresent()
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, PositionEditCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_OLD_ATTRIBUTE, PREFIX_NEW_ATTRIBUTE);

        Position oldPosition = ParserUtil.parsePosition(argMultimap.getValue(PREFIX_OLD_ATTRIBUTE).get());
        Position newPosition = ParserUtil.parsePosition(argMultimap.getValue(PREFIX_NEW_ATTRIBUTE).get());

        return new PositionEditCommand(oldPosition, newPosition);
    }
}

