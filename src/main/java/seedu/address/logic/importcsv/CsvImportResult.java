package seedu.address.logic.importcsv;

import java.util.ArrayList;
import java.util.List;

public class CsvImportResult {
    private int totalRowsProcessed;
    private int successfulImports;
    private final List<String> failureMessages = new ArrayList<>();

    public void incrementProcessed() {
        totalRowsProcessed++;
    }

    public void incrementSuccessful() {
        successfulImports++;
    }

    public void addFailureMessage(String message) {
        failureMessages.add(message);
    }

    public int getTotalRowsProcessed() {
        return totalRowsProcessed;
    }

    public int getSuccessfulImports() {
        return successfulImports;
    }

    public int getFailedImports() {
        return failureMessages.size();
    }

    public List<String> getFailureMessages() {
        return failureMessages;
    }

    public String toUserMessage() {
        StringBuilder sb = new StringBuilder();
        sb.append("CSV import completed.\n")
                .append("Rows processed: ").append(totalRowsProcessed).append("\n")
                .append("Successfully imported: ").append(successfulImports).append("\n")
                .append("Failed: ").append(getFailedImports());

        if (!failureMessages.isEmpty()) {
            sb.append("\n\nFailures:");
            for (String message : failureMessages) {
                sb.append("\n- ").append(message);
            }
        }

        return sb.toString();
    }
}
