package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.UpdateCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.StatField;

/**
 * Parses input arguments and creates a new UpdateCommand object
 */
public class UpdateCommandParser implements Parser<UpdateCommand> {
    private static final int EXPECTED_ARGS = 3;
    private static final int ARG_INDEX = 0;
    private static final int ARG_STAT = 1;
    private static final int ARG_VALUE = 2;

    /**
     * Parses the given {@code String} of arguments in the context of the UpdateCommand
     * and returns an UpdateCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public UpdateCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String[] splitArgs = args.trim().split("\\s+");

        if (splitArgs.length != EXPECTED_ARGS) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE));
        }

        Index index;
        index = ParserUtil.parseIndex(splitArgs[ARG_INDEX]);

        StatField statField;
        try {
            statField = StatField.valueOf(splitArgs[ARG_STAT].toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE));
        }

        int value;
        try {
            value = Integer.parseInt(splitArgs[ARG_VALUE]);
        } catch (NumberFormatException e) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE));
        }

        return new UpdateCommand(index, statField, value);
    }
}
