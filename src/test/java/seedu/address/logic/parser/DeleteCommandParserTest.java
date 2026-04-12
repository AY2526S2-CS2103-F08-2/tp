package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteCommand.DeletionDecision;

public class DeleteCommandParserTest {

    private final DeleteCommandParser parser = new DeleteCommandParser();

    @Test
    public void parse_validIndexArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "1", DeleteCommand.forAmbiguousNumericInput("1", INDEX_FIRST_PERSON));
        assertParseSuccess(parser, "1 confirm", new DeleteCommand(INDEX_FIRST_PERSON, DeletionDecision.CONFIRM));
        assertParseSuccess(parser, "1 y",
                DeleteCommand.forAmbiguousNumericInput("1", INDEX_FIRST_PERSON, DeletionDecision.CONFIRM));
        assertParseSuccess(parser, "1 n",
                DeleteCommand.forAmbiguousNumericInput("1", INDEX_FIRST_PERSON, DeletionDecision.CANCEL));
    }

    @Test
    public void parse_validCriteriaArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "meier", new DeleteCommand("meier", null, DeletionDecision.UNDECIDED));
        assertParseSuccess(parser, "meier 2", new DeleteCommand("meier 2", null, DeletionDecision.UNDECIDED));
        assertParseSuccess(parser, "Amy Y", new DeleteCommand("Amy Y", null, DeletionDecision.UNDECIDED));
        assertParseSuccess(parser, "Alex 2", new DeleteCommand("Alex 2", null, DeletionDecision.UNDECIDED));
    }

    @Test
    public void parse_internalCriteriaFollowUp_returnsDeleteCommand() {
        assertParseSuccess(parser, "meier " + DeleteCommandParser.INTERNAL_MATCH_INDEX_MARKER + " 2",
                new DeleteCommand("meier", INDEX_SECOND_PERSON, DeletionDecision.UNDECIDED));
        assertParseSuccess(parser, "meier " + DeleteCommandParser.INTERNAL_DECISION_MARKER + " y",
                new DeleteCommand("meier", null, DeletionDecision.CONFIRM));
        assertParseSuccess(parser, "meier " + DeleteCommandParser.INTERNAL_MATCH_INDEX_MARKER + " 2 "
                        + DeleteCommandParser.INTERNAL_DECISION_MARKER + " y",
                new DeleteCommand("meier", INDEX_SECOND_PERSON, DeletionDecision.CONFIRM));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "meier " + DeleteCommandParser.INTERNAL_MATCH_INDEX_MARKER,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "meier " + DeleteCommandParser.INTERNAL_DECISION_MARKER,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }
}
