package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.StatusEditCommand;
import seedu.address.model.person.Status;

public class StatusEditCommandParserTest {

    private final StatusEditCommandParser parser = new StatusEditCommandParser();

    @Test
    // VALID_CASE + EP_VALID
    public void parse_validArgs_returnsStatusEditCommand() {
        assertParseSuccess(parser, " old/Active new/Rehab",
                new StatusEditCommand(new Status("Active"), new Status("Rehab")));
    }

    @Test
    // INVALID_CASE + EP_INVALID (missing required prefix)
    public void parse_missingOldPrefix_throwsParseException() {
        assertParseFailure(parser, " Active new/Rehab",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, StatusEditCommand.MESSAGE_USAGE));
    }

    @Test
    // INVALID_CASE + EP_INVALID (missing required prefix)
    public void parse_missingNewPrefix_throwsParseException() {
        assertParseFailure(parser, " old/Active Rehab",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, StatusEditCommand.MESSAGE_USAGE));
    }

    @Test
    // INVALID_CASE + SINGLE_INVALID (duplicate single-value prefix)
    public void parse_duplicatePrefixes_throwsParseException() {
        assertParseFailure(parser, " old/Active old/Unavailable new/Rehab",
                Messages.getErrorMessageForDuplicatePrefixes(CliSyntax.PREFIX_OLD_ATTRIBUTE));
    }

    @Test
    // INVALID_CASE + EP_INVALID (invalid token format)
    public void parse_invalidStatusValue_throwsParseException() {
        assertParseFailure(parser, " old/#Bad new/Rehab", Status.MESSAGE_CONSTRAINTS);
    }
}
