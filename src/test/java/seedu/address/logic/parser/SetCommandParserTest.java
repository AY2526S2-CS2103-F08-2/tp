package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.SetCommand;
import seedu.address.model.person.StatField;

public class SetCommandParserTest {

    private final SetCommandParser parser = new SetCommandParser();

    @Test
    public void parse_validArgs_success() {
        assertParseSuccess(parser, "1 goals 10",
                new SetCommand(Index.fromOneBased(1), StatField.GOALS, 10));

        assertParseSuccess(parser, "2 wins 3",
                new SetCommand(Index.fromOneBased(2), StatField.WINS, 3));

        assertParseSuccess(parser, "3 losses 0",
                new SetCommand(Index.fromOneBased(3), StatField.LOSSES, 0));
    }

    @Test
    public void parse_extraWhitespace_success() {
        assertParseSuccess(parser, "   1   goals   10   ",
                new SetCommand(Index.fromOneBased(1), StatField.GOALS, 10));
    }

    @Test
    public void parse_invalidArgCount_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetCommand.MESSAGE_USAGE);

        assertParseFailure(parser, "", expectedMessage);
        assertParseFailure(parser, "1", expectedMessage);
        assertParseFailure(parser, "1 goals", expectedMessage);
        assertParseFailure(parser, "1 goals 10 extra", expectedMessage);
    }

    @Test
    public void parse_invalidStat_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetCommand.MESSAGE_USAGE);

        assertParseFailure(parser, "1 invalidstat 10", expectedMessage);
        assertParseFailure(parser, "1 . 10", expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetCommand.MESSAGE_USAGE);

        assertParseFailure(parser, "1 goals abc", expectedMessage);
        assertParseFailure(parser, "1 goals 1.5", expectedMessage);
    }
}
