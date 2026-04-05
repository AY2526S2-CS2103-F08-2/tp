package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.StatusDeleteCommand;
import seedu.address.model.person.Status;

public class StatusDeleteCommandParserTest {

    private final StatusDeleteCommandParser parser = new StatusDeleteCommandParser();

    @Test
    // VALID_CASE + EP_VALID
    public void parse_validArgs_returnsStatusDeleteCommand() {
        assertParseSuccess(parser, " Rehab ", new StatusDeleteCommand(new Status("Rehab")));
    }

    @Test
    // INVALID_CASE + EP_INVALID (invalid characters)
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, " #Rehab ", Status.MESSAGE_CONSTRAINTS);
    }

    @Test
    // INVALID_CASE + BOUNDARY (blank input)
    public void parse_emptyArgs_throwsParseException() {
        assertParseFailure(parser, "   ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, StatusDeleteCommand.MESSAGE_USAGE));
    }
}
