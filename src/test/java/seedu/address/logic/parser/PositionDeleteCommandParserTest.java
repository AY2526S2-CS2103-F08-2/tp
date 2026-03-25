package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.PositionDeleteCommand;
import seedu.address.model.person.Position;

public class PositionDeleteCommandParserTest {

    private final PositionDeleteCommandParser parser = new PositionDeleteCommandParser();

    @Test
    public void parse_validArgs_returnsPositionDeleteCommand() {
        assertParseSuccess(parser, " Winger ", new PositionDeleteCommand(new Position("Winger")));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, " #Winger ", Position.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertParseFailure(parser, "   ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, PositionDeleteCommand.MESSAGE_USAGE));
    }
}

