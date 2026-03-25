package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

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
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, TeamAddCommand.MESSAGE_USAGE));
        }

        Team team = ParserUtil.parseTeam(trimmedArgs);
        return new TeamAddCommand(team);
    }
}

