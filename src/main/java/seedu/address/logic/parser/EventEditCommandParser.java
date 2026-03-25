package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PLAYER;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EventEditCommand;
import seedu.address.logic.commands.EventEditCommand.EditEventDescriptor;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new EditEventCommand object
 */
public class EventEditCommandParser implements Parser<EventEditCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EventEditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_EVENT_TYPE, PREFIX_DATE, PREFIX_PLAYER);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EventEditCommand.MESSAGE_USAGE), pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_EVENT_TYPE, PREFIX_DATE);

        EditEventDescriptor editEventDescriptor = new EditEventDescriptor();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editEventDescriptor.setEventName(ParserUtil.parseEventName(argMultimap.getValue(PREFIX_NAME).get()));
        }
        if (argMultimap.getValue(PREFIX_EVENT_TYPE).isPresent()) {
            editEventDescriptor.setEventType(ParserUtil.parseEventType(argMultimap.getValue(PREFIX_EVENT_TYPE).get()));
        }
        if (argMultimap.getValue(PREFIX_DATE).isPresent()) {
            editEventDescriptor.setEventDate(ParserUtil.parseDate(argMultimap.getValue(PREFIX_DATE).get()));
        }

        parseEventPlayerNamesForEdit(argMultimap.getAllValues(PREFIX_PLAYER)).ifPresent(editEventDescriptor::setEventPlayerNames);
        if (!editEventDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EventEditCommand.MESSAGE_NOT_EDITED);
        }

        return new EventEditCommand(index, editEventDescriptor);
    }

    /**
     * Parses {@code Collection<String> eventPlayerNames} into a {@code Set<String>} if {@code eventPlayerNames} is
     * non-empty.If {@code tags} contain only one element which is an empty string, it will be parsed into a
     * {@code Set<String>} containing zero player names.
     */
    private Optional<Set<String>> parseEventPlayerNamesForEdit(Collection<String> eventPlayerNames)
            throws ParseException {
        assert eventPlayerNames != null;

        if (eventPlayerNames.isEmpty()) {
            return Optional.empty();
        }
        Collection<String> eventPlayerNamesSet = eventPlayerNames.size() == 1
                && eventPlayerNames.contains("") ? Collections.emptySet() : eventPlayerNames;

        return Optional.of(new HashSet<>(ParserUtil.parsePlayers(eventPlayerNamesSet)));
    }
}
