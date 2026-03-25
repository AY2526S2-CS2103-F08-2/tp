package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Role;
import seedu.address.model.person.RoleFilteredNameContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "Alice Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedFindCommand);
    }

    @Test
    public void parse_roleWithoutKeyword_throwsParseException() {
        assertParseFailure(parser, "r/player", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                                                                        FindCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "r/staff", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                                                                        FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validRoleArgs_returnsFindCommand() {
        FindCommand expectedPlayerCommand =
                new FindCommand(new RoleFilteredNameContainsKeywordsPredicate(Role.PLAYER, Arrays.asList("Alice")));
        FindCommand expectedStaffCommand =
                new FindCommand(new RoleFilteredNameContainsKeywordsPredicate(Role.STAFF, Arrays.asList("Bob", "Tan")));

        assertParseSuccess(parser, "r/player Alice", expectedPlayerCommand);
        assertParseSuccess(parser, "r/staff Bob Tan", expectedStaffCommand);
    }

    @Test
    public void parse_roleLikeKeywords_treatedAsGeneralKeywords() {
        FindCommand expectedFindCommand =
                new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList("staff", "Ben")));
        assertParseSuccess(parser, "staff Ben", expectedFindCommand);
    }

}
