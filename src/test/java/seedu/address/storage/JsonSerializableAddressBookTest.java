package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.JsonUtil;
import seedu.address.model.AddressBook;
import seedu.address.model.person.Person;
import seedu.address.model.person.Position;
import seedu.address.model.person.Role;
import seedu.address.model.person.Status;
import seedu.address.model.person.Team;
import seedu.address.testutil.TypicalPersons;

public class JsonSerializableAddressBookTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonSerializableAddressBookTest");
    private static final Path TYPICAL_PERSONS_FILE = TEST_DATA_FOLDER.resolve("typicalPersonsAddressBook.json");
    private static final Path INVALID_PERSON_FILE = TEST_DATA_FOLDER.resolve("invalidPersonAddressBook.json");
    private static final Path DUPLICATE_PERSON_FILE = TEST_DATA_FOLDER.resolve("duplicatePersonAddressBook.json");
    private static final Path DUPLICATE_TEAM_FILE = TEST_DATA_FOLDER.resolve("duplicateTeamAddressBook.json");
    private static final Path DUPLICATE_POSITION_FILE = TEST_DATA_FOLDER.resolve("duplicatePositionAddressBook.json");
    private static final Path DUPLICATE_STATUS_FILE = TEST_DATA_FOLDER.resolve("duplicateStatusAddressBook.json");
    private static final Path MISSING_DEFAULT_ATTRIBUTES_FILE =
            TEST_DATA_FOLDER.resolve("missingDefaultAttributesAddressBook.json");
    private static final Path MALFORMED_ATTRIBUTE_CATALOG_FILE =
            TEST_DATA_FOLDER.resolve("malformedAttributeCatalogAddressBook.json");
    private static final Path PERSON_ATTRIBUTES_NOT_IN_CATALOG_FILE =
            TEST_DATA_FOLDER.resolve("personAttributesNotInCatalogAddressBook.json");
    private static final Path STAFF_NON_DEFAULT_POSITION_FILE =
            TEST_DATA_FOLDER.resolve("staffWithNonDefaultPositionAddressBook.json");
    private static final Path MALFORMED_AND_VALID_PERSONS_FILE =
            TEST_DATA_FOLDER.resolve("malformedAndValidPersonsAddressBook.json");

    @Test
    public void toModelType_typicalPersonsFile_success() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(TYPICAL_PERSONS_FILE,
                JsonSerializableAddressBook.class).get();
        AddressBook addressBookFromFile = dataFromFile.toModelType();
        AddressBook typicalPersonsAddressBook = TypicalPersons.getTypicalAddressBook();
        assertEquals(addressBookFromFile, typicalPersonsAddressBook);
    }

    @Test
    public void toModelType_invalidPersonFile_malformedPersonIsSkipped() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(INVALID_PERSON_FILE,
                JsonSerializableAddressBook.class).get();
        AddressBook addressBookFromFile = dataFromFile.toModelType();
        assertEquals(0, addressBookFromFile.getPersonList().size());
    }

    @Test
    public void toModelType_malformedAndValidPersons_onlyValidPersonsLoaded() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(MALFORMED_AND_VALID_PERSONS_FILE,
                JsonSerializableAddressBook.class).get();
        AddressBook addressBookFromFile = dataFromFile.toModelType();

        assertEquals(1, addressBookFromFile.getPersonList().size());
        assertEquals("Valid Player", addressBookFromFile.getPersonList().get(0).getName().toString());
    }

    @Test
    public void toModelType_duplicatePersons_throwsIllegalValueException() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_PERSON_FILE,
                JsonSerializableAddressBook.class).get();
        assertThrows(IllegalValueException.class, JsonSerializableAddressBook.MESSAGE_DUPLICATE_PERSON,
                dataFromFile::toModelType);
    }

    @Test
    public void toModelType_duplicateTeams_duplicateEntriesAreIgnored() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_TEAM_FILE,
                JsonSerializableAddressBook.class).get();
        AddressBook addressBookFromFile = dataFromFile.toModelType();

        assertTrue(addressBookFromFile.hasTeam(new Team(Team.DEFAULT_UNASSIGNED_TEAM)));
        assertTrue(addressBookFromFile.hasTeam(new Team("First Team")));
        assertEquals(2, addressBookFromFile.getTeamList().size());
    }

    @Test
    public void toModelType_duplicatePositions_duplicateEntriesAreIgnored() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_POSITION_FILE,
                JsonSerializableAddressBook.class).get();
        AddressBook addressBookFromFile = dataFromFile.toModelType();

        assertTrue(addressBookFromFile.hasPosition(new Position(Position.DEFAULT_UNASSIGNED_POSITION)));
        assertTrue(addressBookFromFile.hasPosition(new Position("Forward")));
        assertEquals(2, addressBookFromFile.getPositionList().size());
    }

    @Test
    public void toModelType_duplicateStatuses_duplicateEntriesAreIgnored() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(DUPLICATE_STATUS_FILE,
                JsonSerializableAddressBook.class).get();
        AddressBook addressBookFromFile = dataFromFile.toModelType();

        assertTrue(addressBookFromFile.hasStatus(new Status(Status.DEFAULT_UNKNOWN_STATUS)));
        assertEquals(1, addressBookFromFile.getStatusList().size());
    }

    @Test
    public void toModelType_missingDefaultAttributes_defaultsAreAutoAdded() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(MISSING_DEFAULT_ATTRIBUTES_FILE,
                JsonSerializableAddressBook.class).get();
        AddressBook addressBookFromFile = dataFromFile.toModelType();

        assertTrue(addressBookFromFile.hasTeam(new Team(Team.DEFAULT_UNASSIGNED_TEAM)));
        assertTrue(addressBookFromFile.hasPosition(new Position(Position.DEFAULT_UNASSIGNED_POSITION)));
        assertTrue(addressBookFromFile.hasStatus(new Status(Status.DEFAULT_UNKNOWN_STATUS)));
        assertEquals(new Team(Team.DEFAULT_UNASSIGNED_TEAM), addressBookFromFile.getTeamList().get(0));
        assertEquals(new Position(Position.DEFAULT_UNASSIGNED_POSITION), addressBookFromFile.getPositionList().get(0));
        assertEquals(new Status(Status.DEFAULT_UNKNOWN_STATUS), addressBookFromFile.getStatusList().get(0));
    }

    @Test
    public void toModelType_malformedAttributeCatalog_recoversBySkippingBadEntries() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(MALFORMED_ATTRIBUTE_CATALOG_FILE,
                JsonSerializableAddressBook.class).get();
        AddressBook addressBookFromFile = dataFromFile.toModelType();

        assertTrue(addressBookFromFile.hasTeam(new Team(Team.DEFAULT_UNASSIGNED_TEAM)));
        assertTrue(addressBookFromFile.hasTeam(new Team("First Team")));
        assertTrue(addressBookFromFile.hasTeam(new Team("Third Team")));
        assertEquals(3, addressBookFromFile.getTeamList().size());

        assertTrue(addressBookFromFile.hasPosition(new Position(Position.DEFAULT_UNASSIGNED_POSITION)));
        assertTrue(addressBookFromFile.hasPosition(new Position("Forward")));
        assertEquals(2, addressBookFromFile.getPositionList().size());

        assertTrue(addressBookFromFile.hasStatus(new Status(Status.DEFAULT_UNKNOWN_STATUS)));
        assertTrue(addressBookFromFile.hasStatus(new Status("Active")));
        assertEquals(2, addressBookFromFile.getStatusList().size());
    }

    @Test
    public void toModelType_personAttributesNotInCatalog_autoRegistersMissingCatalogValues() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(PERSON_ATTRIBUTES_NOT_IN_CATALOG_FILE,
                JsonSerializableAddressBook.class).get();
        AddressBook addressBookFromFile = dataFromFile.toModelType();

        assertTrue(addressBookFromFile.hasTeam(new Team("Third Team")));
        assertTrue(addressBookFromFile.hasPosition(new Position("Sweeper")));
        assertTrue(addressBookFromFile.hasStatus(new Status("Injured")));
        assertEquals(2, addressBookFromFile.getTeamList().size());
        assertEquals(2, addressBookFromFile.getPositionList().size());
        assertEquals(2, addressBookFromFile.getStatusList().size());
    }

    @Test
    public void toModelType_staffWithNonDefaultPosition_normalizesToUnassignedPosition() throws Exception {
        JsonSerializableAddressBook dataFromFile = JsonUtil.readJsonFile(STAFF_NON_DEFAULT_POSITION_FILE,
                JsonSerializableAddressBook.class).get();
        AddressBook addressBookFromFile = dataFromFile.toModelType();

        Person staff = addressBookFromFile.getPersonList().get(0);
        assertEquals(Role.STAFF, staff.getRole());
        assertEquals(new Position(Position.DEFAULT_UNASSIGNED_POSITION), staff.getPosition());
        assertEquals(1, addressBookFromFile.getPositionList().size());
    }

}
