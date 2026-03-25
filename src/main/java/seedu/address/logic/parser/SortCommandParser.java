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

    public static final String ARGUMENT_PLAYERS = "players";
    public static final String ARGUMENT_STAFF = "staff";
    public static final String SCOPE_ALL_PERSONS = "persons";

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

        String attributeKeyword = argMultimap.getValue(PREFIX_SORT_BY)
                .orElseThrow(() -> new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE)));

        return new SortCommand(parseScope(scope), parseAttribute(attributeKeyword), getScopeDescription(scope));
    }

    private Predicate<Person> parseScope(String scope) {
        switch (scope.toLowerCase()) {
        case ARGUMENT_PLAYERS:
            return new PersonHasRolePredicate(Role.PLAYER);
        case ARGUMENT_STAFF:
            return new PersonHasRolePredicate(Role.STAFF);
        default:
            return PREDICATE_SHOW_ALL_PERSONS;
        }
    }

    private PersonSortAttribute parseAttribute(String attribute) throws ParseException {
        try {
            return PersonSortAttribute.fromKeyword(attribute);
        } catch (IllegalArgumentException iae) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }
    }

    private boolean isValidScope(String scope) {
        return scope.isEmpty()
                || ARGUMENT_PLAYERS.equalsIgnoreCase(scope)
                || ARGUMENT_STAFF.equalsIgnoreCase(scope);
    }

    private String getScopeDescription(String scope) {
        if (scope.isEmpty()) {
            return SCOPE_ALL_PERSONS;
        }
        return scope.toLowerCase();
    }
}
