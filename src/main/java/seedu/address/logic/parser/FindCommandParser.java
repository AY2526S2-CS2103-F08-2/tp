package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Role;
import seedu.address.model.person.RoleFilteredNameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {
    private static final Logger logger = LogsCenter.getLogger(FindCommandParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        String[] tokens = trimmedArgs.split("\\s+");
        String firstToken = tokens[0];

        if (isRolePrefixedToken(firstToken)) {
            if (tokens.length == 1) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
            }
            Role role = ParserUtil.parseRole(firstToken.substring(PREFIX_ROLE.getPrefix().length()));
            List<String> nameKeywords = Arrays.asList(Arrays.copyOfRange(tokens, 1, tokens.length));
            logger.fine(() -> String.format("Parsed role-aware find: role=%s keywords=%d", role, nameKeywords.size()));
            return new FindCommand(new RoleFilteredNameContainsKeywordsPredicate(role, nameKeywords));
        }
        logger.fine(() -> String.format("Parsed global find with %d keywords", tokens.length));
        return new FindCommand(new NameContainsKeywordsPredicate(Arrays.asList(tokens)));
    }

    private boolean isRolePrefixedToken(String token) {
        return token.startsWith(PREFIX_ROLE.getPrefix());
    }

}
