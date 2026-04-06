package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEW_ATTRIBUTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OLD_ATTRIBUTE;

import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Shared parser helpers for attribute catalog command parsers.
 * Centralizes common validation so Team/Status/Position parser behavior stays consistent.
 */
final class AttributeParserUtil {

    private AttributeParserUtil() {}

    /**
     * Validates that a required free-form argument is present and returns its trimmed value.
     *
     * @param args Raw parser argument string.
     * @param usage Usage message to include when format validation fails.
     * @return Non-empty trimmed argument value.
     * @throws ParseException If the trimmed argument is empty.
     */
    static String parseRequiredValue(String args, String usage) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, usage));
        }
        return trimmedArgs;
    }

    /**
     * Tokenizes and validates required {@code old/} and {@code new/} prefixed values for attribute edit commands.
     *
     * Validation rules:
     * - both {@code old/} and {@code new/} must be present
     * - no preamble text is allowed
     * - duplicate {@code old/} or {@code new/} prefixes are rejected
     *
     * @param args Raw parser argument string.
     * @param usage Usage message to include when format validation fails.
     * @return Validated tokenized argument map.
     * @throws ParseException If any required prefix is missing or duplicates are present.
     */
    static ArgumentMultimap parseRequiredOldAndNew(String args, String usage) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_OLD_ATTRIBUTE, PREFIX_NEW_ATTRIBUTE);
        if (!argMultimap.getValue(PREFIX_OLD_ATTRIBUTE).isPresent()
                || !argMultimap.getValue(PREFIX_NEW_ATTRIBUTE).isPresent()
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, usage));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_OLD_ATTRIBUTE, PREFIX_NEW_ATTRIBUTE);
        return argMultimap;
    }
}
