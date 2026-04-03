package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.model.util.SampleDataUtil.getSampleAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests for {@link StatusListCommand}.
 */
public class StatusListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getSampleAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
    }

    @Test
    // BOUNDARY (non-empty catalog state)
    public void execute_nonEmptyCatalog_success() {
        String expectedMessage = String.join(System.lineSeparator(),
                "Statuses:",
                "1. Unknown",
                "2. Active",
                "3. Unavailable");
        assertCommandSuccess(new StatusListCommand(), model, expectedMessage, expectedModel);
    }

    @Test
    // BOUNDARY (empty catalog state)
    public void execute_emptyCatalog_returnsEmptyMessage() {
        Model emptyModel = new ModelManager();
        Model expectedEmptyModel = new ModelManager(emptyModel.getAddressBook(), new UserPrefs());

        assertCommandSuccess(new StatusListCommand(), emptyModel, StatusListCommand.MESSAGE_EMPTY, expectedEmptyModel);
    }
}
