package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.SetCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.StatField;

/**
 * Parses input arguments and creates a new SetCommand object
 */
public class SetCommandParser implements Parser<SetCommand> {
    private static final int EXPECTED_ARGS = 3;
    private static final int ARG_INDEX = 0;
    private static final int ARG_STAT = 1;
    private static final int ARG_VALUE = 2;

    /**
     * Parses the given {@code String} of arguments in the context of the SetCommand
     * and returns an SetCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SetCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String[] splitArgs = args.trim().split("\\s+");

        if (splitArgs.length != EXPECTED_ARGS) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetCommand.MESSAGE_USAGE));
        }

        Index index;
        index = ParserUtil.parseIndex(splitArgs[ARG_INDEX]);

        StatField statField;
        try {
            statField = StatField.valueOf(splitArgs[ARG_STAT].toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetCommand.MESSAGE_USAGE));
        }

        int value;
        try {
            value = Integer.parseInt(splitArgs[ARG_VALUE]);
        } catch (NumberFormatException e) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetCommand.MESSAGE_USAGE));
        }

        return new SetCommand(index, statField, value);
    }
}
