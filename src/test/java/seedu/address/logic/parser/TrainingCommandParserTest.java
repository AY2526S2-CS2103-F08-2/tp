package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PLAYER;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.TrainingCommand;
import seedu.address.model.event.Date;
import seedu.address.model.event.EventName;

/**
 * A class to test the TrainingCommand parser.
 */
public class TrainingCommandParserTest {

    private TrainingCommandParser parser = new TrainingCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        EventName expectedName = new EventName("Warm Up");
        Date expectedDate = new Date("2026-04-15 1600");
        List<String> expectedPlayers = List.of("Alex Yeoh", "Bernice Yu");

        // Normal input string
        assertParseSuccess(parser, " " + PREFIX_NAME + "Warm Up "
                                   + PREFIX_DATE + "2026-04-15 1600 "
                                   + PREFIX_PLAYER + "Alex Yeoh "
                                   + PREFIX_PLAYER + "Bernice Yu",
                new TrainingCommand(expectedName, expectedDate, expectedPlayers));

        // White spaces in between
        assertParseSuccess(parser, "    " + PREFIX_NAME + "Warm Up  "
                                   + PREFIX_DATE + "2026-04-15 1600    "
                                   + PREFIX_PLAYER + "Alex Yeoh",
                new TrainingCommand(expectedName, expectedDate, List.of("Alex Yeoh")));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, TrainingCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, " " + PREFIX_DATE + "2026-04-15 1600", expectedMessage);

        // missing date prefix
        assertParseFailure(parser, " " + PREFIX_NAME + "Warm Up", expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, "", expectedMessage);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, TrainingCommand.MESSAGE_USAGE);

        // non-empty preamble (garbage before the first prefix)
        assertParseFailure(parser, " some random text " + PREFIX_NAME + "Warm Up "
                                   + PREFIX_DATE + "2026-04-15 1600", expectedMessage);
    }
}
