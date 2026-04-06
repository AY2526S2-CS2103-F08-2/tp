package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.SortCommand.MESSAGE_USAGE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SortCommand;
import seedu.address.model.person.PersonHasRolePredicate;
import seedu.address.model.person.PersonSortAttribute;
import seedu.address.model.person.Role;

public class SortCommandParserTest {

    private final SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_sortAllPersonsByName_returnsSortCommand() throws Exception {
        SortCommand expected = new SortCommand(PREDICATE_SHOW_ALL_PERSONS,
                PersonSortAttribute.NAME, "persons", false);
        assertEquals(expected, parser.parse("by/name"));
    }

    @Test
    public void parse_sortPlayersByEmail_returnsSortCommand() throws Exception {
        SortCommand expected = new SortCommand(
                new PersonHasRolePredicate(Role.PLAYER), PersonSortAttribute.EMAIL, "players", false);
        assertEquals(expected, parser.parse("r/player by/email"));
    }

    @Test
    public void parse_sortAllPersonsByTeam_returnsSortCommand() throws Exception {
        SortCommand expected = new SortCommand(PREDICATE_SHOW_ALL_PERSONS,
                PersonSortAttribute.TEAM, "persons", false);
        assertEquals(expected, parser.parse("by/team"));
    }

    @Test
    public void parse_sortStaffByStatus_returnsSortCommand() throws Exception {
        SortCommand expected = new SortCommand(
                new PersonHasRolePredicate(Role.STAFF), PersonSortAttribute.STATUS, "staff", false);
        assertEquals(expected, parser.parse("r/staff by/status"));
    }

    @Test
    public void parse_sortPlayersByPositionDescending_returnsSortCommand() {
        SortCommand expected = new SortCommand(
                new PersonHasRolePredicate(Role.PLAYER), PersonSortAttribute.POSITION, "players", true);
        assertParseSuccess(parser, "r/player by/position desc", expected);
    }

    @Test
    public void parse_sortPlayersByGoals_returnsSortCommand() throws Exception {
        SortCommand expected = new SortCommand(
                new PersonHasRolePredicate(Role.PLAYER), PersonSortAttribute.GOALS, "players", false);
        assertEquals(expected, parser.parse("r/player by/goals"));
    }

    @Test
    public void parse_sortAllPersonsByWins_returnsSortCommand() throws Exception {
        SortCommand expected = new SortCommand(PREDICATE_SHOW_ALL_PERSONS,
                PersonSortAttribute.WINS, "persons", false);
        assertEquals(expected, parser.parse("by/wins"));
    }

    @Test
    public void parse_sortPlayersByLossesDescending_returnsSortCommand() {
        SortCommand expected = new SortCommand(
                new PersonHasRolePredicate(Role.PLAYER), PersonSortAttribute.LOSSES, "players", true);
        assertParseSuccess(parser, "r/player by/losses desc", expected);
    }

    @Test
    public void parse_caseInsensitive_returnsSortCommand() throws Exception {
        SortCommand expected = new SortCommand(
                new PersonHasRolePredicate(Role.STAFF), PersonSortAttribute.NAME, "staff", false);
        assertEquals(expected, parser.parse("r/STAFF by/NAME"));
    }

    @Test
    public void parse_extraWhitespace_returnsSortCommand() {
        SortCommand expected = new SortCommand(PREDICATE_SHOW_ALL_PERSONS,
                PersonSortAttribute.EMAIL, "persons", false);
        assertParseSuccess(parser, "   by/email   ", expected);
    }

    @Test
    public void parse_descendingOrder_returnsSortCommand() {
        SortCommand expected = new SortCommand(new PersonHasRolePredicate(Role.PLAYER),
                PersonSortAttribute.NAME, "players", true);
        assertParseSuccess(parser, "r/player by/name desc", expected);
    }

    @Test
    public void parse_invalidFormat_throwsParseException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE);
        assertParseFailure(parser, "", expectedMessage);
        assertParseFailure(parser, "name", expectedMessage);
        assertParseFailure(parser, "persons by/name", expectedMessage);
        assertParseFailure(parser, "players by/name", expectedMessage);
        assertParseFailure(parser, "r/player name", expectedMessage);
        assertParseFailure(parser, "r/player by/name by/email", expectedMessage);
        assertParseFailure(parser, "r/player by/name desc extra", expectedMessage);
        assertParseFailure(parser, "by/name asc", expectedMessage);
        assertParseFailure(parser, "r/player by/name descending", expectedMessage);
        assertParseFailure(parser, "r/player desc by/name", expectedMessage);
    }
}
