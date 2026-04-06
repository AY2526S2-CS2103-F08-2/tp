package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.Player;
import seedu.address.model.person.PlayerStats;
import seedu.address.model.person.StatField;


/**
 * Sets the specific stat of a given player
 */
public class SetCommand extends Command {

    public static final String COMMAND_WORD = "set";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets the specific stat of a given player.\n"
            + "Parameters: "
            + "INDEX (must be a positive integer) "
            + "STAT (must be a valid player stat) [goals|wins|losses] "
            + "VALUE"
            + "\nExample: " + COMMAND_WORD + " 1 goals 10";

    public static final String MESSAGE_SET_PLAYER_SUCCESS = "Set %1$s\n%2$s: %3$s -> %4$s";
    public static final String MESSAGE_NOT_PLAYER = "This person must be a player.";

    private final Index index;
    private final StatField stat;
    private final int value;

    /**
     * Creates an SetCommand to add the specified {@code Person}
     */
    public SetCommand(Index index, StatField stat, int value) {
        requireNonNull(index);
        requireNonNull(stat);

        this.index = index;
        this.stat = stat;
        this.value = value;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person person = lastShownList.get(index.getZeroBased());
        if (!(person instanceof Player)) {
            throw new CommandException(MESSAGE_NOT_PLAYER);
        }

        Player player = (Player) person;
        int old = this.stat.getValue(player.getStats());
        Player updatedPlayer = setPlayerStat(player);

        model.setPerson(player, updatedPlayer);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_SET_PLAYER_SUCCESS,
                Messages.format(player), this.stat, old, this.value));
    }

    /**
     * Creates and returns a {@code Person} with updated {@code StatField}
     * based on the set {@code value}
     */
    private Player setPlayerStat(Player player) throws CommandException {
        assert player != null;
        if (!this.stat.isValid(this.value)) {
            throw new CommandException(stat.messageConstraints);
        }
        PlayerStats oldStats = player.getStats();
        PlayerStats newStats = new PlayerStats(oldStats);
        this.stat.setValue(newStats, this.value);

        return new Player(player, newStats);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SetCommand)) {
            return false;
        }

        SetCommand otherSetCommand = (SetCommand) other;
        return index.equals(otherSetCommand.index)
                && stat.equals(otherSetCommand.stat)
                && value == otherSetCommand.value;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("stat", stat)
                .add("value", value)
                .toString();
    }
}
