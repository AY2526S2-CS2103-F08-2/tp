package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.model.util.SampleDataUtil.getSampleAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests for {@link PositionListCommand}.
 */
public class PositionListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getSampleAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_nonEmptyCatalog_success() {
        String expectedMessage = String.join(System.lineSeparator(),
                "Positions:",
                "1. Unassigned Position",
                "2. Goalkeeper",
                "3. Defender",
                "4. Midfielder",
                "5. Forward");
        assertCommandSuccess(new PositionListCommand(), model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_emptyCatalog_returnsEmptyMessage() {
        Model emptyModel = new ModelManager();
        Model expectedEmptyModel = new ModelManager(emptyModel.getAddressBook(), new UserPrefs());

        assertCommandSuccess(new PositionListCommand(), emptyModel,
                PositionListCommand.MESSAGE_EMPTY, expectedEmptyModel);
    }
}

