package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_NEW_ATTRIBUTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OLD_ATTRIBUTE;

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
        ArgumentMultimap argMultimap = AttributeParserUtil.parseRequiredOldAndNew(args, TeamEditCommand.MESSAGE_USAGE);

        Team oldTeam = ParserUtil.parseTeam(argMultimap.getValue(PREFIX_OLD_ATTRIBUTE).get());
        Team newTeam = ParserUtil.parseTeam(argMultimap.getValue(PREFIX_NEW_ATTRIBUTE).get());

        return new TeamEditCommand(oldTeam, newTeam);
    }
}
