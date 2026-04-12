package seedu.address.logic.parser;

import java.util.Locale;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteBulkCommand;
import seedu.address.logic.commands.DeleteBulkCommand.BulkDeletionDecision;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteCommand.DeletionDecision;

/**
 * Handles parser-level continuation input for the multi-step delete interaction flow.
 */
class DeleteInteractionFlow {
    private PendingDeleteContext pendingDeleteContext;
    private String pendingDeleteBulkArgument;

    String preprocessInput(String trimmedInput) {
        if (pendingDeleteBulkArgument != null && isYesNo(trimmedInput)) {
            return DeleteBulkCommand.COMMAND_WORD + " " + trimmedInput.toLowerCase(Locale.ROOT)
                    + " " + pendingDeleteBulkArgument;
        }
        if (pendingDeleteBulkArgument != null) {
            pendingDeleteBulkArgument = null;
            return trimmedInput;
        }

        if (pendingDeleteContext == null) {
            return trimmedInput;
        }

        if (pendingDeleteContext.pendingDeleteBaseCommand != null && isYesNo(trimmedInput)) {
            return buildFollowUpCommand(trimmedInput.toLowerCase(Locale.ROOT));
        }

        if (pendingDeleteContext.pendingDeleteCriteria != null && isPositiveInteger(trimmedInput)) {
            return DeleteCommand.COMMAND_WORD + " " + pendingDeleteContext.pendingDeleteCriteria + " "
                    + DeleteCommandParser.INTERNAL_MATCH_INDEX_MARKER + " " + trimmedInput;
        }

        pendingDeleteContext = null;
        pendingDeleteBulkArgument = null;
        return trimmedInput;
    }

    void updateAfterParse(Command command) {
        if (command instanceof DeleteBulkCommand) {
            updateDeleteBulkContext((DeleteBulkCommand) command);
            return;
        }

        if (!(command instanceof DeleteCommand)) {
            pendingDeleteContext = null;
            pendingDeleteBulkArgument = null;
            return;
        }

        pendingDeleteBulkArgument = null;
        DeleteCommand deleteCommand = (DeleteCommand) command;
        if (deleteCommand.getDeletionDecision() != DeletionDecision.UNDECIDED) {
            pendingDeleteContext = null;
            return;
        }

        if (deleteCommand.isAmbiguousNumericDelete()) {
            pendingDeleteContext = new PendingDeleteContext(
                    DeleteCommand.COMMAND_WORD + " " + deleteCommand.getCriteria(),
                    deleteCommand.getCriteria(),
                    false,
                    true);
            return;
        }

        if (deleteCommand.isCriteriaDelete()) {
            if (deleteCommand.getCriteriaMatchIndex() == null) {
                pendingDeleteContext = new PendingDeleteContext(
                        DeleteCommand.COMMAND_WORD + " " + deleteCommand.getCriteria(),
                        deleteCommand.getCriteria(), false, false);
                return;
            }

            pendingDeleteContext = new PendingDeleteContext(
                    buildBaseDeleteCommand(deleteCommand),
                    deleteCommand.getCriteria(),
                    false,
                    false);
            return;
        }

        pendingDeleteContext = new PendingDeleteContext(buildBaseDeleteCommand(deleteCommand), null, true, false);
    }

    private void updateDeleteBulkContext(DeleteBulkCommand deleteBulkCommand) {
        pendingDeleteContext = null;
        if (deleteBulkCommand.getDecision() != BulkDeletionDecision.UNDECIDED) {
            pendingDeleteBulkArgument = null;
            return;
        }

        pendingDeleteBulkArgument = deleteBulkCommand.getCriterion().toArgumentString();
    }

    private String buildBaseDeleteCommand(DeleteCommand deleteCommand) {
        if (!deleteCommand.isCriteriaDelete()) {
            return DeleteCommand.COMMAND_WORD + " " + deleteCommand.getTargetIndex().getOneBased();
        }

        StringBuilder commandBuilder = new StringBuilder(DeleteCommand.COMMAND_WORD)
                .append(" ")
                .append(deleteCommand.getCriteria());
        Index criteriaMatchIndex = deleteCommand.getCriteriaMatchIndex();
        if (criteriaMatchIndex != null) {
            commandBuilder.append(" ")
                    .append(DeleteCommandParser.INTERNAL_MATCH_INDEX_MARKER)
                    .append(" ")
                    .append(criteriaMatchIndex.getOneBased());
        }
        return commandBuilder.toString();
    }

    private String buildFollowUpCommand(String normalizedInput) {
        if (pendingDeleteContext.isIndexBasedDelete && normalizedInput.equals(DeleteCommand.YES_KEYWORD)) {
            return pendingDeleteContext.pendingDeleteBaseCommand + " " + DeleteCommand.CONFIRM_KEYWORD;
        }

        if (pendingDeleteContext.isAmbiguousNumericDelete) {
            return pendingDeleteContext.pendingDeleteBaseCommand + " " + normalizedInput;
        }

        return pendingDeleteContext.pendingDeleteBaseCommand + " " + DeleteCommandParser.INTERNAL_DECISION_MARKER
                + " " + normalizedInput;
    }

    private boolean isYesNo(String input) {
        return input.equalsIgnoreCase(DeleteCommand.YES_KEYWORD)
                || input.equalsIgnoreCase(DeleteCommand.NO_KEYWORD);
    }

    private boolean isPositiveInteger(String input) {
        return input.matches("[1-9]\\d*");
    }

    private static class PendingDeleteContext {
        private final String pendingDeleteBaseCommand;
        private final String pendingDeleteCriteria;
        private final boolean isIndexBasedDelete;
        private final boolean isAmbiguousNumericDelete;

        private PendingDeleteContext(String pendingDeleteBaseCommand, String pendingDeleteCriteria,
                                     boolean isIndexBasedDelete, boolean isAmbiguousNumericDelete) {
            this.pendingDeleteBaseCommand = pendingDeleteBaseCommand;
            this.pendingDeleteCriteria = pendingDeleteCriteria;
            this.isIndexBasedDelete = isIndexBasedDelete;
            this.isAmbiguousNumericDelete = isAmbiguousNumericDelete;
        }
    }
}
