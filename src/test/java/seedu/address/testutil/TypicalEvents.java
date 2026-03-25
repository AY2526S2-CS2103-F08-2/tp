package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import seedu.address.model.AddressBook;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;
import seedu.address.model.person.Role;

/**
 * A utility class containing a list of {@code Event} objects to be used in tests.
 */
public class TypicalEvents {

    public static final Person DEFAULT_PERSON = new PersonBuilder().withRole(Role.PLAYER).build();

    public static final Event MATCH = new EventBuilder()
            .withEventName("Barcelona")
            .withEventType("MATCH")
            .withDate("2025-01-02 1500")
            .withPlayers(Set.of(DEFAULT_PERSON))
            .build();


    private TypicalEvents() {}

    /**
     * Returns an {@code AddressBook} with all the typical events.
     */
    public static AddressBook getTypicalAddressBookWithEvents() {
        AddressBook ab = new AddressBook();
        ab.addPerson(DEFAULT_PERSON);
        for (Event event : getTypicalEvents()) {
            ab.addEvent(event);
        }
        return ab;
    }

    public static List<Event> getTypicalEvents() {
        return new ArrayList<>(Arrays.asList(MATCH));
    }
}
