package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.StatusAddCommand;
import seedu.address.model.person.Status;

public class StatusAddCommandParserTest {

    private final StatusAddCommandParser parser = new StatusAddCommandParser();

    @Test
    public void parse_validArgs_returnsStatusAddCommand() {
        assertParseSuccess(parser, " Rehab ", new StatusAddCommand(new Status("Rehab")));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, " #Rehab ", Status.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        assertParseFailure(parser, "   ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, StatusAddCommand.MESSAGE_USAGE));
    }
}

