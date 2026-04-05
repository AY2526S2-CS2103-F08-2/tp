package seedu.address.model.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.event.Date;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventName;
import seedu.address.model.event.EventPlayerList;
import seedu.address.model.event.EventType;
import seedu.address.model.event.match.Match;
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

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Team[] getDefaultTeams() {
        return new Team[] {
            new Team("Unassigned Team"),
            new Team("First Team"),
            new Team("Second Team")
        };
    }

    public static Position[] getDefaultPositions() {
        return new Position[] {
            new Position("Unassigned Position"),
            new Position("Goalkeeper"),
            new Position("Defender"),
            new Position("Midfielder"),
            new Position("Forward")
        };
    }

    public static Status[] getDefaultStatuses() {
        return new Status[] {
            new Status("Unknown"),
            new Status("Active"),
            new Status("Unavailable")
        };
    }

    public static Person[] getSamplePersons() {
        return new Person[]{
                Person.createPerson(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                        new Address("Blk 30 Geylang Street 29, #06-40"),
                        getTagSet("friends"), Role.PLAYER),
                Person.createPerson(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                        new Address("Blk 30 Lorong 3 Serangoon Gardens, #07-18"),
                        getTagSet("colleagues", "friends"), Role.PLAYER),
                Person.createPerson(new Name("Charlotte Oliveiro"), new Phone("93210283"),
                        new Email("charlotte@example.com"),
                        new Address("Blk 11 Ang Mo Kio Street 74, #11-04"),
                        getTagSet("neighbours"), Role.PLAYER),
                Person.createPerson(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                        new Address("Blk 436 Serangoon Gardens Street 26, #16-43"),
                        getTagSet("family"), Role.STAFF),
                Person.createPerson(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                        new Address("Blk 47 Tampines Street 20, #17-35"),
                        getTagSet("classmates"), Role.STAFF),
                Person.createPerson(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                        new Address("Blk 45 Aljunied Street 85, #11-31"),
                        getTagSet("colleagues"), Role.STAFF)
        };
    }

    public static Event[] getSampleEvents() {
        Person[] samplePersons = getSamplePersons();

        Map<String, Integer> firstMatchGoals = new HashMap<>();

        return new Event[]{
                new Match(
                        new EventName("Manchester United"),
                        new Date("2026-04-15 1600"),
                        new EventPlayerList(new HashSet<>(Arrays.asList(
                                samplePersons[0], samplePersons[1], samplePersons[2]
                        ))),
                        firstMatchGoals,
                        0
                ),
                new Match(
                        new EventName("Real Madrid"),
                        new Date("2026-04-20 1220"),
                        new EventPlayerList(new HashSet<>()),
                        new HashMap<>(),
                        0
                ),
                Event.createEvent(
                        new EventName("Dribbling drills"),
                        new Date("2026-02-12 1000"),
                        EventType.TRAINING,
                        new EventPlayerList(new HashSet<>(Arrays.asList(
                                samplePersons[0], samplePersons[1]
                        )))
                ),
                Event.createEvent(
                        new EventName("Defending drills"),
                        new Date("2025-12-04 1800"),
                        EventType.TRAINING,
                        new EventPlayerList(new HashSet<>(Arrays.asList(
                                samplePersons[1], samplePersons[2]
                        )))
                )
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Team defaultTeam : getDefaultTeams()) {
            sampleAb.addTeam(defaultTeam);
        }
        for (Position defaultPosition : getDefaultPositions()) {
            sampleAb.addPosition(defaultPosition);
        }
        for (Status defaultStatus : getDefaultStatuses()) {
            sampleAb.addStatus(defaultStatus);
        }
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        for (Event sampleEvent : getSampleEvents()) {
            sampleAb.addEvent(sampleEvent);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
