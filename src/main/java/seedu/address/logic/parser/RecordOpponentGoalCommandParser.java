package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OPPONENT_GOALS;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RecordOpponentGoalCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class RecordOpponentGoalCommandParser implements Parser<RecordOpponentGoalCommand> {

    @Override
    public RecordOpponentGoalCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_OPPONENT_GOALS);

        if (argMultimap.getPreamble().isEmpty()
                || argMultimap.getValue(PREFIX_OPPONENT_GOALS).isEmpty()) {
            throw new ParseException(String.format(
                    MESSAGE_INVALID_COMMAND_FORMAT, RecordOpponentGoalCommand.MESSAGE_USAGE));
        }

        Index index = ParserUtil.parseIndex(argMultimap.getPreamble());
        int opponentGoals = parseGoals(argMultimap.getValue(PREFIX_OPPONENT_GOALS).get().trim());

        return new RecordOpponentGoalCommand(index, opponentGoals);
    }

    private int parseGoals(String value) throws ParseException {
        try {
            int goals = Integer.parseInt(value);
            if (goals < 0) {
                throw new ParseException("Opponent goals must be a non-negative integer.");
            }
            return goals;
        } catch (NumberFormatException e) {
            throw new ParseException("Opponent goals must be a non-negative integer.");
        }
    }
}