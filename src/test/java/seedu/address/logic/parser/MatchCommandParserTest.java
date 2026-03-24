package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_MATCH;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_OPPONENT_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.MATCH_NAME_DESC_PLAYER_BEN;
import static seedu.address.logic.commands.CommandTestUtil.OPPONENT_NAME_DESC_MATCH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_PLAYER_BEN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_OPPONENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.MatchCommand;
import seedu.address.model.match.Date;
import seedu.address.model.match.OpponentName;


public class MatchCommandParserTest {

    private final MatchCommandParser parser = new MatchCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        String userInput = OPPONENT_NAME_DESC_MATCH + DATE_DESC_MATCH + MATCH_NAME_DESC_PLAYER_BEN;

        MatchCommand expectedCommand = new MatchCommand(
                new OpponentName(VALID_OPPONENT_NAME),
                new Date(VALID_DATE),
                List.of(VALID_NAME_PLAYER_BEN)
        );

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_optionalPlayersAbsent_success() {
        String userInput = OPPONENT_NAME_DESC_MATCH + DATE_DESC_MATCH;

        MatchCommand expectedCommand = new MatchCommand(
                new OpponentName(VALID_OPPONENT_NAME),
                new Date(VALID_DATE),
                List.of()
        );

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingRequiredPrefix_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MatchCommand.MESSAGE_USAGE);

        assertParseFailure(parser, DATE_DESC_MATCH + MATCH_NAME_DESC_PLAYER_BEN, expectedMessage);
        assertParseFailure(parser, OPPONENT_NAME_DESC_MATCH + MATCH_NAME_DESC_PLAYER_BEN, expectedMessage);
        assertParseFailure(parser, MATCH_NAME_DESC_PLAYER_BEN, expectedMessage);
    }

    @Test
    public void parse_invalidOpponentName_failure() {
        assertParseFailure(parser, INVALID_OPPONENT_NAME_DESC + DATE_DESC_MATCH,
                OpponentName.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidDate_failure() {
        assertParseFailure(parser, OPPONENT_NAME_DESC_MATCH + INVALID_DATE_DESC, Date.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_duplicateNamePrefix_failure() {
        String userInput = MATCH_NAME_DESC_PLAYER_BEN + " " + PREFIX_NAME + OPPONENT_NAME_DESC_MATCH + DATE_DESC_MATCH;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));
    }
}
