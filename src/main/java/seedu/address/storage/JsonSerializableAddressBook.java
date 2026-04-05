package seedu.address.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;
import seedu.address.model.person.Position;
import seedu.address.model.person.Status;
import seedu.address.model.person.Team;

/**
 * An Immutable AddressBook that is serializable to JSON format.
 */
@JsonRootName(value = "addressbook")
class JsonSerializableAddressBook {
    public static final String MESSAGE_DUPLICATE_PERSON = "Persons list contains duplicate person(s).";
    public static final String MESSAGE_DUPLICATE_EVENT = "Event list contains duplicate event(s).";
    public static final String MESSAGE_DUPLICATE_TEAM = "Teams list contains duplicate team(s).";
    public static final String MESSAGE_DUPLICATE_POSITION = "Positions list contains duplicate position(s).";
    public static final String MESSAGE_DUPLICATE_STATUS = "Statuses list contains duplicate status(es).";
    private static final Logger logger = LogsCenter.getLogger(JsonSerializableAddressBook.class);

    private final List<JsonAdaptedPerson> persons = new ArrayList<>();
    private final List<JsonAdaptedEvent> events = new ArrayList<>();
    private final List<JsonAdaptedTeam> teams = new ArrayList<>();
    private final List<JsonAdaptedPosition> positions = new ArrayList<>();
    private final List<JsonAdaptedStatus> statuses = new ArrayList<>();

    /**
     * Constructs a {@code JsonSerializableAddressBook} with the given data.
     */
    @JsonCreator
    public JsonSerializableAddressBook(@JsonProperty("persons") List<JsonAdaptedPerson> persons,
            @JsonProperty("events") List<JsonAdaptedEvent> events,
            @JsonProperty("teams") List<JsonAdaptedTeam> teams,
            @JsonProperty("positions") List<JsonAdaptedPosition> positions,
            @JsonProperty("statuses") List<JsonAdaptedStatus> statuses) {
        if (persons != null) {
            this.persons.addAll(persons);
        }
        if (events != null) {
            this.events.addAll(events);
        }
        if (teams != null) {
            this.teams.addAll(teams);
        }
        if (positions != null) {
            this.positions.addAll(positions);
        }
        if (statuses != null) {
            this.statuses.addAll(statuses);
        }
    }

    /**
     * Converts a given {@code ReadOnlyAddressBook} into this class for Jackson use.
     *
     * @param source future changes to this will not affect the created {@code JsonSerializableAddressBook}.
     */
    public JsonSerializableAddressBook(ReadOnlyAddressBook source) {
        persons.addAll(source.getPersonList().stream().map(JsonAdaptedPerson::new).collect(Collectors.toList()));
        events.addAll(source.getEventList().stream().map(JsonAdaptedEvent::new).collect(Collectors.toList()));
        teams.addAll(source.getTeamList().stream().map(JsonAdaptedTeam::new).collect(Collectors.toList()));
        positions.addAll(source.getPositionList().stream().map(JsonAdaptedPosition::new).collect(Collectors.toList()));
        statuses.addAll(source.getStatusList().stream().map(JsonAdaptedStatus::new).collect(Collectors.toList()));
    }

    /**
     * Converts this address book into the model's {@code AddressBook} object.
     *
     * @throws IllegalValueException if there were any data constraints violated.
     */
    public AddressBook toModelType() throws IllegalValueException {
        AddressBook addressBook = new AddressBook();
        Map<String, Person> personMap = new HashMap<>();

        for (JsonAdaptedTeam jsonAdaptedTeam : teams) {
            Team team = jsonAdaptedTeam.toModelType();
            if (addressBook.hasTeam(team)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_TEAM);
            }
            addressBook.addTeam(team);
        }

        for (JsonAdaptedPosition jsonAdaptedPosition : positions) {
            Position position = jsonAdaptedPosition.toModelType();
            if (addressBook.hasPosition(position)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_POSITION);
            }
            addressBook.addPosition(position);
        }

        for (JsonAdaptedStatus jsonAdaptedStatus : statuses) {
            Status status = jsonAdaptedStatus.toModelType();
            if (addressBook.hasStatus(status)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_STATUS);
            }
            addressBook.addStatus(status);
        }

        ensureDefaultAttributes(addressBook);

        for (JsonAdaptedPerson jsonAdaptedPerson : persons) {
            Person person = jsonAdaptedPerson.toModelType();
            if (addressBook.hasPerson(person)) {
                throw new IllegalValueException(MESSAGE_DUPLICATE_PERSON);
            }
            addressBook.addPerson(person);
            personMap.put(person.getName().toString(), person);
        }

        for (JsonAdaptedEvent jsonAdaptedEvent : events) {
            Event event = jsonAdaptedEvent.toModelType(personMap);
            if (addressBook.hasEvent(event)) {
                throw new IllegalValueException((MESSAGE_DUPLICATE_EVENT));
            }
            addressBook.addEvent(event);
        }
        return addressBook;
    }

    /**
     * Ensures protected default attribute values always exist, even if source JSON omits them.
     */
    private void ensureDefaultAttributes(AddressBook addressBook) {
        Team defaultTeam = new Team(Team.DEFAULT_UNASSIGNED_TEAM);
        Position defaultPosition = new Position(Position.DEFAULT_UNASSIGNED_POSITION);
        Status defaultStatus = new Status(Status.DEFAULT_UNKNOWN_STATUS);
        boolean hasDefaultTeam = addressBook.hasTeam(defaultTeam);
        boolean hasDefaultPosition = addressBook.hasPosition(defaultPosition);
        boolean hasDefaultStatus = addressBook.hasStatus(defaultStatus);

        List<Team> orderedTeams = new ArrayList<>();
        orderedTeams.add(defaultTeam);
        addressBook.getTeamList().stream()
                .filter(team -> !team.equals(defaultTeam))
                .forEach(orderedTeams::add);
        addressBook.setTeams(orderedTeams);

        List<Position> orderedPositions = new ArrayList<>();
        orderedPositions.add(defaultPosition);
        addressBook.getPositionList().stream()
                .filter(position -> !position.equals(defaultPosition))
                .forEach(orderedPositions::add);
        addressBook.setPositions(orderedPositions);

        List<Status> orderedStatuses = new ArrayList<>();
        orderedStatuses.add(defaultStatus);
        addressBook.getStatusList().stream()
                .filter(status -> !status.equals(defaultStatus))
                .forEach(orderedStatuses::add);
        addressBook.setStatuses(orderedStatuses);

        if (!hasDefaultTeam) {
            logger.warning("Auto-healed missing default team: " + Team.DEFAULT_UNASSIGNED_TEAM);
        }
        if (!hasDefaultPosition) {
            logger.warning("Auto-healed missing default position: " + Position.DEFAULT_UNASSIGNED_POSITION);
        }
        if (!hasDefaultStatus) {
            logger.warning("Auto-healed missing default status: " + Status.DEFAULT_UNKNOWN_STATUS);
        }
    }

}
