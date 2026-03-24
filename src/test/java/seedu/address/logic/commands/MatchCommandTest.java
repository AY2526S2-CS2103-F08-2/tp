package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.match.Match;
import seedu.address.model.person.Person;
import seedu.address.model.person.Role;
import seedu.address.testutil.MatchBuilder;
import seedu.address.testutil.PersonBuilder;

public class MatchCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_matchAcceptedByModel_addSuccessful() {
        // create a new player (avoid duplicates)
        Person player = new PersonBuilder().withRole(Role.PLAYER).build();
        model.addPerson(player);

        Match match = new MatchBuilder().build();

        MatchCommand command = new MatchCommand(
                match.getEventName(),
                match.getEventDate(),
                List.of(player.getName().toString())
        );

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.addPerson(player);
        expectedModel.addEvent(match);

        String expectedMessage = String.format(
                MatchCommand.MESSAGE_SUCCESS,
                Messages.format(match)
        );

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateMatch_throwsCommandException() {
        Person player = new PersonBuilder().withRole(Role.PLAYER).build();
        model.addPerson(player);

        Match match = new MatchBuilder().build();
        model.addEvent(match);

        MatchCommand command = new MatchCommand(
                match.getEventName(),
                match.getEventDate(),
                List.of(player.getName().toString())
        );

        assertCommandFailure(command, model, MatchCommand.MESSAGE_DUPLICATE_MATCH);
    }

    @Test
    public void execute_addDuplicatePlayer_throwsCommandException() {
        Person player = new PersonBuilder().withRole(Role.PLAYER).build();
        model.addPerson(player);

        Match match = new MatchBuilder().build();
        model.addEvent(match);

        MatchCommand command = new MatchCommand(
                match.getEventName(),
                match.getEventDate(),
                List.of(player.getName().toString(), player.getName().toString())
        );

        assertCommandFailure(command, model, MatchCommand.MESSAGE_ADD_DUPLICATE_PLAYER);
    }

    @Test
    public void execute_personDoesNotExist_throwsCommandException() {
        Match match = new MatchBuilder().build();

        MatchCommand command = new MatchCommand(
                match.getEventName(),
                match.getEventDate(),
                List.of("Fake Player")
        );

        assertCommandFailure(command, model,
                String.format(MatchCommand.MESSAGE_PERSON_DOES_NOT_EXIST, "Fake Player"));
    }

    @Test
    public void execute_personIsNotPlayer_throwsCommandException() {
        Person staff = new PersonBuilder().withName("Staff Person").withRole(Role.STAFF).build();
        model.addPerson(staff);

        Match match = new MatchBuilder().build();

        MatchCommand command = new MatchCommand(
                match.getEventName(),
                match.getEventDate(),
                List.of("Staff Person")
        );

        assertCommandFailure(command, model,
                String.format(MatchCommand.MESSAGE_NOT_A_PLAYER, "Staff Person"));
    }

    @Test
    public void equals() {
        Match match = new MatchBuilder().build();

        MatchCommand command = new MatchCommand(
                match.getEventName(),
                match.getEventDate(),
                List.of("Player A")
        );

        MatchCommand sameCommand = new MatchCommand(
                match.getEventName(),
                match.getEventDate(),
                List.of("Player A")
        );

        MatchCommand differentCommand = new MatchCommand(
                match.getEventName(),
                match.getEventDate(),
                List.of("Player B")
        );

        assertTrue(command.equals(sameCommand));
        assertTrue(command.equals(command));
        assertFalse(command.equals(null));
        assertFalse(command.equals(new ClearCommand()));
        assertFalse(command.equals(differentCommand));
    }

    @Test
    public void toStringMethod() {
        Match match = new MatchBuilder().build();

        MatchCommand command = new MatchCommand(
                match.getEventName(),
                match.getEventDate(),
                List.of("Player A", "Player B")
        );

        String expected = MatchCommand.class.getCanonicalName()
                + "{Opponent Name: =" + match.getEventName()
                + ", Date: =" + match.getEventDate()
                + ", Player Names: =" + List.of("Player A", "Player B") + "}";

        assertEquals(expected, command.toString());
    }
}
