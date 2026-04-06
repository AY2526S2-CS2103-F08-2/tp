package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import javafx.collections.ObservableList;

/**
 * Shared formatter for attribute catalog list command outputs.
 * Produces numbered list text with a heading, or an empty-state message when the catalog is empty.
 */
final class AttributeCatalogFormatter {

    private AttributeCatalogFormatter() {}

    /**
     * Formats a catalog list for command feedback.
     *
     * @param items Catalog values to format.
     * @param heading Heading shown at the top of the output (e.g. {@code Teams:}).
     * @param emptyMessage Message returned when {@code items} is empty.
     * @return Formatted command result for display.
     */
    static CommandResult formatCatalogList(ObservableList<?> items, String heading, String emptyMessage) {
        requireNonNull(items);
        requireNonNull(heading);
        requireNonNull(emptyMessage);

        if (items.isEmpty()) {
            return new CommandResult(emptyMessage);
        }

        StringBuilder builder = new StringBuilder(heading);
        for (int i = 0; i < items.size(); i++) {
            builder.append(System.lineSeparator())
                    .append(i + 1)
                    .append(". ")
                    .append(items.get(i));
        }
        return new CommandResult(builder.toString());
    }
}
