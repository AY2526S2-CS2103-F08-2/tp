package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Player;
import seedu.address.model.person.PlayerStats;
import seedu.address.model.person.Position;
import seedu.address.model.person.Role;
import seedu.address.model.person.Status;
import seedu.address.model.person.Team;
import seedu.address.model.tag.Tag;

/**
 * Jackson-friendly version of {@link Person}.
 */
class JsonAdaptedPerson {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Person's %s field is missing!";
    private static final Logger logger = LogsCenter.getLogger(JsonAdaptedPerson.class);

    private final String name;
    private final String phone;
    private final String email;
    private final String address;
    private final List<JsonAdaptedTag> tags = new ArrayList<>();
    private final String role;
    private final JsonAdaptedPlayerStats stats;
    private final String team;
    private final String status;
    private final String position;


    /**
     * Constructs a {@code JsonAdaptedPerson} with the given person details.
     *
     * Note: {@code stats} is only applicable to those with the {@link Player} role.
     */
    @JsonCreator
    public JsonAdaptedPerson(@JsonProperty("name") String name, @JsonProperty("phone") String phone,
                             @JsonProperty("email") String email, @JsonProperty("address") String address,
                             @JsonProperty("tags") List<JsonAdaptedTag> tags, @JsonProperty("role") String role,
                             @JsonProperty("stats") JsonAdaptedPlayerStats stats,
                             @JsonProperty("team") String team,
                             @JsonProperty("status") String status,
                             @JsonProperty("position") String position) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        if (tags != null) {
            this.tags.addAll(tags);
        }
        this.role = role;
        this.stats = stats;
        this.team = team;
        this.status = status;
        this.position = position;
    }

    /**
     * Converts a given {@code Person} into this class for Jackson use.
     */
    public JsonAdaptedPerson(Person source) {
        name = source.getName().fullName;
        phone = source.getPhone().value;
        email = source.getEmail().value;
        address = source.getAddress().value;
        tags.addAll(source.getTags().stream()
                .map(JsonAdaptedTag::new)
                .collect(Collectors.toList()));
        role = source.getRole().name();

        if (source.getRole().equals(Role.PLAYER)) {
            Player player = (Player) source;
            this.stats = new JsonAdaptedPlayerStats(player.getStats());
        } else {
            stats = null;
        }
        team = source.getTeam().value;
        status = source.getStatus().value;
        position = source.getPosition().value;
    }

    /**
     * Converts this Jackson-friendly adapted person object into the model's {@code Person} object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted person.
     */
    public Person toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (JsonAdaptedTag tag : tags) {
            personTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (phone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Phone.class.getSimpleName()));
        }
        if (!Phone.isValidPhone(phone)) {
            throw new IllegalValueException(Phone.MESSAGE_CONSTRAINTS);
        }
        final Phone modelPhone = new Phone(phone);

        if (email == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Email.class.getSimpleName()));
        }
        if (!Email.isValidEmail(email)) {
            throw new IllegalValueException(Email.MESSAGE_CONSTRAINTS);
        }
        final Email modelEmail = new Email(email);

        if (address == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Address.class.getSimpleName()));
        }
        if (!Address.isValidAddress(address)) {
            throw new IllegalValueException(Address.MESSAGE_CONSTRAINTS);
        }
        final Address modelAddress = new Address(address);

        final Set<Tag> modelTags = new HashSet<>(personTags);

        if (role == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Role.class.getSimpleName()));
        }

        if (!Role.isValidRole(role)) {
            throw new IllegalValueException(Role.MESSAGE_CONSTRAINTS);
        }
        final Role modelRole = Role.valueOf(role.toUpperCase());

        if (team == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Team.class.getSimpleName()));
        }
        Team modelTeam = parseTeam(team);

        if (status == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Status.class.getSimpleName()));
        }
        Status modelStatus = parseStatus(status);

        if (position == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Position.class.getSimpleName()));
        }
        Position modelPosition = parsePosition(position);

        if (modelRole == Role.PLAYER) {
            PlayerStats modelPlayerStats = (stats != null)
                    ? stats.toModelType()
                    : new PlayerStats(); // default zeros if stats missing from JSON
            return Person.createPerson(modelName, modelPhone, modelEmail,
                    modelAddress, modelTags, modelRole, modelPlayerStats,
                    modelTeam, modelStatus, modelPosition);
        }

        if (!modelPosition.isDefaultUnassignedPosition()) {
            logger.warning("Auto-normalized non-default staff position to Unassigned Position for: " + modelName);
            modelPosition = new Position(Position.DEFAULT_UNASSIGNED_POSITION);
        }

        return Person.createPerson(modelName, modelPhone, modelEmail,
                modelAddress, modelTags, modelRole, modelTeam, modelStatus, modelPosition);
    }

    private Team parseTeam(String teamValue) throws IllegalValueException {
        String trimmedTeam = teamValue.trim();
        if (!Team.isValidTeamName(trimmedTeam)) {
            throw new IllegalValueException(Team.MESSAGE_CONSTRAINTS);
        }
        return new Team(trimmedTeam);
    }

    private Status parseStatus(String statusValue) throws IllegalValueException {
        String trimmedStatus = statusValue.trim();
        if (!Status.isValidStatusName(trimmedStatus)) {
            throw new IllegalValueException(Status.MESSAGE_CONSTRAINTS);
        }
        return new Status(trimmedStatus);
    }

    private Position parsePosition(String positionValue) throws IllegalValueException {
        String trimmedPosition = positionValue.trim();
        if (!Position.isValidPositionName(trimmedPosition)) {
            throw new IllegalValueException(Position.MESSAGE_CONSTRAINTS);
        }
        return new Position(trimmedPosition);
    }

}
