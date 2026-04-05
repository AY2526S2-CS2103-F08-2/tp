package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ListFilteredCommand;
import seedu.address.logic.commands.ListRoleCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.PersonHasRolePredicate;
import seedu.address.model.person.PersonMatchesListFiltersPredicate;
import seedu.address.model.person.Position;
import seedu.address.model.person.Role;
import seedu.address.model.person.Status;
import seedu.address.model.person.Team;

public class ListCommandParserTest {

    private final ListCommandParser parser = new ListCommandParser();

    @Test
    public void parse_roleOnly_returnsListRoleCommand() throws Exception {
        ListRoleCommand expected = new ListRoleCommand(new PersonHasRolePredicate(Role.PLAYER), "players");
        assertEquals(expected, parser.parse("players"));
    }

    @Test
    public void parse_filtersOnly_returnsListFilteredCommand() throws Exception {
        ListFilteredCommand expected = new ListFilteredCommand(
                new PersonMatchesListFiltersPredicate(Optional.empty(),
                        Optional.of(new Team("First Team")),
                        Optional.empty(),
                        Optional.empty()),
                "persons matching team First Team");
        assertEquals(expected, parser.parse("tm/First Team"));
    }

    @Test
    public void parse_roleAndCombinedFilters_returnsListFilteredCommand() throws Exception {
        ListFilteredCommand expected = new ListFilteredCommand(
                new PersonMatchesListFiltersPredicate(Optional.of(Role.STAFF),
                        Optional.empty(),
                        Optional.of(new Status("Active")),
                        Optional.of(new Position("Forward"))),
                "staff matching status Active, position Forward");
        assertEquals(expected, parser.parse("staff st/Active pos/Forward"));
    }

    @Test
    public void parse_invalidScope_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("coaches tm/First Team"));
    }

    @Test
    public void parse_duplicatePrefix_throwsParseException() {
        assertThrows(ParseException.class, () -> parser.parse("tm/First Team tm/Second Team"));
    }
}
