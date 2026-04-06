package seedu.address.testutil;

import static seedu.address.testutil.TypicalPersons.PLAYER_AMY;
import static seedu.address.testutil.TypicalPersons.PLAYER_BEN;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import seedu.address.model.AddressBook;
import seedu.address.model.event.Event;

/**
 * A utility class containing a list of {@code Event} objects to be used in tests.
 */
public class TypicalEvents {

    public static final Event MATCH = new EventBuilder()
            .withEventName("Barcelona")
            .withEventType("MATCH")
            .withDate("2025-01-02 1500")
            .withPlayers(Set.of(PLAYER_AMY, PLAYER_BEN))
            .build();

    public static final Event TRAINING1 = new EventBuilder()
            .withEventName("Warm Up")
            .withEventType("TRAINING")
            .withDate("2025-01-03 1500")
            .withPlayers(Set.of(PLAYER_AMY, PLAYER_BEN))
            .build();

    public static final Event TRAINING2 = new EventBuilder()
            .withEventName("Shooting Drills")
            .withEventType("TRAINING")
            .withDate("2025-01-03 1500")
            .withPlayers(Set.of(PLAYER_AMY))
            .build();

    private TypicalEvents() {}

    /**
     * Returns an {@code AddressBook} with all the typical events.
     */
    public static AddressBook getTypicalAddressBookWithEvents() {
        AddressBook ab = new AddressBook();
        ab.addPerson(PLAYER_AMY);
        ab.addPerson(PLAYER_BEN);
        for (Event event : getTypicalEvents()) {
            ab.addEvent(event);
        }
        return ab;
    }

    public static List<Event> getTypicalEvents() {
        return new ArrayList<>(Arrays.asList(MATCH, TRAINING1, TRAINING2));
    }
}
