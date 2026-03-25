package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.MatchCommand;
import seedu.address.logic.commands.StatusAddCommand;
import seedu.address.logic.commands.StatusDeleteCommand;
import seedu.address.logic.commands.StatusEditCommand;
import seedu.address.logic.commands.StatusListCommand;
import seedu.address.logic.commands.TeamAddCommand;
import seedu.address.logic.commands.TeamDeleteCommand;
import seedu.address.logic.commands.TeamEditCommand;
import seedu.address.logic.commands.TeamListCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses user input.
 */
public class AddressBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static final Logger logger = LogsCenter.getLogger(AddressBookParser.class);
    private final DeleteInteractionFlow deleteInteractionFlow = new DeleteInteractionFlow();

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        final String processedInput = deleteInteractionFlow.preprocessInput(userInput.trim());
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(processedInput);
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        // Note to developers: Change the log level in config.json to enable lower level (i.e., FINE, FINER and lower)
        // log messages such as the one below.
        // Lower level log messages are used sparingly to minimize noise in the code.
        logger.fine("Command word: " + commandWord + "; Arguments: " + arguments);

        Command command;
        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            command = new AddCommandParser().parse(arguments);
            break;

        case EditCommand.COMMAND_WORD:
            command = new EditCommandParser().parse(arguments);
            break;

        case DeleteCommand.COMMAND_WORD:
            command = new DeleteCommandParser().parse(arguments);
            break;

        case ClearCommand.COMMAND_WORD:
            command = new ClearCommand();
            break;

        case FindCommand.COMMAND_WORD:
            command = new FindCommandParser().parse(arguments);
            break;

        case ListCommand.COMMAND_WORD:
            if (arguments.isBlank()) {
                command = new ListCommand();
            } else {
                command = new ListRoleCommandParser().parse(arguments);
            }
            break;

        case TeamListCommand.COMMAND_WORD:
            if (!arguments.isBlank()) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, TeamListCommand.MESSAGE_USAGE));
            }
            command = new TeamListCommand();
            break;

        case StatusListCommand.COMMAND_WORD:
            if (!arguments.isBlank()) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, StatusListCommand.MESSAGE_USAGE));
            }
            command = new StatusListCommand();
            break;

        case StatusAddCommand.COMMAND_WORD:
            command = new StatusAddCommandParser().parse(arguments);
            break;

        case StatusEditCommand.COMMAND_WORD:
            command = new StatusEditCommandParser().parse(arguments);
            break;

        case StatusDeleteCommand.COMMAND_WORD:
            command = new StatusDeleteCommandParser().parse(arguments);
            break;

        case TeamAddCommand.COMMAND_WORD:
            command = new TeamAddCommandParser().parse(arguments);
            break;

        case TeamDeleteCommand.COMMAND_WORD:
            command = new TeamDeleteCommandParser().parse(arguments);
            break;

        case TeamEditCommand.COMMAND_WORD:
            command = new TeamEditCommandParser().parse(arguments);
            break;

        case ExitCommand.COMMAND_WORD:
            command = new ExitCommand();
            break;

        case HelpCommand.COMMAND_WORD:
            command = new HelpCommand();
            break;

        case MatchCommand.COMMAND_WORD:
            command = new MatchCommandParser().parse(arguments);
            break;

        default:
            logger.finer("This user input caused a ParseException: " + userInput);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }

        deleteInteractionFlow.updateAfterParse(command);
        return command;
    }

}
