package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import seedu.address.logic.commands.DeleteBulkCommand;
import seedu.address.logic.commands.DeleteBulkCommand.BulkDeletionDecision;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.tag.Tag;

/**
 * Parses arguments for {@link DeleteBulkCommand}.
 */
public class DeleteBulkCommandParser implements Parser<DeleteBulkCommand> {

    @Override
    public DeleteBulkCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_TAG);
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_TAG);

        if (argMultimap.getValue(PREFIX_TAG).isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteBulkCommand.MESSAGE_USAGE));
        }

        Tag tag = ParserUtil.parseTag(argMultimap.getValue(PREFIX_TAG).get());
        String preamble = argMultimap.getPreamble().trim();

        BulkDeletionDecision decision = parseDecision(preamble);
        return new DeleteBulkCommand(tag, decision);
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
