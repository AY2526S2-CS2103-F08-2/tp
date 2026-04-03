package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.PositionAddCommand;
import seedu.address.model.person.Position;

public class PositionAddCommandParserTest {

    private final PositionAddCommandParser parser = new PositionAddCommandParser();

    @Test
    // VALID_CASE + EP_VALID
    public void parse_validArgs_returnsPositionAddCommand() {
        assertParseSuccess(parser, " Winger ", new PositionAddCommand(new Position("Winger")));
    }

    @Test
    // INVALID_CASE + EP_INVALID (invalid characters)
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, " #Winger ", Position.MESSAGE_CONSTRAINTS);
    }

    @Test
    // INVALID_CASE + BOUNDARY (blank input)
    public void parse_emptyArgs_throwsParseException() {
        assertParseFailure(parser, "   ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, PositionAddCommand.MESSAGE_USAGE));
    }
}
