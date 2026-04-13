package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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

        List<String> tokens = Arrays.asList(trimmedArgs.split("\\s+"));
        Optional<Role> role = Optional.empty();
        List<String> nameKeywords = new ArrayList<>();

        for (String token : tokens) {
            if (isRolePrefixedToken(token)) {
                if (role.isPresent()) {
                    throw new ParseException(
                            String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
                }
                role = Optional.of(ParserUtil.parseRole(token.substring(PREFIX_ROLE.getPrefix().length())));
            } else {
                nameKeywords.add(token);
            }
        }

        if (nameKeywords.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        if (role.isPresent()) {
            Role parsedRole = role.get();
            logger.fine(() -> String.format("Parsed role-aware find: role=%s keywords=%d",
                    parsedRole, nameKeywords.size()));
            return new FindCommand(new RoleFilteredNameContainsKeywordsPredicate(parsedRole, nameKeywords));
        }

        logger.fine(() -> String.format("Parsed global find with %d keywords", nameKeywords.size()));
        return new FindCommand(new NameContainsKeywordsPredicate(nameKeywords));
    }

    private boolean isRolePrefixedToken(String token) {
        return token.startsWith(PREFIX_ROLE.getPrefix());
    }

}
