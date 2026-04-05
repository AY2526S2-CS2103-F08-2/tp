package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POSITION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEAM;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import seedu.address.logic.commands.ListFilteredCommand;
import seedu.address.logic.commands.ListRoleCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.PersonHasRolePredicate;
import seedu.address.model.person.PersonMatchesListFiltersPredicate;
import seedu.address.model.person.Position;
import seedu.address.model.person.Role;
import seedu.address.model.person.Status;
import seedu.address.model.person.Team;

/**
 * Parses input arguments for the {@code list} command, including optional attribute filters.
 */
public class ListCommandParser implements Parser<seedu.address.logic.commands.Command> {

    private static final String ARGUMENT_PLAYERS = "players";
    private static final String ARGUMENT_STAFF = "staff";
    private static final String DESCRIPTION_PERSONS = "persons";

    @Override
    public seedu.address.logic.commands.Command parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListRoleCommand.MESSAGE_USAGE));
        }

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(" " + trimmedArgs, PREFIX_TEAM, PREFIX_STATUS, PREFIX_POSITION);
        try {
            argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_TEAM, PREFIX_STATUS, PREFIX_POSITION);
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListRoleCommand.MESSAGE_USAGE));
        }

        Optional<Role> role = parseRoleScope(argMultimap.getPreamble().trim());
        Optional<Team> team = parseOptionalTeam(argMultimap);
        Optional<Status> status = parseOptionalStatus(argMultimap);
        Optional<Position> position = parseOptionalPosition(argMultimap);
        boolean hasAttributeFilters = team.isPresent() || status.isPresent() || position.isPresent();

        if (!hasAttributeFilters) {
            return new ListRoleCommand(new PersonHasRolePredicate(role.get()), getRoleDescription(role.get()));
        }

        PersonMatchesListFiltersPredicate predicate =
                new PersonMatchesListFiltersPredicate(role, team, status, position);
        return new ListFilteredCommand(predicate, buildDescription(role, team, status, position));
    }

    private Optional<Role> parseRoleScope(String preamble) throws ParseException {
        if (preamble.isEmpty()) {
            return Optional.empty();
        }
        if (ARGUMENT_PLAYERS.equalsIgnoreCase(preamble)) {
            return Optional.of(Role.PLAYER);
        }
        if (ARGUMENT_STAFF.equalsIgnoreCase(preamble)) {
            return Optional.of(Role.STAFF);
        }
        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ListRoleCommand.MESSAGE_USAGE));
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

    private String getRoleDescription(Role role) {
        return role == Role.PLAYER ? ARGUMENT_PLAYERS : ARGUMENT_STAFF;
    }

    private String buildDescription(Optional<Role> role, Optional<Team> team,
                                    Optional<Status> status, Optional<Position> position) {
        List<String> parts = new ArrayList<>();
        parts.add(role.map(this::getRoleDescription).orElse(DESCRIPTION_PERSONS));
        team.ifPresent(value -> parts.add("team " + value));
        status.ifPresent(value -> parts.add("status " + value));
        position.ifPresent(value -> parts.add("position " + value));
        return parts.get(0) + " matching " + String.join(", ", parts.subList(1, parts.size()));
    }
}
