package seedu.address.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_PLAYER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_PLAYER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_PLAYER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_PLAYER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ROLE_DESC_PLAYER;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalPersons.PLAYER_AMY;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.PositionAddCommand;
import seedu.address.logic.commands.PositionListCommand;
import seedu.address.logic.commands.StatusAddCommand;
import seedu.address.logic.commands.TeamAddCommand;
import seedu.address.logic.commands.TeamListCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Position;
import seedu.address.model.person.Status;
import seedu.address.model.person.Team;
import seedu.address.storage.JsonAddressBookStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.StorageManager;
import seedu.address.testutil.PersonBuilder;

public class LogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy IO exception");
    private static final IOException DUMMY_AD_EXCEPTION = new AccessDeniedException("dummy access denied exception");

    @TempDir
    public Path temporaryFolder;

    private Model model = new ModelManager();
    private Logic logic;

    @BeforeEach
    public void setUp() {
        JsonAddressBookStorage addressBookStorage =
                new JsonAddressBookStorage(temporaryFolder.resolve("addressBook.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        StorageManager storage = new StorageManager(addressBookStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);
    }

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_commandExecutionError_throwsCommandException() {
        String deleteCommand = "delete 9";
        assertCommandException(deleteCommand, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validCommand_success() throws Exception {
        String listCommand = ListCommand.COMMAND_WORD;
        assertCommandSuccess(listCommand, ListCommand.MESSAGE_SUCCESS, model);
    }

    @Test
    public void execute_teamList_success() throws Exception {
        String teamListCommand = TeamListCommand.COMMAND_WORD;
        assertCommandSuccess(teamListCommand, TeamListCommand.MESSAGE_EMPTY, model);
    }

    @Test
    public void execute_positionList_success() throws Exception {
        String positionListCommand = PositionListCommand.COMMAND_WORD;
        assertCommandSuccess(positionListCommand, PositionListCommand.MESSAGE_EMPTY, model);
    }

    @Test
    public void execute_teamAdd_success() throws Exception {
        String teamAddCommand = TeamAddCommand.COMMAND_WORD + " Reserve Team";
        assertCommandSuccess(teamAddCommand, "New team added: Reserve Team", model);
    }

    @Test
    public void execute_statusAdd_success() throws Exception {
        String statusAddCommand = StatusAddCommand.COMMAND_WORD + " Rehab";
        assertCommandSuccess(statusAddCommand, "New status added: Rehab", model);
    }

    @Test
    public void execute_positionAdd_success() throws Exception {
        String positionAddCommand = PositionAddCommand.COMMAND_WORD + " Winger";
        assertCommandSuccess(positionAddCommand, "New position added: Winger", model);
    }

    @Test
    public void execute_storageThrowsIoException_throwsCommandException() {
        assertCommandFailureForExceptionFromStorage(DUMMY_IO_EXCEPTION, String.format(
                LogicManager.FILE_OPS_ERROR_FORMAT, DUMMY_IO_EXCEPTION.getMessage()));
    }

    @Test
    public void execute_storageThrowsAdException_throwsCommandException() {
        assertCommandFailureForExceptionFromStorage(DUMMY_AD_EXCEPTION, String.format(
                LogicManager.FILE_OPS_PERMISSION_ERROR_FORMAT, DUMMY_AD_EXCEPTION.getMessage()));
    }

    @Test
    public void getFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredPersonList().remove(0));
    }

    @Test
    public void importCsv_validCsv_returnsSummaryMessage() throws Exception {
        Path csvFile = createCsvFile("validImport.csv",
                "name,role,address,phone,email,tags",
                "Alex Tan,player,12 Clementi Road,91234567,alex.tan@example.com,captain;striker");
        String result = logic.importCsv(csvFile);

        assertTrue(result.contains("Rows processed: 1"));
        assertTrue(result.contains("Successfully imported: 1"));
        assertTrue(result.contains("Failed: 0"));
        assertEquals(1, model.getFilteredPersonList().size());
    }

    @Test
    public void importCsv_invalidHeader_throwsCommandException() throws Exception {
        Path csvFile = createCsvFile("invalidHeader.csv",
                "name,address,phone,email,tags",
                "Alex Tan,12 Clementi Road,91234567,alex.tan@example.com,captain");

        assertThrows(CommandException.class, () -> logic.importCsv(csvFile));
    }

    @Test
    public void importCsv_nonexistentFile_throwsCommandException() {
        Path csvFile = Path.of("this-file-should-not-exist-12345.csv");

        assertThrows(CommandException.class, () -> logic.importCsv(csvFile));
    }

    @Test
    public void importCsv_mixedRows_returnsPartialSummary() throws Exception {
        Path csvFile = createCsvFile("mixedImport.csv",
                "name,role,address,phone,email,tags",
                "Alex Tan,player,12 Clementi Road,91234567,alex.tan@example.com,captain;striker",
                "Bad Role,coach,45 Jurong West Ave 3,92345678,bad.role@example.com,manager",
                "Bad Email,staff,22 Tampines Street 11,95678901,not-an-email,physio",
                "Grace Koh,staff,14 Hougang Ave 8,97890123,grace.koh@example.com,analyst");

        String result = logic.importCsv(csvFile);

        assertTrue(result.contains("Rows processed: 4"));
        assertTrue(result.contains("Successfully imported: 2"));
        assertTrue(result.contains("Failed: 2"));
        assertEquals(2, model.getFilteredPersonList().size());
    }

    private Path createCsvFile(String fileName, String... lines) throws IOException {
        Path file = temporaryFolder.resolve(fileName);
        Files.write(file, List.of(lines));
        return file;
    }

    /**
     * Executes the command and confirms that
     * - no exceptions are thrown <br>
     * - the feedback message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage,
                                      Model expectedModel) throws CommandException, ParseException {
        CommandResult result = logic.execute(inputCommand);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(expectedModel, model);
    }

    /**
     * Executes the command, confirms that a ParseException is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertParseException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, ParseException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that a CommandException is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, CommandException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that the exception is thrown and that the result message is correct.
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
                                      String expectedMessage) {
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        assertCommandFailure(inputCommand, expectedException, expectedMessage, expectedModel);
    }

    /**
     * Executes the command and confirms that
     * - the {@code expectedException} is thrown <br>
     * - the resulting error message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     * @see #assertCommandSuccess(String, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
                                      String expectedMessage, Model expectedModel) {
        assertThrows(expectedException, expectedMessage, () -> logic.execute(inputCommand));
        assertEquals(expectedModel, model);
    }

    /**
     * Tests the Logic component's handling of an {@code IOException} thrown by the Storage component.
     *
     * @param e the exception to be thrown by the Storage component
     * @param expectedMessage the message expected inside exception thrown by the Logic component
     */
    private void assertCommandFailureForExceptionFromStorage(IOException e, String expectedMessage) {
        model.addTeam(new Team(Team.DEFAULT_UNASSIGNED_TEAM));
        model.addStatus(new Status(Status.DEFAULT_UNKNOWN_STATUS));
        model.addPosition(new Position(Position.DEFAULT_UNASSIGNED_POSITION));

        Path prefPath = temporaryFolder.resolve("ExceptionUserPrefs.json");

        // Inject LogicManager with an AddressBookStorage that throws the IOException e when saving
        JsonAddressBookStorage addressBookStorage = new JsonAddressBookStorage(prefPath) {
            @Override
            public void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath)
                    throws IOException {
                throw e;
            }
        };

        JsonUserPrefsStorage userPrefsStorage =
                new JsonUserPrefsStorage(temporaryFolder.resolve("ExceptionUserPrefs.json"));
        StorageManager storage = new StorageManager(addressBookStorage, userPrefsStorage);

        logic = new LogicManager(model, storage);

        // Triggers the saveAddressBook method by executing an add command
        String addCommand = AddCommand.COMMAND_WORD + NAME_DESC_PLAYER_AMY + PHONE_DESC_PLAYER_AMY
                + EMAIL_DESC_PLAYER_AMY + ADDRESS_DESC_PLAYER_AMY + ROLE_DESC_PLAYER;
        Person expectedPerson = new PersonBuilder(PLAYER_AMY).withTags().build();
        ModelManager expectedModel = new ModelManager();
        expectedModel.addTeam(new Team(Team.DEFAULT_UNASSIGNED_TEAM));
        expectedModel.addStatus(new Status(Status.DEFAULT_UNKNOWN_STATUS));
        expectedModel.addPosition(new Position(Position.DEFAULT_UNASSIGNED_POSITION));
        expectedModel.addPerson(expectedPerson);
        assertCommandFailure(addCommand, CommandException.class, expectedMessage, expectedModel);
    }
}
