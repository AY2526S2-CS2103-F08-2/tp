package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteBulkCommand;
import seedu.address.logic.commands.DeleteBulkCommand.BulkDeletionDecision;
import seedu.address.model.tag.Tag;

public class DeleteBulkCommandParserTest {

    private final DeleteBulkCommandParser parser = new DeleteBulkCommandParser();

    @Test
    public void parse_validArgs_success() {
        assertParseSuccess(parser, " t/graduated",
                new DeleteBulkCommand(new Tag("graduated"), BulkDeletionDecision.UNDECIDED));
        assertParseSuccess(parser, " y t/graduated",
                new DeleteBulkCommand(new Tag("graduated"), BulkDeletionDecision.CONFIRM));
        assertParseSuccess(parser, " n t/graduated",
                new DeleteBulkCommand(new Tag("graduated"), BulkDeletionDecision.CANCEL));
    }

    @Test
    public void parse_invalidArgs_failure() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteBulkCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "graduated",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteBulkCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " maybe t/graduated",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteBulkCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " t/grad*",
                seedu.address.model.tag.Tag.MESSAGE_CONSTRAINTS);
    }
}
