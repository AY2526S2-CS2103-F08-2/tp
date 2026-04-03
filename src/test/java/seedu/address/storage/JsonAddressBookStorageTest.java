package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.PLAYER_AMY;
import static seedu.address.testutil.TypicalPersons.PLAYER_TYRONE;
import static seedu.address.testutil.TypicalPersons.STAFF_JEFFREY;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.exceptions.DataLoadingException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Position;
import seedu.address.model.person.Status;
import seedu.address.model.person.Team;

public class JsonAddressBookStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "JsonAddressBookStorageTest");

    @TempDir
    public Path testFolder;

    @Test
    public void readAddressBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> readAddressBook(null));
    }

    private java.util.Optional<ReadOnlyAddressBook> readAddressBook(String filePath) throws Exception {
        return new JsonAddressBookStorage(Paths.get(filePath)).readAddressBook(addToTestDataPathIfNotNull(filePath));
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readAddressBook("NonExistentFile.json").isPresent());
    }

    @Test
    public void read_notJsonFormat_exceptionThrown() {
        assertThrows(DataLoadingException.class, () -> readAddressBook("notJsonFormatAddressBook.json"));
    }

    @Test
    public void readAddressBook_invalidPersonAddressBook_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readAddressBook("invalidPersonAddressBook.json"));
    }

    @Test
    public void readAddressBook_invalidAndValidPersonAddressBook_throwDataLoadingException() {
        assertThrows(DataLoadingException.class, () -> readAddressBook("invalidAndValidPersonAddressBook.json"));
    }

    @Test
    public void readAddressBook_malformedAttributeCatalogAddressBook_loadsSuccessfully() throws Exception {
        ReadOnlyAddressBook readOnlyAddressBook = readAddressBook("malformedAttributeCatalogAddressBook.json").get();
        AddressBook loadedAddressBook = new AddressBook(readOnlyAddressBook);

        assertTrue(loadedAddressBook.hasTeam(new Team(Team.DEFAULT_UNASSIGNED_TEAM)));
        assertTrue(loadedAddressBook.hasTeam(new Team("First Team")));
        assertTrue(loadedAddressBook.hasTeam(new Team("Third Team")));
        assertEquals(3, loadedAddressBook.getTeamList().size());

        assertTrue(loadedAddressBook.hasPosition(new Position(Position.DEFAULT_UNASSIGNED_POSITION)));
        assertTrue(loadedAddressBook.hasPosition(new Position("Forward")));
        assertEquals(2, loadedAddressBook.getPositionList().size());

        assertTrue(loadedAddressBook.hasStatus(new Status(Status.DEFAULT_UNKNOWN_STATUS)));
        assertTrue(loadedAddressBook.hasStatus(new Status("Active")));
        assertEquals(2, loadedAddressBook.getStatusList().size());
    }

    @Test
    public void readAddressBook_personAttributesNotInCatalog_autoRegistersMissingCatalogValues() throws Exception {
        ReadOnlyAddressBook readOnlyAddressBook = readAddressBook("personAttributesNotInCatalogAddressBook.json").get();
        AddressBook loadedAddressBook = new AddressBook(readOnlyAddressBook);

        assertTrue(loadedAddressBook.hasTeam(new Team("Third Team")));
        assertTrue(loadedAddressBook.hasPosition(new Position("Sweeper")));
        assertTrue(loadedAddressBook.hasStatus(new Status("Injured")));
        assertEquals(2, loadedAddressBook.getTeamList().size());
        assertEquals(2, loadedAddressBook.getPositionList().size());
        assertEquals(2, loadedAddressBook.getStatusList().size());
    }

    @Test
    public void readAndSaveAddressBook_allInOrder_success() throws Exception {
        Path filePath = testFolder.resolve("TempAddressBook.json");
        AddressBook original = getTypicalAddressBook();
        JsonAddressBookStorage jsonAddressBookStorage = new JsonAddressBookStorage(filePath);

        // Save in new file and read back
        jsonAddressBookStorage.saveAddressBook(original, filePath);
        ReadOnlyAddressBook readBack = jsonAddressBookStorage.readAddressBook(filePath).get();
        assertEquals(original, new AddressBook(readBack));

        // Modify data, overwrite exiting file, and read back
        original.addPerson(STAFF_JEFFREY);
        original.removePerson(PLAYER_AMY);
        jsonAddressBookStorage.saveAddressBook(original, filePath);
        readBack = jsonAddressBookStorage.readAddressBook(filePath).get();
        assertEquals(original, new AddressBook(readBack));

        // Save and read without specifying file path
        original.addPerson(PLAYER_TYRONE);
        jsonAddressBookStorage.saveAddressBook(original); // file path not specified
        readBack = jsonAddressBookStorage.readAddressBook().get(); // file path not specified
        assertEquals(original, new AddressBook(readBack));

    }

    @Test
    public void saveAddressBook_nullAddressBook_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveAddressBook(null, "SomeFile.json"));
    }

    /**
     * Saves {@code addressBook} at the specified {@code filePath}.
     */
    private void saveAddressBook(ReadOnlyAddressBook addressBook, String filePath) {
        try {
            new JsonAddressBookStorage(Paths.get(filePath))
                    .saveAddressBook(addressBook, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveAddressBook_nullFilePath_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> saveAddressBook(new AddressBook(), null));
    }
}
