package seedu.address.model.training;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import seedu.address.model.event.Date;
import seedu.address.model.event.EventName;
import seedu.address.model.event.EventPlayerList;
import seedu.address.model.event.training.Training;
import seedu.address.model.person.Person;
import seedu.address.model.person.Role;
import seedu.address.testutil.EventBuilder;
import seedu.address.testutil.PersonBuilder;

public class TrainingTest {
    private final Person playerA = new PersonBuilder().withName("Alice").withRole(Role.PLAYER).build();
    private final Person playerB = new PersonBuilder().withName("Bob").withRole(Role.PLAYER).build();

    private final EventPlayerList playerListA = new EventPlayerList(Set.of(playerA));
    private final EventPlayerList playerListB = new EventPlayerList(Set.of(playerB));

    @Test
    public void constructor_nullFields_throwsNullPointerException() {
        EventName name = new EventName("Team A");
        Date date = new Date("2026-04-15 1600");

        assertThrows(NullPointerException.class, () -> new Training(null, date, playerListA));
        assertThrows(NullPointerException.class, () -> new Training(name, null, playerListA));
        assertThrows(NullPointerException.class, () -> new Training(name, date, null));
    }

    @Test
    public void isSameTraining_sameObject_returnsTrue() {
        Training training =
                (Training) new EventBuilder().withEventType("TRAINING").withDate("2026-06-04 1600").withEventName(
                        "Warm Up").build();
        assertTrue(training.isSameEvent(training));
    }

    @Test
    public void isSameTraining_null_returnsFalse() {
        Training training =
                (Training) new EventBuilder().withEventType("TRAINING").withDate("2026-06-04 1600").withEventName(
                        "Warm Up").build();
        assertFalse(training.isSameEvent(null));
    }

    @Test
    public void isSameTraining_sameIdentityFields_returnsTrue() {
        Training training1 =
                (Training) new EventBuilder().withEventType("TRAINING").withDate("2026-06-04 1600").withEventName(
                        "Warm Up").build();

        Training training2 =
                (Training) new EventBuilder().withEventType("TRAINING").withDate("2026-06-04 1600").withEventName(
                        "Warm Up").build();

        assertTrue(training1.isSameEvent(training2));
    }

    @Test
    public void isSameTraining_differentName_returnsFalse() {
        Training training1 =
                (Training) new EventBuilder().withEventType("TRAINING").withDate("2026-06-04 1600").withEventName(
                        "Warm Up").build();

        Training training2 =
                (Training) new EventBuilder().withEventType("TRAINING").withDate("2026-06-04 1600").withEventName(
                        "Cool Down").build();

        assertFalse(training1.isSameEvent(training2));
    }

    @Test
    public void isSameTraining_differentDate_returnsFalse() {
        Training training1 =
                (Training) new EventBuilder().withEventType("TRAINING").withDate("2026-06-04 1600").withEventName(
                        "Warm Up").build();

        Training training2 =
                (Training) new EventBuilder().withEventType("TRAINING").withDate("2026-06-05 1600").withEventName(
                        "Warm Up").build();

        assertFalse(training1.isSameEvent(training2));
    }

    @Test
    public void equals() {
        Training training =
                (Training) new EventBuilder().withEventType("TRAINING").withDate("2026-06-04 1600").withEventName(
                        "Warm Up").build();

        Training sameTraining = (Training) new EventBuilder(training).build();

        Training differentName =
                (Training) new EventBuilder().withEventType("TRAINING").withDate("2026-06-04 1600").withEventName(
                        "Cool Down").build();

        Training differentDate =
                (Training) new EventBuilder().withEventType("TRAINING").withDate("2026-06-05 1600").withEventName(
                        "Warm Up").build();

        Training differentPlayers =
                (Training) new EventBuilder().withEventType("TRAINING").withDate("2026-06-04 1600").withEventName(
                        "Warm Up").withPlayers(playerListB.getPlayerNames().stream()
                        .map((p) -> new PersonBuilder().withRole(Role.PLAYER).withName(p).build()).collect(
                                Collectors.toSet())).build();

        // same values
        assertTrue(training.equals(sameTraining));

        // same object
        assertTrue(training.equals(training));

        // null
        assertFalse(training.equals(null));

        // different type
        assertFalse(training.equals("string"));

        // different fields
        assertFalse(training.equals(differentName));
        assertFalse(training.equals(differentDate));
        assertFalse(training.equals(differentPlayers));
    }

    @Test
    public void hashCode_consistency() {
        Training training1 =
                (Training) new EventBuilder().withEventType("TRAINING").withDate("2026-06-04 1600").withEventName(
                        "Warm Up").build();

        Training training2 =
                (Training) new EventBuilder().withEventType("TRAINING").withDate("2026-06-04 1600").withEventName(
                        "Warm Up").build();

        assertEquals(training1.hashCode(), training2.hashCode());
    }

    @Test
    public void toStringMethod() {
        Training training =
                (Training) new EventBuilder().withEventType("TRAINING").withDate("2026-06-04 1600").withEventName(
                        "Warm Up").build();

        String expected = new seedu.address.commons.util.ToStringBuilder(training)
                .add("training name", training.getEventName())
                .add("training date", training.getEventDate())
                .add("players", training.getEventPlayerList())
                .toString();

        assertEquals(expected, training.toString());
    }
}
