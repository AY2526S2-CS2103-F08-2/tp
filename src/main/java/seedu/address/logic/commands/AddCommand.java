package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POSITION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEAM;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Role;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a contact to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_ROLE + "ROLE "
            + PREFIX_PHONE + "PHONE "
            + PREFIX_EMAIL + "EMAIL "
            + PREFIX_ADDRESS + "ADDRESS "
            + "[" + PREFIX_TEAM + "TEAM] "
            + "[" + PREFIX_STATUS + "STATUS] "
            + "[" + PREFIX_POSITION + "POSITION] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_ROLE + "staff "
            + PREFIX_PHONE + "98765432 "
            + PREFIX_EMAIL + "johnd@example.com "
            + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25 "
            + PREFIX_TAG + "friends "
            + PREFIX_TAG + "owesMoney";

    public static final String MESSAGE_SUCCESS = "New %1$s added: %2$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book";
    public static final String MESSAGE_TEAM_NOT_IN_CATALOG = "The specified team does not exist in the catalog";
    public static final String MESSAGE_STATUS_NOT_IN_CATALOG = "The specified status does not exist in the catalog";
    public static final String MESSAGE_POSITION_NOT_IN_CATALOG =
            "The specified position does not exist in the catalog";
    public static final String MESSAGE_POSITION_NOT_APPLICABLE_TO_STAFF =
            "Position can only be assigned to players";

    private final Person toAdd;

    /**
     * Creates an AddCommand to add the specified {@code Person}
     */
    public AddCommand(Person person) {
        requireNonNull(person);
        toAdd = person;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Person personToAdd = toAdd;

        if (!model.hasTeam(personToAdd.getTeam())) {
            throw new CommandException(MESSAGE_TEAM_NOT_IN_CATALOG);
        }
        if (!model.hasStatus(personToAdd.getStatus())) {
            throw new CommandException(MESSAGE_STATUS_NOT_IN_CATALOG);
        }
        if (!model.hasPosition(personToAdd.getPosition())) {
            throw new CommandException(MESSAGE_POSITION_NOT_IN_CATALOG);
        }
        if (personToAdd.getRole() == Role.STAFF && !personToAdd.getPosition().isDefaultUnassignedPosition()) {
            throw new CommandException(MESSAGE_POSITION_NOT_APPLICABLE_TO_STAFF);
        }

        personToAdd = Person.createPerson(personToAdd.getName(), personToAdd.getPhone(),
                personToAdd.getEmail(), personToAdd.getAddress(), personToAdd.getTags(), personToAdd.getRole(),
                AttributeCatalogResolver.resolveTeam(model, personToAdd.getTeam()),
                AttributeCatalogResolver.resolveStatus(model, personToAdd.getStatus()),
                AttributeCatalogResolver.resolvePosition(model, personToAdd.getPosition()));

        if (model.hasPerson(personToAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }

        model.addPerson(personToAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, personToAdd.getRole().toString(),
                Messages.format(personToAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddCommand)) {
            return false;
        }

        AddCommand otherAddCommand = (AddCommand) other;
        return toAdd.equals(otherAddCommand.toAdd);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("toAdd", toAdd)
                .toString();
    }
}
