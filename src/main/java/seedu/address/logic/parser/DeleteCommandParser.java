package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.Locale;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteCommand.DeletionDecision;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand object.
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {
    static final String INTERNAL_MATCH_INDEX_MARKER = "__match_index__";
    static final String INTERNAL_DECISION_MARKER = "__decision__";

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }

        String[] tokens = trimmedArgs.split("\\s+");

        if (isInternalCriteriaFollowUp(tokens)) {
            return parseCriteriaFollowUp(trimmedArgs, tokens);
        }

        Index index = tryParseIndex(tokens[0]);
        if (index != null && tokens.length == 1) {
            return DeleteCommand.forAmbiguousNumericInput(trimmedArgs, index);
        }

        if (index != null && tokens.length == 2 && isYesNoToken(tokens[1])) {
            DeletionDecision decision = tokens[1].equalsIgnoreCase(DeleteCommand.YES_KEYWORD)
                    ? DeletionDecision.CONFIRM : DeletionDecision.CANCEL;
            return DeleteCommand.forAmbiguousNumericInput(tokens[0], index, decision);
        }

        if (index != null && shouldParseAsIndexDelete(tokens)) {
            return parseIndexBasedDelete(index, tokens);
        }

        return new DeleteCommand(trimmedArgs, null, DeletionDecision.UNDECIDED);
    }

    private Index tryParseIndex(String token) {
        try {
            return ParserUtil.parseIndex(token);
        } catch (ParseException ignored) {
            return null;
        }
    }

    private DeleteCommand parseIndexBasedDelete(Index index, String[] tokens) throws ParseException {
        if (tokens.length == 2) {
            String secondToken = tokens[1].toLowerCase(Locale.ROOT);
            if (secondToken.equals(DeleteCommand.CONFIRM_KEYWORD)) {
                return new DeleteCommand(index, DeletionDecision.CONFIRM);
            }

            if (secondToken.equals(DeleteCommand.NO_KEYWORD)) {
                return new DeleteCommand(index, DeletionDecision.CANCEL);
            }
        }

        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }

    private DeleteCommand parseCriteriaFollowUp(String trimmedArgs, String[] tokens) throws ParseException {
        int decisionMarkerIndex = indexOf(tokens, INTERNAL_DECISION_MARKER);
        int matchIndexMarkerIndex = indexOf(tokens, INTERNAL_MATCH_INDEX_MARKER);

        if (decisionMarkerIndex == -1 && matchIndexMarkerIndex == -1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }

        int firstMarkerIndex = firstMarkerIndex(decisionMarkerIndex, matchIndexMarkerIndex);
        if (firstMarkerIndex <= 0) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }

        String criteria = String.join(" ", Arrays.copyOfRange(tokens, 0, firstMarkerIndex)).trim();
        if (criteria.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }

        Index matchIndex = null;
        if (matchIndexMarkerIndex != -1) {
            if (matchIndexMarkerIndex == tokens.length - 1) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
            }
            if (matchIndexMarkerIndex != tokens.length - 2
                    && !(decisionMarkerIndex != -1 && matchIndexMarkerIndex == tokens.length - 4
                    && decisionMarkerIndex == tokens.length - 2)) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
            }
            matchIndex = ParserUtil.parseIndex(tokens[matchIndexMarkerIndex + 1]);
        }

        if (decisionMarkerIndex != -1) {
            if (decisionMarkerIndex == tokens.length - 1) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
            }
            if (decisionMarkerIndex != tokens.length - 2) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
            }
            String decisionToken = tokens[tokens.length - 1].toLowerCase(Locale.ROOT);
            if (!isYesNoToken(decisionToken)) {
                throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
            }

            DeletionDecision decision = decisionToken.equals(DeleteCommand.YES_KEYWORD)
                    ? DeletionDecision.CONFIRM : DeletionDecision.CANCEL;
            return new DeleteCommand(criteria, matchIndex, decision);
        }

        return new DeleteCommand(criteria, matchIndex, DeletionDecision.UNDECIDED);
    }

    private boolean isInternalCriteriaFollowUp(String[] tokens) {
        return indexOf(tokens, INTERNAL_DECISION_MARKER) != -1 || indexOf(tokens, INTERNAL_MATCH_INDEX_MARKER) != -1;
    }

    private boolean isYesNoToken(String token) {
        return token.equalsIgnoreCase(DeleteCommand.YES_KEYWORD)
                || token.equalsIgnoreCase(DeleteCommand.NO_KEYWORD);
    }

    private boolean shouldParseAsIndexDelete(String[] tokens) {
        return tokens.length == 2 && tokens[1].equalsIgnoreCase(DeleteCommand.CONFIRM_KEYWORD);
    }

    private int indexOf(String[] tokens, String marker) {
        for (int i = 0; i < tokens.length; i++) {
            if (tokens[i].equals(marker)) {
                return i;
            }
        }
        return -1;
    }

    private int firstMarkerIndex(int firstIndex, int secondIndex) {
        if (firstIndex == -1) {
            return secondIndex;
        }
        if (secondIndex == -1) {
            return firstIndex;
        }
        return Math.min(firstIndex, secondIndex);
    }
}
