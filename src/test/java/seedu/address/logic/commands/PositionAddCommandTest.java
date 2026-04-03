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

/**
 * Contains integration tests for {@link PositionAddCommand}.
 */
public class PositionAddCommandTest {

    @Test
    // INVALID_CASE + EP_INVALID (null input)
    public void constructor_nullPosition_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new PositionAddCommand(null));
    }

    @Test
    // VALID_CASE + EP_VALID
    public void execute_newPosition_success() {
        Model model = new ModelManager(getSampleAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Position positionToAdd = new Position("Winger");

        expectedModel.addPosition(positionToAdd);

        assertCommandSuccess(new PositionAddCommand(positionToAdd), model,
                String.format(PositionAddCommand.MESSAGE_SUCCESS, positionToAdd), expectedModel);
    }

    @Test
    // INVALID_CASE + EP_INVALID (duplicate catalog entry)
    public void execute_duplicatePosition_throwsCommandException() {
        Model model = new ModelManager(getSampleAddressBook(), new UserPrefs());
        Position duplicatePosition = new Position("Forward");

        assertCommandFailure(new PositionAddCommand(duplicatePosition), model,
                PositionAddCommand.MESSAGE_DUPLICATE_POSITION);
    }

    @Test
    public void equals() {
        PositionAddCommand addForward = new PositionAddCommand(new Position("Forward"));
        PositionAddCommand addWinger = new PositionAddCommand(new Position("Winger"));

        assertTrue(addForward.equals(addForward));
        assertTrue(addForward.equals(new PositionAddCommand(new Position("forward"))));
        assertFalse(addForward.equals(1));
        assertFalse(addForward.equals(null));
        assertFalse(addForward.equals(addWinger));
    }

    @Test
    public void toStringMethod() {
        Position position = new Position("Winger");
        PositionAddCommand command = new PositionAddCommand(position);
        String expected = PositionAddCommand.class.getCanonicalName() + "{toAdd=" + position + "}";
        assertEquals(expected, command.toString());
    }
}
