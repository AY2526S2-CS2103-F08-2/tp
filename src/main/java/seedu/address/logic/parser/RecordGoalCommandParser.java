package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GOALS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PLAYER;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RecordGoalCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class RecordGoalCommandParser implements Parser<RecordGoalCommand> {

    @Override
    public RecordGoalCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_PLAYER, PREFIX_GOALS);

        if (argMultimap.getPreamble().isEmpty()
                || argMultimap.getValue(PREFIX_PLAYER).isEmpty()
                || argMultimap.getValue(PREFIX_GOALS).isEmpty()) {
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, RecordGoalCommand.MESSAGE_USAGE));
        }

        Index index = ParserUtil.parseIndex(argMultimap.getPreamble());
        String playerName = argMultimap.getValue(PREFIX_PLAYER).get().trim();
        int goals = parseGoals(argMultimap.getValue(PREFIX_GOALS).get().trim());

        return new RecordGoalCommand(index, playerName, goals);
    }

    private int parseGoals(String value) throws ParseException {
        try {
            int goals = Integer.parseInt(value);
            if (goals < 0) {
                throw new ParseException("Goals must be a non-negative integer.");
            }
            return goals;
        } catch (NumberFormatException e) {
            throw new ParseException("Goals must be a non-negative integer.");
        }
    }
}