package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.model.util.SampleDataUtil.getSampleAddressBook;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Position;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests for {@link PositionEditCommand}.
 */
public class PositionEditCommandTest {

    @Test
    public void constructor_nullOldPosition_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new PositionEditCommand(null, new Position("Winger")));
    }

    @Test
    public void constructor_nullNewPosition_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new PositionEditCommand(new Position("Forward"), null));
    }

    @Test
    public void execute_validRename_success() {
        Model model = new ModelManager(getSampleAddressBook(), new UserPrefs());
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        Position oldPosition = new Position("Defender");
        Position newPosition = new Position("Center Back");
        expectedModel.setPosition(oldPosition, newPosition);

        assertCommandSuccess(new PositionEditCommand(oldPosition, newPosition), model,
                String.format(PositionEditCommand.MESSAGE_SUCCESS, oldPosition, newPosition), expectedModel);
    }

    @Test
    public void execute_oldPositionNotFound_throwsCommandException() {
        Model model = new ModelManager(getSampleAddressBook(), new UserPrefs());

        assertCommandFailure(new PositionEditCommand(new Position("Ghost Position"), new Position("Winger")),
                model, PositionEditCommand.MESSAGE_POSITION_NOT_FOUND);
    }

    @Test
    public void execute_newPositionAlreadyExists_throwsCommandException() {
        Model model = new ModelManager(getSampleAddressBook(), new UserPrefs());

        assertCommandFailure(new PositionEditCommand(new Position("Defender"), new Position("Forward")),
                model, PositionEditCommand.MESSAGE_DUPLICATE_POSITION);
    }

    @Test
    public void execute_defaultPosition_throwsCommandException() {
        Model model = new ModelManager(getSampleAddressBook(), new UserPrefs());

        assertCommandFailure(
                new PositionEditCommand(new Position(Position.DEFAULT_UNASSIGNED_POSITION), new Position("Winger")),
                model, PositionEditCommand.MESSAGE_CANNOT_EDIT_DEFAULT_POSITION);
    }

    @Test
    public void execute_validRename_updatesPersonsWithOldPosition() throws CommandException {
        Model model = new ModelManager(getSampleAddressBook(), new UserPrefs());
        model.addPerson(new PersonBuilder().withPosition("Defender").build());

        Position oldPosition = new Position("Defender");
        Position newPosition = new Position("Center Back");
        new PositionEditCommand(oldPosition, newPosition).execute(model);

        assertTrue(model.getPersonsMatching(person -> person.getPosition().equals(oldPosition)).isEmpty());
        assertFalse(model.getPersonsMatching(person -> person.getPosition().equals(newPosition)).isEmpty());
    }

    @Test
    public void equals() {
        PositionEditCommand editDefenderToCenterBack =
                new PositionEditCommand(new Position("Defender"), new Position("Center Back"));
        PositionEditCommand editForwardToCenterBack =
                new PositionEditCommand(new Position("Forward"), new Position("Center Back"));

        assertTrue(editDefenderToCenterBack.equals(editDefenderToCenterBack));
        assertTrue(editDefenderToCenterBack.equals(
                new PositionEditCommand(new Position("defender"), new Position("Center Back"))));
        assertFalse(editDefenderToCenterBack.equals(1));
        assertFalse(editDefenderToCenterBack.equals(null));
        assertFalse(editDefenderToCenterBack.equals(editForwardToCenterBack));
    }

    @Test
    public void toStringMethod() {
        PositionEditCommand command = new PositionEditCommand(new Position("Defender"), new Position("Center Back"));
        String expected = PositionEditCommand.class.getCanonicalName()
                + "{oldPosition=Defender, newPosition=Center Back}";
        assertEquals(expected, command.toString());
    }
}
