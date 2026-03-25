package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_MATCH;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_OPPONENT_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.MATCH_NAME_DESC_PLAYER_BEN;
import static seedu.address.logic.commands.CommandTestUtil.OPPONENT_NAME_DESC_MATCH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_TYPE_MATCH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_PLAYER_BEN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_OPPONENT_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;

import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.EventEditCommand;
import seedu.address.logic.commands.EventEditCommand.EditEventDescriptor;
import seedu.address.model.event.Date;
import seedu.address.model.event.EventName;
import seedu.address.testutil.EditEventDescriptorBuilder;

public class EventEditCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EventEditCommand.MESSAGE_USAGE);

    private EventEditCommandParser parser = new EventEditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        assertParseFailure(parser, OPPONENT_NAME_DESC_MATCH, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "1", EventEditCommand.MESSAGE_NOT_EDITED);
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        assertParseFailure(parser, "-5" + OPPONENT_NAME_DESC_MATCH, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "0" + OPPONENT_NAME_DESC_MATCH, MESSAGE_INVALID_FORMAT);
        assertParseFailure(parser, "1 some random string" + OPPONENT_NAME_DESC_MATCH, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_OPPONENT_NAME_DESC, EventName.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + INVALID_DATE_DESC, Date.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        EditEventDescriptor expectedEditEventDescriptor = new EditEventDescriptorBuilder()
                .withEventName(VALID_OPPONENT_NAME)
                .withEventDate(VALID_DATE)
                .withEventType(VALID_EVENT_TYPE_MATCH)
                .withEventPlayerNames(Set.of(VALID_NAME_PLAYER_BEN))
                .build();

        assertParseSuccess(parser,
                "1" + OPPONENT_NAME_DESC_MATCH + DATE_DESC_MATCH
                        + " " + PREFIX_EVENT_TYPE + VALID_EVENT_TYPE_MATCH
                        + MATCH_NAME_DESC_PLAYER_BEN,
                new EventEditCommand(INDEX_FIRST_EVENT, expectedEditEventDescriptor));
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        EditEventDescriptor descriptor = new EditEventDescriptorBuilder()
                .withEventName(VALID_OPPONENT_NAME)
                .build();
        assertParseSuccess(parser, "1" + OPPONENT_NAME_DESC_MATCH,
                new EventEditCommand(INDEX_FIRST_EVENT, descriptor));

        descriptor = new EditEventDescriptorBuilder()
                .withEventDate(VALID_DATE)
                .build();
        assertParseSuccess(parser, "1" + DATE_DESC_MATCH,
                new EventEditCommand(INDEX_FIRST_EVENT, descriptor));

        descriptor = new EditEventDescriptorBuilder()
                .withEventType(VALID_EVENT_TYPE_MATCH)
                .build();
        assertParseSuccess(parser, "1" + " " + PREFIX_EVENT_TYPE + VALID_EVENT_TYPE_MATCH,
                new EventEditCommand(INDEX_FIRST_EVENT, descriptor));

        descriptor = new EditEventDescriptorBuilder()
                .withEventPlayerNames(Set.of(VALID_NAME_PLAYER_BEN))
                .build();
        assertParseSuccess(parser, "1" + MATCH_NAME_DESC_PLAYER_BEN,
                new EventEditCommand(INDEX_FIRST_EVENT, descriptor));
    }

    @Test
    public void parse_repeatedNonPlayerValue_failure() {
        assertParseFailure(parser,
                "1" + OPPONENT_NAME_DESC_MATCH + OPPONENT_NAME_DESC_MATCH,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));
    }

    @Test
    public void parse_resetPlayers_success() {
        EditEventDescriptor descriptor = new EditEventDescriptorBuilder()
                .withEventPlayerNames(Set.of())
                .build();

        assertParseSuccess(parser, "1 pl/",
                new EventEditCommand(INDEX_FIRST_EVENT, descriptor));
    }
}
