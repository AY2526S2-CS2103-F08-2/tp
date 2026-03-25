package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEW_TEAM;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OLD_TEAM;

import seedu.address.logic.commands.TeamEditCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Team;

/**
 * Parses input arguments and creates a new TeamEditCommand object.
 */
public class TeamEditCommandParser implements Parser<TeamEditCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the TeamEditCommand
     * and returns a TeamEditCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public TeamEditCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_OLD_TEAM, PREFIX_NEW_TEAM);

        if (!argMultimap.getValue(PREFIX_OLD_TEAM).isPresent()
                || !argMultimap.getValue(PREFIX_NEW_TEAM).isPresent()
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TeamEditCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_OLD_TEAM, PREFIX_NEW_TEAM);

        Team oldTeam = ParserUtil.parseTeam(argMultimap.getValue(PREFIX_OLD_TEAM).get());
        Team newTeam = ParserUtil.parseTeam(argMultimap.getValue(PREFIX_NEW_TEAM).get());

        return new TeamEditCommand(oldTeam, newTeam);
    }
}

