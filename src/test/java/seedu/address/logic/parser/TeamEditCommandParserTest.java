package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.TeamEditCommand;
import seedu.address.model.person.Team;

public class TeamEditCommandParserTest {

    private final TeamEditCommandParser parser = new TeamEditCommandParser();

    @Test
    // VALID_CASE + EP_VALID
    public void parse_validArgs_returnsTeamEditCommand() {
        assertParseSuccess(parser, " old/First Team new/Reserve Team",
                new TeamEditCommand(new Team("First Team"), new Team("Reserve Team")));
    }

    @Test
    // INVALID_CASE + EP_INVALID (missing required prefix)
    public void parse_missingOldPrefix_throwsParseException() {
        assertParseFailure(parser, " First Team new/Reserve Team",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, TeamEditCommand.MESSAGE_USAGE));
    }

    @Test
    // INVALID_CASE + EP_INVALID (missing required prefix)
    public void parse_missingNewPrefix_throwsParseException() {
        assertParseFailure(parser, " old/First Team Reserve Team",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, TeamEditCommand.MESSAGE_USAGE));
    }

    @Test
    // INVALID_CASE + SINGLE_INVALID (duplicate single-value prefix)
    public void parse_duplicatePrefixes_throwsParseException() {
        assertParseFailure(parser, " old/First Team old/Second Team new/Reserve Team",
                Messages.getErrorMessageForDuplicatePrefixes(CliSyntax.PREFIX_OLD_ATTRIBUTE));
    }

    @Test
    // INVALID_CASE + EP_INVALID (invalid token format)
    public void parse_invalidTeamValue_throwsParseException() {
        assertParseFailure(parser, " old/#Bad new/Reserve Team", Team.MESSAGE_CONSTRAINTS);
    }
}
