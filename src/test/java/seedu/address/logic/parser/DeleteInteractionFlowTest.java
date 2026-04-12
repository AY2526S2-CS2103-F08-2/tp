package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteBulkCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeleteCommand.DeletionDecision;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.model.person.Status;
import seedu.address.model.person.Team;
import seedu.address.model.tag.Tag;

public class DeleteInteractionFlowTest {

    @Test
    public void preprocessInput_indexDelete_yesMapsToConfirm() {
        DeleteInteractionFlow flow = new DeleteInteractionFlow();
        flow.updateAfterParse(new DeleteCommand(INDEX_FIRST_PERSON));

        assertEquals("delete 1 confirm", flow.preprocessInput("y"));
    }

    @Test
    public void preprocessInput_criteriaDelete_supportsIndexThenYes() {
        DeleteInteractionFlow flow = new DeleteInteractionFlow();
        flow.updateAfterParse(new DeleteCommand("meier", null, DeletionDecision.UNDECIDED));

        assertEquals("delete meier __match_index__ 2", flow.preprocessInput("2"));

        flow.updateAfterParse(new DeleteCommand("meier", INDEX_SECOND_PERSON, DeletionDecision.UNDECIDED));
        assertEquals("delete meier __match_index__ 2 __decision__ y", flow.preprocessInput("y"));
    }

    @Test
    public void preprocessInput_criteriaDelete_supportsReselection() {
        DeleteInteractionFlow flow = new DeleteInteractionFlow();
        flow.updateAfterParse(new DeleteCommand("alex", INDEX_SECOND_PERSON, DeletionDecision.UNDECIDED));

        assertEquals("delete alex __match_index__ 1", flow.preprocessInput("1"));
    }

    @Test
    public void preprocessInput_ambiguousNumericDelete_supportsDirectYesNoFollowUp() {
        DeleteInteractionFlow flow = new DeleteInteractionFlow();
        flow.updateAfterParse(DeleteCommand.forAmbiguousNumericInput("2", INDEX_SECOND_PERSON));

        assertEquals("delete 2 y", flow.preprocessInput("y"));
        flow.updateAfterParse(DeleteCommand.forAmbiguousNumericInput("2", INDEX_SECOND_PERSON));
        assertEquals("delete 2 n", flow.preprocessInput("n"));
    }

    @Test
    public void preprocessInput_invalidFollowUp_clearsPendingContext() {
        DeleteInteractionFlow flow = new DeleteInteractionFlow();
        flow.updateAfterParse(new DeleteCommand(INDEX_FIRST_PERSON));

        assertEquals("gg", flow.preprocessInput("gg"));
        assertEquals("y", flow.preprocessInput("y"));
    }

    @Test
    public void preprocessInput_deleteBulk_yesMapsToFollowUpCommand() {
        DeleteInteractionFlow flow = new DeleteInteractionFlow();
        flow.updateAfterParse(new DeleteBulkCommand(new Tag("graduated")));

        assertEquals("deletebulk y t/graduated", flow.preprocessInput("y"));
    }

    @Test
    public void preprocessInput_deleteBulk_noMapsToFollowUpCommand() {
        DeleteInteractionFlow flow = new DeleteInteractionFlow();
        flow.updateAfterParse(new DeleteBulkCommand(new Tag("graduated")));

        assertEquals("deletebulk n t/graduated", flow.preprocessInput("n"));
    }

    @Test
    public void preprocessInput_deleteBulkInvalidFollowUp_clearsPendingContext() {
        DeleteInteractionFlow flow = new DeleteInteractionFlow();
        flow.updateAfterParse(new DeleteBulkCommand(new Tag("graduated")));

        assertEquals("oops", flow.preprocessInput("oops"));
        assertEquals("y", flow.preprocessInput("y"));
    }

    @Test
    public void updateAfterParse_nonDeleteCommand_clearsAllPendingContext() {
        DeleteInteractionFlow flow = new DeleteInteractionFlow();
        flow.updateAfterParse(new DeleteBulkCommand(new Tag("graduated")));
        flow.updateAfterParse(new HelpCommand());

        assertEquals("y", flow.preprocessInput("y"));
    }

    @Test
    public void updateAfterParse_deleteBulkDecisionMade_clearsPendingBulkContext() {
        DeleteInteractionFlow flow = new DeleteInteractionFlow();
        flow.updateAfterParse(new DeleteBulkCommand(
                new Tag("graduated"), DeleteBulkCommand.BulkDeletionDecision.CONFIRM));

        assertEquals("y", flow.preprocessInput("y"));
    }

    @Test
    public void preprocessInput_deleteBulkTeam_yesMapsToFollowUpCommand() {
        DeleteInteractionFlow flow = new DeleteInteractionFlow();
        flow.updateAfterParse(new DeleteBulkCommand(new Team("Reserve Team")));

        assertEquals("deletebulk y tm/Reserve Team", flow.preprocessInput("y"));
    }

    @Test
    public void preprocessInput_deleteBulkStatus_noMapsToFollowUpCommand() {
        DeleteInteractionFlow flow = new DeleteInteractionFlow();
        flow.updateAfterParse(new DeleteBulkCommand(new Status("Unavailable")));

        assertEquals("deletebulk n st/Unavailable", flow.preprocessInput("n"));
    }
}
