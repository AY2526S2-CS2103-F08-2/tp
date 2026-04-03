package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ListRoleCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.PersonHasRolePredicate;
import seedu.address.model.person.Role;

public class ListRoleCommandParserTest {

    private final ListRoleCommandParser parser = new ListRoleCommandParser();

    @Test
    // VALID_CASE + EP_VALID (role partition: players)
    public void parse_players_returnsListRoleCommand() throws Exception {
        ListRoleCommand expected = new ListRoleCommand(new PersonHasRolePredicate(Role.PLAYER), "players");
        assertEquals(expected, parser.parse("players"));
    }

    @Test
    // VALID_CASE + EP_VALID (role partition: staff)
    public void parse_staff_returnsListRoleCommand() throws Exception {
        ListRoleCommand expected = new ListRoleCommand(new PersonHasRolePredicate(Role.STAFF), "staff");
        assertEquals(expected, parser.parse("staff"));
    }

    @Test
    // REGRESSION_GUARD (case-insensitive parsing)
    public void parse_caseInsensitive_returnsListRoleCommand() throws Exception {
        // uppercase
        ListRoleCommand expected = new ListRoleCommand(new PersonHasRolePredicate(Role.PLAYER), "players");
        assertEquals(expected, parser.parse("PLAYERS"));

        // mixed case
        assertEquals(expected, parser.parse("Players"));
    }

    @Test
    // INVALID_CASE + EP_INVALID (unknown or blank role token)
    public void parse_invalidRole_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("coaches"));
        assertThrows(ParseException.class, () -> parser.parse(""));
        assertThrows(ParseException.class, () -> parser.parse("   "));
    }
}
