package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Position;
import seedu.address.model.person.Role;
import seedu.address.model.person.Status;
import seedu.address.model.person.Team;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Person objects.
 */
public class PersonBuilder {

    public static final String DEFAULT_NAME = "Sugon Dese";
    public static final String DEFAULT_PHONE = "24734435";
    public static final String DEFAULT_EMAIL = "sugondese@gmail.com";
    public static final String DEFAULT_ADDRESS = "Changi Airport";
    public static final Role DEFAULT_ROLE = Role.PLAYER;
    public static final String DEFAULT_TEAM = Team.DEFAULT_UNASSIGNED_TEAM;
    public static final String DEFAULT_STATUS = Status.DEFAULT_UNKNOWN_STATUS;
    public static final String DEFAULT_POSITION = Position.DEFAULT_UNASSIGNED_POSITION;

    private Name name;
    private Phone phone;
    private Email email;
    private Address address;
    private Set<Tag> tags;
    private Role role;
    private Team team;
    private Status status;
    private Position position;

    /**
     * Creates a {@code PersonBuilder} with the default details.
     */
    public PersonBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        address = new Address(DEFAULT_ADDRESS);
        tags = new HashSet<>();
        role = DEFAULT_ROLE;
        team = new Team(DEFAULT_TEAM);
        status = new Status(DEFAULT_STATUS);
        position = new Position(DEFAULT_POSITION);
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public PersonBuilder(Person personToCopy) {
        name = personToCopy.getName();
        phone = personToCopy.getPhone();
        email = personToCopy.getEmail();
        address = personToCopy.getAddress();
        tags = new HashSet<>(personToCopy.getTags());
        role = personToCopy.getRole();
        team = personToCopy.getTeam();
        status = personToCopy.getStatus();
        position = personToCopy.getPosition();
    }

    /**
     * Sets the {@code Name} of the {@code Person} that we are building.
     */
    public PersonBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Person} that we are building.
     */
    public PersonBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Person} that we are building.
     */
    public PersonBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Person} that we are building.
     */
    public PersonBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }

    /**
     * Sets the {@code Role} of the {@code Person} that we are building.
     */
    public PersonBuilder withRole(Role role) {
        this.role = role;
        return this;
    }

    /**
     * Sets the {@code Team} of the {@code Person} that we are building.
     */
    public PersonBuilder withTeam(String team) {
        this.team = new Team(team);
        return this;
    }

    /**
     * Sets the {@code Status} of the {@code Person} that we are building.
     */
    public PersonBuilder withStatus(String status) {
        this.status = new Status(status);
        return this;
    }

    /**
     * Sets the {@code Position} of the {@code Person} that we are building.
     */
    public PersonBuilder withPosition(String position) {
        this.position = new Position(position);
        return this;
    }

    /**
     * Builds and returns a {@code Person} with the current attributes.
     */
    public Person build() {
        return Person.createPerson(name, phone, email, address, tags, role, team, status, position);
    }

}
