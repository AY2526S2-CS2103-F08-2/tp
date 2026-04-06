package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEAM;

import seedu.address.logic.commands.DeleteBulkCommand;
import seedu.address.logic.commands.DeleteBulkCommand.BulkDeletionCriterion;
import seedu.address.logic.commands.DeleteBulkCommand.BulkDeletionDecision;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Status;
import seedu.address.model.person.Team;
import seedu.address.model.tag.Tag;

/**
 * Parses arguments for {@link DeleteBulkCommand}.
 */
public class DeleteBulkCommandParser implements Parser<DeleteBulkCommand> {

    @Override
    public DeleteBulkCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TAG, PREFIX_TEAM, PREFIX_STATUS);
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_TAG, PREFIX_TEAM, PREFIX_STATUS);

        BulkDeletionCriterion criterion = parseCriterion(argMultimap);
        if (criterion == null) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteBulkCommand.MESSAGE_USAGE));
        }
        String preamble = argMultimap.getPreamble().trim();

        BulkDeletionDecision decision = parseDecision(preamble);
        return new DeleteBulkCommand(criterion, decision);
    }

    private BulkDeletionCriterion parseCriterion(ArgumentMultimap argMultimap) throws ParseException {
        boolean hasTag = argMultimap.getValue(PREFIX_TAG).isPresent();
        boolean hasTeam = argMultimap.getValue(PREFIX_TEAM).isPresent();
        boolean hasStatus = argMultimap.getValue(PREFIX_STATUS).isPresent();
        int providedPrefixes = (hasTag ? 1 : 0) + (hasTeam ? 1 : 0) + (hasStatus ? 1 : 0);

        if (providedPrefixes != 1) {
            return null;
        }

        if (hasTag) {
            Tag tag = ParserUtil.parseTag(argMultimap.getValue(PREFIX_TAG).get());
            return BulkDeletionCriterion.forTag(tag);
        }

        if (hasTeam) {
            Team team = ParserUtil.parseTeam(argMultimap.getValue(PREFIX_TEAM).get());
            return BulkDeletionCriterion.forTeam(team);
        }

        Status status = ParserUtil.parseStatus(argMultimap.getValue(PREFIX_STATUS).get());
        return BulkDeletionCriterion.forStatus(status);
    }

    private BulkDeletionDecision parseDecision(String preamble) throws ParseException {
        if (preamble.isEmpty()) {
            return BulkDeletionDecision.UNDECIDED;
        }

        if (preamble.equalsIgnoreCase(DeleteBulkCommand.YES_KEYWORD)) {
            return BulkDeletionDecision.CONFIRM;
        }

        if (preamble.equalsIgnoreCase(DeleteBulkCommand.NO_KEYWORD)) {
            return BulkDeletionDecision.CANCEL;
        }

        throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteBulkCommand.MESSAGE_USAGE));
    }
}
