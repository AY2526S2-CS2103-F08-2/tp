package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORT_BY;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.function.Predicate;
import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonHasRolePredicate;
import seedu.address.model.person.PersonSortAttribute;
import seedu.address.model.person.Role;

/**
 * Parses input arguments and creates a new SortCommand object.
 */
public class SortCommandParser implements Parser<SortCommand> {

    public static final String SCOPE_ALL_PERSONS = "persons";
    public static final String SCOPE_PLAYERS = "players";
    public static final String ARGUMENT_DESC = "desc";
    private static final Logger logger = LogsCenter.getLogger(SortCommandParser.class);

    @Override
    public SortCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(" " + trimmedArgs, PREFIX_SORT_BY);
        try {
            argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_SORT_BY);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        String scope = argMultimap.getPreamble();
        if (!isValidScope(scope)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        String sortArgument = argMultimap.getValue(PREFIX_SORT_BY)
                .orElseThrow(() -> new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE)));

        String[] sortTokens = sortArgument.split("\\s+");
        if (sortTokens.length == 0 || sortTokens.length > 2) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        String attributeKeyword = sortTokens[0];
        boolean isDescending = parseSortOrder(sortTokens);
        PersonSortAttribute attribute = parseAttribute(attributeKeyword);
        validateScopeForAttribute(scope, attribute);
        logger.fine(() -> String.format("Parsed sort command: scope=%s attribute=%s order=%s",
                getScopeDescription(scope, attribute), attributeKeyword, isDescending ? SortCommand.ORDER_DESCENDING
                        : SortCommand.ORDER_ASCENDING));
        return new SortCommand(parseScope(scope), attribute, getScopeDescription(scope, attribute), isDescending);
    }

    private Predicate<Person> parseScope(String scope) throws ParseException {
        if (scope.isEmpty()) {
            return PREDICATE_SHOW_ALL_PERSONS;
        }
        return new PersonHasRolePredicate(parseRoleScope(scope));
    }

    private PersonSortAttribute parseAttribute(String attribute) throws ParseException {
        try {
            return PersonSortAttribute.fromKeyword(attribute);
        } catch (IllegalArgumentException iae) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }
    }

    private boolean isValidScope(String scope) {
        if (scope.isEmpty()) {
            return true;
        }

        try {
            parseRoleScope(scope);
            return true;
        } catch (ParseException pe) {
            return false;
        }
    }

    private String getScopeDescription(String scope, PersonSortAttribute attribute) {
        if (scope.isEmpty()) {
            return attribute.isPlayerStatAttribute() ? SCOPE_PLAYERS : SCOPE_ALL_PERSONS;
        }
        try {
            Role role = parseRoleScope(scope);
            return role == Role.PLAYER ? "players" : "staff";
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Scope should have been validated before description lookup", pe);
        }
    }

    private Role parseRoleScope(String scope) throws ParseException {
        if (!scope.regionMatches(true, 0, PREFIX_ROLE.getPrefix(), 0, PREFIX_ROLE.getPrefix().length())) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }

        return ParserUtil.parseRole(scope.substring(PREFIX_ROLE.getPrefix().length()));
    }

    private void validateScopeForAttribute(String scope, PersonSortAttribute attribute) throws ParseException {
        if (!attribute.isPlayerStatAttribute() || scope.isEmpty()) {
            return;
        }

        Role role = parseRoleScope(scope);
        if (role == Role.STAFF) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }
    }

    private boolean parseSortOrder(String[] sortTokens) throws ParseException {
        if (sortTokens.length == 1) {
            return false;
        }

        if (ARGUMENT_DESC.equalsIgnoreCase(sortTokens[1])) {
            return true;
        }

        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }
}
