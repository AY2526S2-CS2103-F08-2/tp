package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.logic.commands.SortCommand.MESSAGE_USAGE;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SortCommand;
import seedu.address.model.person.PersonHasRolePredicate;
import seedu.address.model.person.PersonSortAttribute;
import seedu.address.model.person.Role;

public class SortCommandParserTest {

    private final SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_sortAllPersonsByName_returnsSortCommand() throws Exception {
        SortCommand expected = new SortCommand(PREDICATE_SHOW_ALL_PERSONS, PersonSortAttribute.NAME, "persons");
        assertEquals(expected, parser.parse("by/name"));
    }

    @Test
    public void parse_sortPlayersByEmail_returnsSortCommand() throws Exception {
        SortCommand expected = new SortCommand(
                new PersonHasRolePredicate(Role.PLAYER), PersonSortAttribute.EMAIL, "players");
        assertEquals(expected, parser.parse("players by/email"));
    }

    @Test
    public void parse_caseInsensitive_returnsSortCommand() throws Exception {
        SortCommand expected = new SortCommand(
                new PersonHasRolePredicate(Role.STAFF), PersonSortAttribute.NAME, "staff");
        assertEquals(expected, parser.parse("STAFF by/NAME"));
    }

    @Test
    public void parse_extraWhitespace_returnsSortCommand() {
        SortCommand expected = new SortCommand(PREDICATE_SHOW_ALL_PERSONS, PersonSortAttribute.EMAIL, "persons");
        assertParseSuccess(parser, "   by/email   ", expected);
    }

    @Test
    public void parse_invalidFormat_throwsParseException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE);
        assertParseFailure(parser, "", expectedMessage);
        assertParseFailure(parser, "name", expectedMessage);
        assertParseFailure(parser, "persons by/name", expectedMessage);
        assertParseFailure(parser, "players name", expectedMessage);
        assertParseFailure(parser, "players by/goals", expectedMessage);
        assertParseFailure(parser, "players by/name by/email", expectedMessage);
    }
}
