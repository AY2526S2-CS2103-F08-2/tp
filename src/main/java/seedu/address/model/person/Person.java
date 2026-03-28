package seedu.address.model.person;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.tag.Tag;

/**
 * Represents a Person in the address book.
 * Guarantees: details are present and not null, field values are validated, immutable.
 */
public abstract class Person {

    // Identity fields
    private final Name name;
    private final Phone phone;
    private final Email email;

    // Data fields
    private final Address address;
    private final Set<Tag> tags = new HashSet<>();
    private final Role role;
    private final Team team;
    private final Status status;
    private final Position position;

    /**
     * Every field must be present and not null. Includes role field.
     */
    protected Person(Name name, Phone phone, Email email, Address address, Set<Tag> tags,
                     Role role, Team team, Status status, Position position) {
        requireAllNonNull(name, phone, email, address, tags, role, team, status, position);
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.tags.addAll(tags);
        this.role = role;
        this.team = team;
        this.status = status;
        this.position = position;
    }

    /**
     * Factory method for creating a person.
     *
     * @param name
     * @param phone
     * @param email
     * @param address
     * @param tags
     * @param role
     * @return {@code Person}
     */
    public static Person createPerson(Name name, Phone phone, Email email,
                                      Address address, Set<Tag> tags, Role role) {
        Team defaultTeam = new Team(Team.DEFAULT_UNASSIGNED_TEAM);
        Status defaultStatus = new Status(Status.DEFAULT_UNKNOWN_STATUS);
        Position defaultPosition = new Position(Position.DEFAULT_UNASSIGNED_POSITION);
        return createPerson(name, phone, email, address, tags, role, defaultTeam, defaultStatus, defaultPosition);
    }

    /**
     * Factory method for creating a person with explicit attributes.
     */
    public static Person createPerson(Name name, Phone phone, Email email,
                                      Address address, Set<Tag> tags, Role role,
                                      Team team, Status status, Position position) {
        return switch (role) {
        case PLAYER -> new Player(name, phone, email, address, tags, team, status, position);
        case STAFF -> new Staff(name, phone, email, address, tags, team, status, position);
        };
    }

    /**
     * Factory method for creating a person with player stats.
     * Expects the person to be a {@code Player}, but will ignore {@code stats} if declared as staff.
     *
     * @param name
     * @param phone
     * @param email
     * @param address
     * @param tags
     * @param role
     * @param stats
     * @return {@code Person}
     */
    public static Person createPerson(Name name, Phone phone, Email email,
                                      Address address, Set<Tag> tags, Role role, PlayerStats stats) {
        Team defaultTeam = new Team(Team.DEFAULT_UNASSIGNED_TEAM);
        Status defaultStatus = new Status(Status.DEFAULT_UNKNOWN_STATUS);
        Position defaultPosition = new Position(Position.DEFAULT_UNASSIGNED_POSITION);
        return createPerson(name, phone, email, address, tags, role, stats, defaultTeam, defaultStatus,
                defaultPosition);
    }

    /**
     * Factory method for creating a person with explicit attributes and player stats.
     */
    public static Person createPerson(Name name, Phone phone, Email email,
                                      Address address, Set<Tag> tags, Role role, PlayerStats stats,
                                      Team team, Status status, Position position) {
        return switch (role) {
        case PLAYER -> new Player(name, phone, email, address, tags, stats, team, status, position);
        case STAFF -> new Staff(name, phone, email, address, tags, team, status, position);
        };
    }

    public Name getName() {
        return name;
    }

    public Phone getPhone() {
        return phone;
    }

    public Email getEmail() {
        return email;
    }

    public Address getAddress() {
        return address;
    }

    /**
     * Returns the role of the person.
     */
    public Role getRole() {
        return role;
    }

    public Team getTeam() {
        return team;
    }

    public Status getStatus() {
        return status;
    }

    public Position getPosition() {
        return position;
    }

    /**
     * Returns an immutable tag set, which throws {@code UnsupportedOperationException}
     * if modification is attempted.
     */
    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    /**
     * Returns true if both persons have the same name.
     * This defines a weaker notion of equality between two persons.
     */
    public boolean isSamePerson(Person otherPerson) {
        if (otherPerson == this) {
            return true;
        }

        return otherPerson != null
                && otherPerson.getName().equals(getName());
    }

    /**
     * Returns true if both persons have the same identity and data fields.
     * This defines a stronger notion of equality between two persons.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Person)) {
            return false;
        }

        Person otherPerson = (Person) other;
        return name.equals(otherPerson.name)
                && phone.equals(otherPerson.phone)
                && email.equals(otherPerson.email)
                && address.equals(otherPerson.address)
                && tags.equals(otherPerson.tags)
                && role.equals(otherPerson.role)
                && team.equals(otherPerson.team)
                && status.equals(otherPerson.status)
                && position.equals(otherPerson.position);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, phone, email, address, tags, role, team, status, position);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("name", name)
                .add("role", role)
                .add("phone", phone)
                .add("email", email)
                .add("address", address)
                .add("team", team)
                .add("status", status)
                .add("position", position)
                .add("tags", tags)
                .toString();
    }

}
