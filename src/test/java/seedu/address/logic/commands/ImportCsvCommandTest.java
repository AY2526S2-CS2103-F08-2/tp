package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class ImportCsvCommandTest {

    private final Model model = new ModelManager(new AddressBook(), new UserPrefs());

    @Test
    public void execute_returnsCommandResultWithImportChooserFlag() throws Exception {
        ImportCsvCommand command = new ImportCsvCommand();

        CommandResult result = command.execute(model);

        assertEquals(ImportCsvCommand.MESSAGE_OPEN_IMPORT_DIALOG_SUCCESS, result.getFeedbackToUser());
        assertTrue(result.isOpenFileChooser());
    }

    @Test
    public void equals() {
        ImportCsvCommand firstCommand = new ImportCsvCommand();
        ImportCsvCommand secondCommand = new ImportCsvCommand();

        assertTrue(firstCommand.equals(firstCommand));
        assertTrue(firstCommand.equals(secondCommand));
    }
}
