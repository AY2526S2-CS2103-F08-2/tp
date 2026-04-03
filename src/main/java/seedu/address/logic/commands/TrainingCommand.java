package seedu.address.logic.commands;

import static java.util.Objects.isNull;
import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PLAYER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POSITION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEAM;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.Date;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventName;
import seedu.address.model.event.EventPlayerList;
import seedu.address.model.event.EventType;
import seedu.address.model.person.Person;
import seedu.address.model.person.Position;
import seedu.address.model.person.Role;
import seedu.address.model.person.Status;
import seedu.address.model.person.Team;

/**
 * Adds a training to the address book.
 */
public class TrainingCommand extends Command {

    public static final String COMMAND_WORD = "training";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a training to the address book. "
            + "Parameters: "
            + PREFIX_NAME + "TRAINING NAME "
            + PREFIX_DATE + "DATE "
            + "[" + PREFIX_STATUS + "STATUS]"
            + "[" + PREFIX_POSITION + "POSITION]"
            + "[" + PREFIX_TEAM + "TEAM]"
            + "[" + PREFIX_STATUS + "STATUS]"
            + "[" + PREFIX_PLAYER + "PLAYER]...\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "Warm Up "
            + PREFIX_DATE + "2026-04-15 1600 "
            + PREFIX_PLAYER + "Alex Yeoh ";

    public static final String MESSAGE_SUCCESS = "New Training added: %1$s";
    public static final String MESSAGE_DUPLICATE_TRAINING = "This training already exists in the address book!";
    public static final String MESSAGE_PERSON_DOES_NOT_EXIST = "%s does not exist in the address book!";
    public static final String MESSAGE_NOT_A_PLAYER = "%s is not a player!";
    public static final String MESSAGE_STATUS_DOES_NOT_EXIST = "Status %s does not exist in the address book!";
    public static final String MESSAGE_POSITION_DOES_NOT_EXIST = "Position %s does not exist in the address book!";
    public static final String MESSAGE_TEAM_DOES_NOT_EXIST = "Team %s does not exist in the address book!";

    private final EventName eventName;
    private final Date date;
    private final Status status;
    private final Position position;
    private final Team team;
    private final List<String> playerNames;

    /**
     * Creates a TrainingCommand. {@code Training} is not created yet as {@code playerNames} has not been validated.
     */
    public TrainingCommand(EventName eventName, Date date, Status status,
                           Position position, Team team, List<String> playerNames) {
        requireNonNull(eventName);
        requireNonNull(date);
        requireNonNull(playerNames);
        this.eventName = eventName;
        this.date = date;
        this.status = status;
        this.team = team;
        this.position = position;
        this.playerNames = playerNames;
    }

    /**
     * Gets all players with the specified status
     * @param model
     * @return {@code Set<Person>}
     */
    private Set<Person> getPlayersWithStatus(Model model) throws CommandException {
        if (!model.getAddressBook().getStatusList().contains(status)) {
            throw new CommandException(String.format(MESSAGE_STATUS_DOES_NOT_EXIST, status));
        }

        List<Person> personList = model.getAddressBook().getPersonList()
                .stream().filter(p -> p.getStatus().equals(status) && p.getRole() == Role.PLAYER).toList();
        return new HashSet<Person>(personList);
    }

    /**
     * Gets all players with the specified team
     * @param model
     * @return {@code Set<Person>}
     */
    private Set<Person> getPlayersWithTeam(Model model) throws CommandException {
        if (!model.getAddressBook().getTeamList().contains(team)) {
            throw new CommandException(String.format(MESSAGE_TEAM_DOES_NOT_EXIST, team));
        }

        List<Person> personList = model.getAddressBook().getPersonList()
                .stream().filter(p -> p.getTeam().equals(team) && p.getRole() == Role.PLAYER).toList();
        return new HashSet<Person>(personList);
    }

    /**
     * Gets all players with the specified position
     * @param model
     * @return {@code Set<Person>}
     */
    private Set<Person> getPlayersWithPosition(Model model) throws CommandException {
        if (!model.getAddressBook().getPositionList().contains(position)) {
            throw new CommandException(String.format(MESSAGE_POSITION_DOES_NOT_EXIST, position));
        }

        List<Person> personList = model.getAddressBook().getPersonList()
                .stream().filter(p -> p.getPosition().equals(position) && p.getRole() == Role.PLAYER).toList();
        return new HashSet<Person>(personList);
    }

    /**
     * Gets all players with the specified names
     * @param model
     * @return
     */
    private Set<Person> getPlayersWithNames(Model model) throws CommandException {
        Set<Person> playerSet = new HashSet<>();

        for (String playerName : playerNames) {
            Person person = model.getAddressBook().getPersonList().stream()
                    .filter(p -> p.getName().toString().equals(playerName.trim()))
                    .findFirst()
                    .orElseThrow(() -> new CommandException(String.format(MESSAGE_PERSON_DOES_NOT_EXIST, playerName)));

            if (person.getRole() != Role.PLAYER) {
                throw new CommandException(String.format(MESSAGE_NOT_A_PLAYER, playerName));
            }

            playerSet.add(person);
        }

        return playerSet;
    }

    /**
     * Gets all players that have all the specified attributes if given. Then add players that were
     * specified in the command by name.
     * @param model
     * @return {@code Set<Person>}
     */
    private Set<Person> getAllPlayers(Model model) throws CommandException {
        Set<Person> result = null;

        if (!isNull(status)) {
            result = new HashSet<>(getPlayersWithStatus(model));
        }

        if (!isNull(position)) {
            if (result == null) {
                result = new HashSet<>(getPlayersWithPosition(model));
            } else {
                result.retainAll(getPlayersWithPosition(model));
            }
        }

        if (!isNull(team)) {
            if (result == null) {
                result = new HashSet<>(getPlayersWithTeam(model));
            } else {
                result.retainAll(getPlayersWithTeam(model));
            }
        }

        // if none of the filters were applied
        if (result == null) {
            result = new HashSet<>();
        }

        result.addAll(getPlayersWithNames(model));

        return result;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException, RuntimeException {
        requireNonNull(model);

        Set<Person> playerList = getAllPlayers(model);

        EventPlayerList eventPlayerList = new EventPlayerList(playerList);
        Event toAdd = Event.createEvent(eventName, date, EventType.TRAINING, eventPlayerList);

        if (model.hasEvent(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_TRAINING);
        }

        model.addEvent(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, Messages.format(toAdd)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TrainingCommand)) {
            return false;
        }

        TrainingCommand otherTrainingCommand = (TrainingCommand) other;
        return eventName.equals(otherTrainingCommand.eventName)
               && date.equals(otherTrainingCommand.date)
               && playerNames.equals(otherTrainingCommand.playerNames);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("Training: ", eventName)
                .add("Date: ", date)
                .add("Player Names: ", playerNames)
                .toString();
    }
}
