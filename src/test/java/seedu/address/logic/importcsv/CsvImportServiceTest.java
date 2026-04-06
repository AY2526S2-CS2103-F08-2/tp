package seedu.address.logic.importcsv;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class CsvImportServiceTest {
    @TempDir
    public Path tempDir;

    private final CsvImportService service = new CsvImportService();

    @Test
    public void importCsv_validSingleRow_success() throws Exception {
        Path csvFile = createCsvFile("validSingleRow.csv",
                "name,role,address,phone,email,tags",
                "John Doe,player,123 Clementi Ave,91234567,john@example.com,captain;injured");

        Model model = new ModelManager(new AddressBook(), new UserPrefs());

        CsvImportResult result = service.importCsv(csvFile, model);

        assertEquals(1, result.getTotalRowsProcessed());
        assertEquals(1, result.getSuccessfulImports());
        assertEquals(0, result.getFailedImports());
        assertEquals(1, model.getFilteredPersonList().size());
    }

    @Test
    public void importCsv_validMultipleRows_success() throws Exception {
        Path csvFile = createCsvFile("validMultipleRows.csv",
                "name,role,address,phone,email,tags",
                "John Doe,player,123 Clementi Ave,91234567,john@example.com,captain;injured",
                "Jane Tan,staff,456 Jurong West,92345678,jane@example.com,manager");

        Model model = new ModelManager(new AddressBook(), new UserPrefs());

        CsvImportResult result = service.importCsv(csvFile, model);

        assertEquals(2, result.getTotalRowsProcessed());
        assertEquals(2, result.getSuccessfulImports());
        assertEquals(0, result.getFailedImports());
        assertEquals(2, model.getFilteredPersonList().size());
    }

    @Test
    public void importCsv_emptyFile_throwsCommandException() throws Exception {
        Path csvFile = createCsvFile("empty.csv");

        Model model = new ModelManager(new AddressBook(), new UserPrefs());

        assertThrows(CommandException.class, () -> service.importCsv(csvFile, model));
    }

    @Test
    public void importCsv_invalidHeaderMissingField_throwsCommandException() throws Exception {
        Path csvFile = createCsvFile("missingHeaderField.csv",
                "name,role,address,phone,email",
                "John Doe,player,123 Clementi Ave,91234567,john@example.com");

        Model model = new ModelManager(new AddressBook(), new UserPrefs());

        assertThrows(CommandException.class, () -> service.importCsv(csvFile, model));
    }

    @Test
    public void importCsv_invalidHeaderUnexpectedField_throwsCommandException() throws Exception {
        Path csvFile = createCsvFile("unexpectedHeaderField.csv",
                "name,role,address,phone,email,tags,extra",
                "John Doe,player,123 Clementi Ave,91234567,john@example.com,captain,extra");

        Model model = new ModelManager(new AddressBook(), new UserPrefs());

        assertThrows(CommandException.class, () -> service.importCsv(csvFile, model));
    }

    @Test
    public void importCsv_invalidHeaderWrongOrder_throwsCommandException() throws Exception {
        Path csvFile = createCsvFile("wrongHeaderOrder.csv",
                "name,address,role,phone,email,tags",
                "John Doe,123 Clementi Ave,player,91234567,john@example.com,captain");

        Model model = new ModelManager(new AddressBook(), new UserPrefs());

        assertThrows(CommandException.class, () -> service.importCsv(csvFile, model));
    }

    @Test
    public void importCsv_invalidRow_skipsAndContinues() throws Exception {
        Path csvFile = createCsvFile("invalidRowSkips.csv",
                "name,role,address,phone,email,tags",
                "John Doe,player,123 Clementi Ave,91234567,john@example.com,captain",
                "Bad Email,staff,456 Avenue,92345678,not-an-email,manager",
                "Jane Tan,staff,789 Road,93456789,jane@example.com,coach");

        Model model = new ModelManager(new AddressBook(), new UserPrefs());

        CsvImportResult result = service.importCsv(csvFile, model);

        assertEquals(3, result.getTotalRowsProcessed());
        assertEquals(2, result.getSuccessfulImports());
        assertEquals(1, result.getFailedImports());
        assertEquals(2, model.getFilteredPersonList().size());
        assertTrue(result.getFailureMessages().get(0).contains("Row 3"));
    }

    @Test
    public void importCsv_duplicateAgainstExistingModel_skipsDuplicate() throws Exception {
        Path csvFile = createCsvFile("duplicateExisting.csv",
                "name,role,address,phone,email,tags",
                "John Doe,player,123 Clementi Ave,91234567,john@example.com,captain");

        Model model = new ModelManager(new AddressBook(), new UserPrefs());
        service.importCsv(csvFile, model);

        CsvImportResult secondResult = service.importCsv(csvFile, model);

        assertEquals(1, secondResult.getTotalRowsProcessed());
        assertEquals(0, secondResult.getSuccessfulImports());
        assertEquals(1, secondResult.getFailedImports());
        assertEquals(1, model.getFilteredPersonList().size());
        assertTrue(secondResult.getFailureMessages().get(0).toLowerCase().contains("duplicate"));
    }

    @Test
    public void importCsv_duplicateWithinSameFile_importsFirstSkipsSecond() throws Exception {
        Path csvFile = createCsvFile("duplicateWithinSameFile.csv",
                "name,role,address,phone,email,tags",
                "John Doe,player,123 Clementi Ave,91234567,john@example.com,captain",
                "John Doe,player,123 Clementi Ave,91234567,john@example.com,captain");

        Model model = new ModelManager(new AddressBook(), new UserPrefs());

        CsvImportResult result = service.importCsv(csvFile, model);

        assertEquals(2, result.getTotalRowsProcessed());
        assertEquals(1, result.getSuccessfulImports());
        assertEquals(1, result.getFailedImports());
        assertEquals(1, model.getFilteredPersonList().size());
        assertTrue(result.getFailureMessages().get(0).toLowerCase().contains("duplicate"));
    }

    @Test
    public void importCsv_escapedCommaField_success() throws Exception {
        Path csvFile = createCsvFile("escapedComma.csv",
                "name,role,address,phone,email,tags",
                "John Doe,player,123 Clementi Ave\\, Block 2,91234567,john@example.com,captain");

        Model model = new ModelManager(new AddressBook(), new UserPrefs());

        CsvImportResult result = service.importCsv(csvFile, model);

        assertEquals(1, result.getTotalRowsProcessed());
        assertEquals(1, result.getSuccessfulImports());
        assertEquals(0, result.getFailedImports());
        assertEquals(1, model.getFilteredPersonList().size());
    }

    @Test
    public void importCsv_escapedCommaInField_success() throws Exception {
        Path csvFile = createCsvFile("escapedCommaTags.csv",
                "name,role,address,phone,email,tags",
                "John Doe,player,test\\,address,91234567,john@example.com,tag1;tag2");

        Model model = new ModelManager(new AddressBook(), new UserPrefs());

        CsvImportResult result = service.importCsv(csvFile, model);

        assertEquals(1, result.getTotalRowsProcessed());
        assertEquals(1, result.getSuccessfulImports());
        assertEquals(0, result.getFailedImports());
    }

    @Test
    public void importCsv_trailingBackslash_preservedInField() throws Exception {
        Path csvFile = createCsvFile("trailingBackslash.csv",
                "name,role,address,phone,email,tags",
                "John Doe,player,address\\,91234567,john@example.com,captain");

        Model model = new ModelManager(new AddressBook(), new UserPrefs());

        CsvImportResult result = service.importCsv(csvFile, model);

        assertEquals(1, result.getTotalRowsProcessed());
    }

    @Test
    public void importCsv_quotesTreatedAsNormalCharacters_rowHandledNormally() throws Exception {
        Path csvFile = createCsvFile("quotesLiteral.csv",
                "name,role,address,phone,email,tags",
                "\"John Doe\",player,address,91234567,john@example.com,captain");

        Model model = new ModelManager(new AddressBook(), new UserPrefs());

        CsvImportResult result = service.importCsv(csvFile, model);

        assertEquals(1, result.getTotalRowsProcessed());
    }

    @Test
    public void importCsv_wrongNumberOfFields_skipsRow() throws Exception {
        Path csvFile = createCsvFile("wrongNumberOfFields.csv",
                "name,role,address,phone,email,tags",
                "John Doe,player,123 Clementi Ave,91234567,john@example.com",
                "Jane Tan,staff,456 Jurong West,92345678,jane@example.com,manager");

        Model model = new ModelManager(new AddressBook(), new UserPrefs());

        CsvImportResult result = service.importCsv(csvFile, model);

        assertEquals(2, result.getTotalRowsProcessed());
        assertEquals(1, result.getSuccessfulImports());
        assertEquals(1, result.getFailedImports());
        assertEquals(1, model.getFilteredPersonList().size());
        assertTrue(result.getFailureMessages().get(0).contains("Row 2"));
    }

    @Test
    public void importCsv_blankLine_ignored() throws Exception {
        Path csvFile = createCsvFile("blankLineIgnored.csv",
                "name,role,address,phone,email,tags",
                "",
                "John Doe,player,123 Clementi Ave,91234567,john@example.com,captain",
                "");

        Model model = new ModelManager(new AddressBook(), new UserPrefs());

        CsvImportResult result = service.importCsv(csvFile, model);

        assertEquals(1, result.getTotalRowsProcessed());
        assertEquals(1, result.getSuccessfulImports());
        assertEquals(0, result.getFailedImports());
        assertEquals(1, model.getFilteredPersonList().size());
    }

    private Path createCsvFile(String fileName, String... lines) throws IOException {
        Path file = tempDir.resolve(fileName);
        Files.write(file, List.of(lines));
        return file;
    }
}
