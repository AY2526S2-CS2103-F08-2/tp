package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.PositionEditCommand;
import seedu.address.model.person.Position;

public class PositionEditCommandParserTest {

    private final PositionEditCommandParser parser = new PositionEditCommandParser();

    @Test
    public void parse_validArgs_returnsPositionEditCommand() {
        assertParseSuccess(parser, " old/Defender new/Center Back",
                new PositionEditCommand(new Position("Defender"), new Position("Center Back")));
    }

    @Test
    public void parse_missingOldPrefix_throwsParseException() {
        assertParseFailure(parser, " Defender new/Center Back",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, PositionEditCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingNewPrefix_throwsParseException() {
        assertParseFailure(parser, " old/Defender Center Back",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, PositionEditCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_duplicatePrefixes_throwsParseException() {
        assertParseFailure(parser, " old/Defender old/Forward new/Center Back",
                Messages.getErrorMessageForDuplicatePrefixes(CliSyntax.PREFIX_OLD_ATTRIBUTE));
    }

    @Test
    public void parse_invalidPositionValue_throwsParseException() {
        assertParseFailure(parser, " old/#Bad new/Center Back", Position.MESSAGE_CONSTRAINTS);
    }
}

