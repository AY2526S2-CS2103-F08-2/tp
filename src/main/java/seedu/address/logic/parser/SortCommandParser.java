package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORT_BY;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.function.Predicate;

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
    public static final String ARGUMENT_DESC = "desc";
    private static final String ARGUMENT_PLAYERS = "players";
    private static final String ARGUMENT_STAFF = "staff";

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
        return new SortCommand(parseScope(scope), parseAttribute(attributeKeyword),
                getScopeDescription(scope), isDescending);
    }

    private Predicate<Person> parseScope(String scope) {
        Role role = parseRoleScope(scope);
        if (role == null) {
            return PREDICATE_SHOW_ALL_PERSONS;
        }
        return new PersonHasRolePredicate(role);
    }

    private PersonSortAttribute parseAttribute(String attribute) throws ParseException {
        try {
            return PersonSortAttribute.fromKeyword(attribute);
        } catch (IllegalArgumentException iae) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }
    }

    private boolean isValidScope(String scope) {
        return scope.isEmpty() || parseRoleScope(scope) != null;
    }

    private String getScopeDescription(String scope) {
        if (scope.isEmpty()) {
            return SCOPE_ALL_PERSONS;
        }
        return scope.toLowerCase();
    }

    private Role parseRoleScope(String scope) {
        if (ARGUMENT_PLAYERS.equalsIgnoreCase(scope)) {
            return Role.PLAYER;
        }
        if (ARGUMENT_STAFF.equalsIgnoreCase(scope)) {
            return Role.STAFF;
        }
        return null;
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
