package seedu.address.logic.parser;

import seedu.address.logic.commands.TeamDeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Team;

/**
 * Parses input arguments and creates a new TeamDeleteCommand object.
 */
public class TeamDeleteCommandParser implements Parser<TeamDeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the TeamDeleteCommand
     * and returns a TeamDeleteCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public TeamDeleteCommand parse(String args) throws ParseException {
        Team team = ParserUtil.parseTeam(AttributeParserUtil.parseRequiredValue(args, TeamDeleteCommand.MESSAGE_USAGE));
        return new TeamDeleteCommand(team);
    }
}
