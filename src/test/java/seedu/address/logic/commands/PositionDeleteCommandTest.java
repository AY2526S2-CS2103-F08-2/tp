package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.model.util.SampleDataUtil.getSampleAddressBook;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Position;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests for {@link PositionDeleteCommand}.
 */
public class PositionDeleteCommandTest {

    @Test
    // INVALID_CASE + EP_INVALID (null input)
    public void constructor_nullPosition_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new PositionDeleteCommand(null));
    }

    @Test
    // VALID_CASE + EP_VALID
    public void execute_existingPosition_success() {
        Model model = new ModelManager(getSampleAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Position positionToDelete = new Position("Defender");

        expectedModel.deletePosition(positionToDelete);

        assertCommandSuccess(new PositionDeleteCommand(positionToDelete), model,
                String.format(PositionDeleteCommand.MESSAGE_SUCCESS, positionToDelete), expectedModel);
    }

    @Test
    // INVALID_CASE + EP_INVALID (value not found)
    public void execute_missingPosition_throwsCommandException() {
        Model model = new ModelManager(getSampleAddressBook(), new UserPrefs());

        assertCommandFailure(new PositionDeleteCommand(new Position("Ghost Position")),
                model, PositionDeleteCommand.MESSAGE_POSITION_NOT_FOUND);
    }

    @Test
    // INVALID_CASE + DEPENDENCY_RULE (protected default)
    public void execute_defaultPosition_throwsCommandException() {
        Model model = new ModelManager(getSampleAddressBook(), new UserPrefs());

        assertCommandFailure(new PositionDeleteCommand(new Position(Position.DEFAULT_UNASSIGNED_POSITION)),
                model, PositionDeleteCommand.MESSAGE_CANNOT_DELETE_DEFAULT_POSITION);
    }

    @Test
    // INVALID_CASE + DEPENDENCY_RULE (cannot delete in-use value)
    public void execute_positionInUse_throwsCommandException() {
        Model model = new ModelManager(getSampleAddressBook(), new UserPrefs());
        Position inUsePosition = new Position("Winger");
        model.addPosition(inUsePosition);
        model.addPerson(new PersonBuilder().withPosition("Winger").build());

        assertCommandFailure(new PositionDeleteCommand(inUsePosition),
                model, PositionDeleteCommand.MESSAGE_POSITION_IN_USE);
    }

    @Test
    public void equals() {
        PositionDeleteCommand deleteDefender = new PositionDeleteCommand(new Position("Defender"));
        PositionDeleteCommand deleteMidfielder = new PositionDeleteCommand(new Position("Midfielder"));

        assertTrue(deleteDefender.equals(deleteDefender));
        assertTrue(deleteDefender.equals(new PositionDeleteCommand(new Position("defender"))));
        assertFalse(deleteDefender.equals(1));
        assertFalse(deleteDefender.equals(null));
        assertFalse(deleteDefender.equals(deleteMidfielder));
    }

    @Test
    public void toStringMethod() {
        Position position = new Position("Defender");
        PositionDeleteCommand command = new PositionDeleteCommand(position);
        String expected = PositionDeleteCommand.class.getCanonicalName() + "{toDelete=" + position + "}";
        assertEquals(expected, command.toString());
    }
}
