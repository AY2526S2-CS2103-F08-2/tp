package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.Model;

/**
 * Prompts the UI to open a file chooser for importing contacts from a CSV file.
 */
public class ImportCsvCommand extends Command {

    public static final String COMMAND_WORD = "importcsv";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Opens a dialog for selecting a CSV file to import.";

    public static final String MESSAGE_OPEN_IMPORT_DIALOG_SUCCESS =
            "Opening CSV import dialog...";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        return new CommandResult(MESSAGE_OPEN_IMPORT_DIALOG_SUCCESS, false, false, true);
    }

    @Override
    public boolean equals(Object other) {
        return other == this || other instanceof ImportCsvCommand;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).toString();
    }
}
