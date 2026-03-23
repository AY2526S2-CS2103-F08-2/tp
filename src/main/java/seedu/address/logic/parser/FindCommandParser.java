package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Role;
import seedu.address.model.person.RoleFilteredNameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {
    private static final String ROLE_PLAYER = "player";
    private static final String ROLE_PLAYERS = "players";
    private static final String ROLE_STAFF = "staff";

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        String[] tokens = trimmedArgs.split("\\s+");
        String firstToken = tokens[0].toLowerCase(Locale.ROOT);

        if (isRoleToken(firstToken)) {
            if (tokens.length == 1) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }
            Role role = parseRole(firstToken);
            List<String> nameKeywords = Arrays.asList(Arrays.copyOfRange(tokens, 1, tokens.length));
            return new FindCommand(new RoleFilteredNameContainsKeywordsPredicate(role, nameKeywords));
        }

        String[] nameKeywords = tokens;

        return new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

    private boolean isRoleToken(String token) {
        return ROLE_PLAYER.equals(token) || ROLE_PLAYERS.equals(token) || ROLE_STAFF.equals(token);
    }

    private Role parseRole(String token) {
        if (ROLE_STAFF.equals(token)) {
            return Role.STAFF;
        }
        return Role.PLAYER;
    }

}
