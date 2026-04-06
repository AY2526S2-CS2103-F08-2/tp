package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GOALS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOSSES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POSITION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEAM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WINS;

import java.util.Optional;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.PersonMatchesFilterPredicate;
import seedu.address.model.person.PersonMatchesFilterPredicate.NumericComparison;
import seedu.address.model.person.PersonMatchesFilterPredicate.NumericComparison.Operator;
import seedu.address.model.person.Position;
import seedu.address.model.person.Role;
import seedu.address.model.person.Status;
import seedu.address.model.person.Team;

/**
 * Parses input arguments and creates a new {@link FilterCommand}.
 */
public class FilterCommandParser implements Parser<FilterCommand> {

    @Override
    public FilterCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_ROLE, PREFIX_TEAM, PREFIX_STATUS,
                PREFIX_POSITION, PREFIX_GOALS, PREFIX_WINS, PREFIX_LOSSES);
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_ROLE, PREFIX_TEAM, PREFIX_STATUS,
                PREFIX_POSITION, PREFIX_GOALS, PREFIX_WINS, PREFIX_LOSSES);

        if (!argMultimap.getPreamble().isEmpty() || hasNoCriteria(argMultimap)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        Optional<Role> role = parseOptionalRole(argMultimap);
        Optional<Team> team = parseOptionalTeam(argMultimap);
        Optional<Status> status = parseOptionalStatus(argMultimap);
        Optional<Position> position = parseOptionalPosition(argMultimap);
        Optional<NumericComparison> goals = parseOptionalNumericComparison(argMultimap, PREFIX_GOALS);
        Optional<NumericComparison> wins = parseOptionalNumericComparison(argMultimap, PREFIX_WINS);
        Optional<NumericComparison> losses = parseOptionalNumericComparison(argMultimap, PREFIX_LOSSES);

        PersonMatchesFilterPredicate predicate = new PersonMatchesFilterPredicate(role, team, status,
                position, goals, wins, losses);
        return new FilterCommand(predicate);
    }

    private boolean hasNoCriteria(ArgumentMultimap argMultimap) {
        return !argMultimap.getValue(PREFIX_ROLE).isPresent()
                && !argMultimap.getValue(PREFIX_TEAM).isPresent()
                && !argMultimap.getValue(PREFIX_STATUS).isPresent()
                && !argMultimap.getValue(PREFIX_POSITION).isPresent()
                && !argMultimap.getValue(PREFIX_GOALS).isPresent()
                && !argMultimap.getValue(PREFIX_WINS).isPresent()
                && !argMultimap.getValue(PREFIX_LOSSES).isPresent();
    }

    private Optional<Role> parseOptionalRole(ArgumentMultimap argMultimap) throws ParseException {
        if (!argMultimap.getValue(PREFIX_ROLE).isPresent()) {
            return Optional.empty();
        }
        return Optional.of(ParserUtil.parseRole(argMultimap.getValue(PREFIX_ROLE).get()));
    }

    private Optional<Team> parseOptionalTeam(ArgumentMultimap argMultimap) throws ParseException {
        if (!argMultimap.getValue(PREFIX_TEAM).isPresent()) {
            return Optional.empty();
        }
        return Optional.of(ParserUtil.parseTeam(argMultimap.getValue(PREFIX_TEAM).get()));
    }

    private Optional<Status> parseOptionalStatus(ArgumentMultimap argMultimap) throws ParseException {
        if (!argMultimap.getValue(PREFIX_STATUS).isPresent()) {
            return Optional.empty();
        }
        return Optional.of(ParserUtil.parseStatus(argMultimap.getValue(PREFIX_STATUS).get()));
    }

    private Optional<Position> parseOptionalPosition(ArgumentMultimap argMultimap) throws ParseException {
        if (!argMultimap.getValue(PREFIX_POSITION).isPresent()) {
            return Optional.empty();
        }
        return Optional.of(ParserUtil.parsePosition(argMultimap.getValue(PREFIX_POSITION).get()));
    }

    private Optional<NumericComparison> parseOptionalNumericComparison(ArgumentMultimap argMultimap, Prefix prefix)
            throws ParseException {
        if (!argMultimap.getValue(prefix).isPresent()) {
            return Optional.empty();
        }

        String rawValue = argMultimap.getValue(prefix).get().trim();
        if (rawValue.length() < 2) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }

        Operator operator = parseOperator(rawValue.charAt(0));
        int threshold = parseThreshold(rawValue.substring(1).trim());
        return Optional.of(new NumericComparison(operator, threshold));
    }

    private Operator parseOperator(char symbol) throws ParseException {
        switch (symbol) {
        case '>':
            return Operator.GREATER_THAN;
        case '<':
            return Operator.LESS_THAN;
        case '=':
            return Operator.EQUALS;
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }
    }

    private int parseThreshold(String value) throws ParseException {
        try {
            int parsed = Integer.parseInt(value);
            if (parsed < 0) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
            }
            return parsed;
        } catch (NumberFormatException nfe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }
    }
}
