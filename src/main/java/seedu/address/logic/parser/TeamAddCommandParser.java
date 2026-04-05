package seedu.address.logic.parser;

import seedu.address.logic.commands.TeamAddCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Team;

/**
 * Parses input arguments and creates a new TeamAddCommand object.
 */
public class TeamAddCommandParser implements Parser<TeamAddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the TeamAddCommand
     * and returns a TeamAddCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public TeamAddCommand parse(String args) throws ParseException {
        Team team = ParserUtil.parseTeam(AttributeParserUtil.parseRequiredValue(args, TeamAddCommand.MESSAGE_USAGE));
        return new TeamAddCommand(team);
    }
}
