package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

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
 * Updates the specific stat of a given player by incrementing it with the given value
 */
public class UpdateCommand extends Command {

    public static final String COMMAND_WORD = "update";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Updates the specific stat of a given player"
            + " by incrementing it with the given value.\n"
            + "Parameters: "
            + "INDEX (must be a positive integer) "
            + "STAT (must be a valid player stat) [goals|wins|losses] "
            + "VALUE"
            + "\nExample: " + COMMAND_WORD + " 1 wins 5";

    public static final String MESSAGE_SET_PLAYER_SUCCESS = "Update %1$s\n%2$s: %3$s -> %4$s (%5$s)";
    public static final String MESSAGE_NOT_PLAYER = "This person must be a player.";
    public static final String MESSAGE_STAT_OVERFLOW = "Stat value has exceeded the maximum limit."
            + "\nWhy are you trying to overflow it?";

    private final Index index;
    private final StatField stat;
    private final int value;

    /**
     * Creates an UpdateCommand to add the specified {@code Person}
     */
    public UpdateCommand(Index index, StatField stat, int value) {
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
        Player updatedPlayer = updatePlayerStat(player);

        model.setPerson(player, updatedPlayer);
        return new CommandResult(String.format(MESSAGE_SET_PLAYER_SUCCESS,
                Messages.format(player),
                this.stat, old, old + this.value, (
                this.value >= 0 ? "+" : "") + this.value));
    }

    /**
     * Creates and returns a {@code Person} with updated {@code StatField}
     * by incrementing it by the provided {@code value}
     */
    private Player updatePlayerStat(Player player) throws CommandException {
        assert player != null;
        PlayerStats oldStats = player.getStats();
        int oldValue = this.stat.getValue(oldStats);
        int newValue;
        try {
            newValue = Math.addExact(oldValue, this.value);
        } catch (ArithmeticException e) {
            throw new CommandException(MESSAGE_STAT_OVERFLOW);
        }

        if (!this.stat.isValid(newValue)) {
            throw new CommandException(stat.messageConstraints);
        }
        PlayerStats newStats = new PlayerStats(oldStats);
        this.stat.setValue(newStats, newValue);

        return new Player(player, newStats);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UpdateCommand)) {
            return false;
        }

        UpdateCommand otherUpdateCommand = (UpdateCommand) other;
        return index.equals(otherUpdateCommand.index)
                && stat.equals(otherUpdateCommand.stat)
                && value == otherUpdateCommand.value;
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
