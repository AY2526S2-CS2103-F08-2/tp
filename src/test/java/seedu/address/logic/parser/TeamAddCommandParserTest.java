package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.TeamAddCommand;
import seedu.address.model.person.Team;

public class TeamAddCommandParserTest {

    private final TeamAddCommandParser parser = new TeamAddCommandParser();

    @Test
    // VALID_CASE + EP_VALID
    public void parse_validArgs_returnsTeamAddCommand() {
        assertParseSuccess(parser, " Reserve Team ", new TeamAddCommand(new Team("Reserve Team")));
    }

    @Test
    // INVALID_CASE + EP_INVALID (invalid characters)
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, " #Reserve ", Team.MESSAGE_CONSTRAINTS);
    }

    @Test
    // INVALID_CASE + BOUNDARY (blank input)
    public void parse_emptyArgs_throwsParseException() {
        assertParseFailure(parser, "   ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, TeamAddCommand.MESSAGE_USAGE));
    }
}
