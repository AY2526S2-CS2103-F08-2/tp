package seedu.address.model.person;

import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a Player.
 */
public class Player extends Person {

    private final PlayerStats stats;

    /**
     * Constructs a {@code Player} with the given data, with a fresh set of stats.
     *
     * @param name the player name
     * @param phone the player phone number
     * @param email the player email
     * @param address the player address
     * @param tags the player tags
     */
    public Player(Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        super(name, phone, email, address, tags, Role.PLAYER);
        this.stats = new PlayerStats();
    }

    /**
     * Constructs a {@code Player} with the given data.
     *
     * @param name the player name
     * @param phone the player phone number
     * @param email the player email
     * @param address the player address
     * @param tags the player tags
     * @param stats the player stats
     */
    public Player(Name name, Phone phone, Email email, Address address, Set<Tag> tags, PlayerStats stats) {
        super(name, phone, email, address, tags, Role.PLAYER);
        assert stats != null;
        this.stats = stats;
    }

    public PlayerStats getStats() {
        return stats;
    }
}
