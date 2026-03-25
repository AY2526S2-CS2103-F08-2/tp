package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.UpdateCommand;
import seedu.address.model.person.StatField;

public class UpdateCommandParserTest {

    private final UpdateCommandParser parser = new UpdateCommandParser();

    @Test
    public void parse_validArgs_success() {
        assertParseSuccess(parser, "1 goals 10",
                new UpdateCommand(Index.fromOneBased(1), StatField.GOALS, 10));

        assertParseSuccess(parser, "2 wins 3",
                new UpdateCommand(Index.fromOneBased(2), StatField.WINS, 3));

        assertParseSuccess(parser, "3 losses 0",
                new UpdateCommand(Index.fromOneBased(3), StatField.LOSSES, 0));
    }

    @Test
    public void parse_extraWhitespace_success() {
        assertParseSuccess(parser, "   1   wins   5   ",
                new UpdateCommand(Index.fromOneBased(1), StatField.WINS, 5));
    }

    @Test
    public void parse_invalidArgCount_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE);

        assertParseFailure(parser, "", expectedMessage);
        assertParseFailure(parser, "1", expectedMessage);
        assertParseFailure(parser, "1 wins", expectedMessage);
        assertParseFailure(parser, "1 wins 5 extra", expectedMessage);
    }

    @Test
    public void parse_invalidStat_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE);

        assertParseFailure(parser, "1 assists 5", expectedMessage);
        assertParseFailure(parser, "1 goal 5", expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE);

        assertParseFailure(parser, "1 wins abc", expectedMessage);
        assertParseFailure(parser, "1 wins 1.5", expectedMessage);
    }
}
