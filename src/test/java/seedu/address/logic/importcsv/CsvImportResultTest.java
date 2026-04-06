package seedu.address.logic.importcsv;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class CsvImportResultTest {

    @Test
    public void toUserMessage_noFailures_successMessage() {
        CsvImportResult result = new CsvImportResult();
        result.incrementProcessed();
        result.incrementSuccessful();

        String message = result.toUserMessage();

        assertTrue(message.contains("Rows processed: 1"));
        assertTrue(message.contains("Successfully imported: 1"));
        assertTrue(message.contains("Failed: 0"));
    }

    @Test
    public void toUserMessage_withFailures_includesFailureMessages() {
        CsvImportResult result = new CsvImportResult();
        result.incrementProcessed();
        result.incrementProcessed();
        result.incrementSuccessful();
        result.addFailureMessage("Row 3: duplicate person");

        String message = result.toUserMessage();

        assertTrue(message.contains("Rows processed: 2"));
        assertTrue(message.contains("Successfully imported: 1"));
        assertTrue(message.contains("Failed: 1"));
        assertTrue(message.contains("Row 3: duplicate person"));
    }

    @Test
    public void getFailedImports_returnsFailureMessageCount() {
        CsvImportResult result = new CsvImportResult();
        result.addFailureMessage("Row 2: invalid email");
        result.addFailureMessage("Row 4: duplicate person");

        assertEquals(2, result.getFailedImports());
    }
}
