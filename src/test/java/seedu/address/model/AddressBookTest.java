package seedu.address.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_PLAYER_BEN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.PLAYER_AMY;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.event.Event;
import seedu.address.model.person.Person;
import seedu.address.model.person.Position;
import seedu.address.model.person.Status;
import seedu.address.model.person.Team;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.DuplicatePositionException;
import seedu.address.model.person.exceptions.DuplicateStatusException;
import seedu.address.model.person.exceptions.DuplicateTeamException;
import seedu.address.model.person.exceptions.PositionNotFoundException;
import seedu.address.model.person.exceptions.StatusNotFoundException;
import seedu.address.model.person.exceptions.TeamNotFoundException;
import seedu.address.testutil.PersonBuilder;

public class AddressBookTest {

    private final AddressBook addressBook = new AddressBook();

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), addressBook.getPersonList());
        assertEquals(Collections.emptyList(), addressBook.getEventList());
        assertEquals(Collections.emptyList(), addressBook.getTeamList());
        assertEquals(Collections.emptyList(), addressBook.getPositionList());
        assertEquals(Collections.emptyList(), addressBook.getStatusList());
    }

    @Test
    public void resetData_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.resetData(null));
    }

    @Test
    public void resetData_withValidReadOnlyAddressBook_replacesData() {
        AddressBook newData = getTypicalAddressBook();
        addressBook.resetData(newData);
        assertEquals(newData, addressBook);
    }

    @Test
    public void resetData_withDuplicatePersons_throwsDuplicatePersonException() {
        // Two persons with the same identity fields
        Person editedAmy =
                new PersonBuilder(PLAYER_AMY).withAddress(VALID_ADDRESS_PLAYER_BEN).withTags(VALID_TAG_HUSBAND)
                        .build();
        List<Person> newPersons = Arrays.asList(PLAYER_AMY, editedAmy);
        AddressBookStub newData = new AddressBookStub(newPersons);

        assertThrows(DuplicatePersonException.class, () -> addressBook.resetData(newData));
    }

    @Test
    public void hasPerson_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasPerson(null));
    }

    @Test
    public void hasPerson_personNotInAddressBook_returnsFalse() {
        assertFalse(addressBook.hasPerson(PLAYER_AMY));
    }

    @Test
    public void hasPerson_personInAddressBook_returnsTrue() {
        addressBook.addPerson(PLAYER_AMY);
        assertTrue(addressBook.hasPerson(PLAYER_AMY));
    }

    @Test
    public void hasPerson_personWithSameIdentityFieldsInAddressBook_returnsTrue() {
        addressBook.addPerson(PLAYER_AMY);
        Person editedAmy =
                new PersonBuilder(PLAYER_AMY).withAddress(VALID_ADDRESS_PLAYER_BEN).withTags(VALID_TAG_HUSBAND)
                        .build();
        assertTrue(addressBook.hasPerson(editedAmy));
    }

    @Test
    public void hasTeam_nullTeam_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasTeam(null));
    }

    @Test
    public void addTeam_teamAlreadyExists_throwsDuplicateTeamException() {
        Team team = new Team("First Team");
        addressBook.addTeam(team);
        assertThrows(DuplicateTeamException.class, () -> addressBook.addTeam(new Team("first team")));
    }

    @Test
    public void hasTeam_teamInAddressBook_returnsTrue() {
        Team team = new Team("First Team");
        addressBook.addTeam(team);
        assertTrue(addressBook.hasTeam(new Team("first team")));
    }

    @Test
    public void removeTeam_teamDoesNotExist_throwsTeamNotFoundException() {
        assertThrows(TeamNotFoundException.class, () -> addressBook.removeTeam(new Team("First Team")));
    }

    @Test
    public void removeTeam_existingTeam_removesTeam() {
        Team team = new Team("First Team");
        addressBook.addTeam(team);
        addressBook.removeTeam(team);
        assertFalse(addressBook.hasTeam(team));
    }

    @Test
    public void hasPosition_nullPosition_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasPosition(null));
    }

    @Test
    public void addPosition_positionAlreadyExists_throwsDuplicatePositionException() {
        Position position = new Position("Forward");
        addressBook.addPosition(position);
        assertThrows(DuplicatePositionException.class, () -> addressBook.addPosition(new Position("forward")));
    }

    @Test
    public void hasPosition_positionInAddressBook_returnsTrue() {
        Position position = new Position("Forward");
        addressBook.addPosition(position);
        assertTrue(addressBook.hasPosition(new Position("forward")));
    }

    @Test
    public void removePosition_positionDoesNotExist_throwsPositionNotFoundException() {
        assertThrows(PositionNotFoundException.class, () -> addressBook.removePosition(new Position("Forward")));
    }

    @Test
    public void removePosition_existingPosition_removesPosition() {
        Position position = new Position("Forward");
        addressBook.addPosition(position);
        addressBook.removePosition(position);
        assertFalse(addressBook.hasPosition(position));
    }

    @Test
    public void hasStatus_nullStatus_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> addressBook.hasStatus(null));
    }

    @Test
    public void addStatus_statusAlreadyExists_throwsDuplicateStatusException() {
        Status status = new Status("Unknown");
        addressBook.addStatus(status);
        assertThrows(DuplicateStatusException.class, () -> addressBook.addStatus(new Status("unknown")));
    }

    @Test
    public void hasStatus_statusInAddressBook_returnsTrue() {
        Status status = new Status("Unknown");
        addressBook.addStatus(status);
        assertTrue(addressBook.hasStatus(new Status("unknown")));
    }

    @Test
    public void removeStatus_statusDoesNotExist_throwsStatusNotFoundException() {
        assertThrows(StatusNotFoundException.class, () -> addressBook.removeStatus(new Status("Unknown")));
    }

    @Test
    public void removeStatus_existingStatus_removesStatus() {
        Status status = new Status("Unknown");
        addressBook.addStatus(status);
        addressBook.removeStatus(status);
        assertFalse(addressBook.hasStatus(status));
    }

    @Test
    public void getPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getPersonList().remove(0));
    }

    @Test
    public void getTeamList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getTeamList().remove(0));
    }

    @Test
    public void getPositionList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getPositionList().remove(0));
    }

    @Test
    public void getStatusList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> addressBook.getStatusList().remove(0));
    }

    @Test
    public void toStringMethod() {
        String expected = AddressBook.class.getCanonicalName()
                + "{persons=" + addressBook.getPersonList()
                + ", events=" + addressBook.getEventList()
                + ", teams=" + addressBook.getTeamList()
                + ", positions=" + addressBook.getPositionList()
                + ", statuses=" + addressBook.getStatusList() + "}";
        assertEquals(expected, addressBook.toString());
    }

    /**
     * A stub ReadOnlyAddressBook whose persons list can violate interface constraints.
     */
    private static class AddressBookStub implements ReadOnlyAddressBook {
        private final ObservableList<Person> persons = FXCollections.observableArrayList();
        private final ObservableList<Event> events = FXCollections.observableArrayList();
        private final ObservableList<Team> teams = FXCollections.observableArrayList();
        private final ObservableList<Position> positions = FXCollections.observableArrayList();
        private final ObservableList<Status> statuses = FXCollections.observableArrayList();

        AddressBookStub(Collection<Person> persons) {
            this.persons.setAll(persons);
        }

        @Override
        public ObservableList<Person> getPersonList() {
            return persons;
        }

        public ObservableList<Event> getEventList() {
            return events;
        }

        @Override
        public ObservableList<Team> getTeamList() {
            return teams;
        }

        @Override
        public ObservableList<Position> getPositionList() {
            return positions;
        }

        @Override
        public ObservableList<Status> getStatusList() {
            return statuses;
        }
    }
}
