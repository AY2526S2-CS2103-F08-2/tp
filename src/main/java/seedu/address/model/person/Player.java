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
        this(name, phone, email, address, tags,
                new Team(Team.DEFAULT_UNASSIGNED_TEAM),
                new Status(Status.DEFAULT_UNKNOWN_STATUS),
                new Position(Position.DEFAULT_UNASSIGNED_POSITION));
    }

    /**
     * Constructs a {@code Player} with explicit attributes and fresh stats.
     */
    public Player(Name name, Phone phone, Email email, Address address, Set<Tag> tags,
                  Team team, Status status, Position position) {
        super(name, phone, email, address, tags, Role.PLAYER, team, status, position);
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
        this(name, phone, email, address, tags, stats,
                new Team(Team.DEFAULT_UNASSIGNED_TEAM),
                new Status(Status.DEFAULT_UNKNOWN_STATUS),
                new Position(Position.DEFAULT_UNASSIGNED_POSITION));
    }

    /**
     * Constructs a {@code Player} with explicit attributes and stats.
     */
    public Player(Name name, Phone phone, Email email, Address address, Set<Tag> tags,
                  PlayerStats stats, Team team, Status status, Position position) {
        super(name, phone, email, address, tags, Role.PLAYER, team, status, position);
        assert stats != null;
        this.stats = stats;
    }

    public PlayerStats getStats() {
        return stats;
    }
}
