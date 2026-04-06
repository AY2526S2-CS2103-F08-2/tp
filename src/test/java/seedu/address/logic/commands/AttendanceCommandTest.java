package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.testutil.TypicalEvents;

/**
 * A testing class for attendance command
 */
public class AttendanceCommandTest {
    @Test
    public void execute_typicalEvents_success() {
        Model model = new ModelManager(TypicalEvents.getTypicalAddressBookWithEvents(), new UserPrefs());

        String expectedReport = model.getAttendanceReport();
        String expectedMessage = AttendanceCommand.MESSAGE_SUCCESS + expectedReport;

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        assertCommandSuccess(new AttendanceCommand(), model, expectedMessage, expectedModel);
    }
}
