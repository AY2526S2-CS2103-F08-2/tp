package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
                null,
                null,
                null,
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
                null,
                null,
                null,
                List.of(player.getName().toString())
        );

        assertCommandFailure(command, model, MatchCommand.MESSAGE_DUPLICATE_MATCH);
    }

    @Test
    public void execute_personDoesNotExist_throwsCommandException() {
        Match match = new MatchBuilder().build();

        MatchCommand command = new MatchCommand(
                match.getEventName(),
                match.getEventDate(),
                null,
                null,
                null,
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
                null,
                null,
                null,
                List.of("Staff Person")
        );

        assertCommandFailure(command, model,
                String.format(MatchCommand.MESSAGE_NOT_A_PLAYER, "Staff Person"));
    }

    @Test
    public void execute_statusFilter_addSuccessful() {
        Person matchingPlayer = new PersonBuilder()
                .withName("Matching Player")
                .withRole(Role.PLAYER)
                .withStatus("Active")
                .build();

        Person nonMatchingPlayer = new PersonBuilder()
                .withName("Non Matching Player")
                .withRole(Role.PLAYER)
                .withStatus("Unavailable")
                .build();

        model.addPerson(matchingPlayer);
        model.addPerson(nonMatchingPlayer);

        Match match = new MatchBuilder().build();

        MatchCommand command = new MatchCommand(
                match.getEventName(),
                match.getEventDate(),
                matchingPlayer.getStatus(),
                null,
                null,
                List.of()
        );

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.addPerson(matchingPlayer);
        expectedModel.addPerson(nonMatchingPlayer);

        Set<Person> personSet = new HashSet<Person>();
        personSet.add(matchingPlayer);

        Match expectedMatch = new MatchBuilder()
                .withOpponentName(match.getEventName().toString())
                .withDate(match.getEventDate().getDateWithInputFormat())
                .withPlayers(personSet)
                .build();

        expectedModel.addEvent(expectedMatch);

        String expectedMessage = String.format(
                MatchCommand.MESSAGE_SUCCESS,
                Messages.format(expectedMatch)
        );

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_statusFilterAndNamedPlayer_addSuccessful() {
        Person filteredPlayer = new PersonBuilder()
                .withName("Filtered Player")
                .withRole(Role.PLAYER)
                .withStatus("Active")
                .build();

        Person namedPlayer = new PersonBuilder()
                .withName("Named Player")
                .withRole(Role.PLAYER)
                .withStatus("Unavailable")
                .build();

        model.addPerson(filteredPlayer);
        model.addPerson(namedPlayer);

        Match match = new MatchBuilder().build();

        MatchCommand command = new MatchCommand(
                match.getEventName(),
                match.getEventDate(),
                filteredPlayer.getStatus(),
                null,
                null,
                List.of(namedPlayer.getName().toString())
        );

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.addPerson(filteredPlayer);
        expectedModel.addPerson(namedPlayer);

        Set<Person> personSet = new HashSet<Person>();
        personSet.add(filteredPlayer);
        personSet.add(namedPlayer);

        Match expectedMatch = new MatchBuilder()
                .withOpponentName(match.getEventName().toString())
                .withDate(match.getEventDate().getDateWithInputFormat())
                .withPlayers(personSet)
                .build();

        expectedModel.addEvent(expectedMatch);

        String expectedMessage = String.format(
                MatchCommand.MESSAGE_SUCCESS,
                Messages.format(expectedMatch)
        );

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_statusAndTeamFilter_addSuccessful() {
        Person matchingPlayer = new PersonBuilder()
                .withName("Matching Player")
                .withRole(Role.PLAYER)
                .withStatus("Active")
                .withTeam("First Team")
                .build();

        Person sameStatusOnly = new PersonBuilder()
                .withName("Same Status Only")
                .withRole(Role.PLAYER)
                .withStatus("Active")
                .withTeam("Second Team")
                .build();

        Person sameTeamOnly = new PersonBuilder()
                .withName("Same Team Only")
                .withRole(Role.PLAYER)
                .withStatus("Unavailable")
                .withTeam("First Team")
                .build();

        model.addPerson(matchingPlayer);
        model.addPerson(sameStatusOnly);
        model.addPerson(sameTeamOnly);

        Match match = new MatchBuilder().build();

        MatchCommand command = new MatchCommand(
                match.getEventName(),
                match.getEventDate(),
                matchingPlayer.getStatus(),
                null,
                matchingPlayer.getTeam(),
                List.of()
        );

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.addPerson(matchingPlayer);
        expectedModel.addPerson(sameStatusOnly);
        expectedModel.addPerson(sameTeamOnly);

        Set<Person> personSet = new HashSet<Person>();
        personSet.add(matchingPlayer);

        Match expectedMatch = new MatchBuilder()
                .withOpponentName(match.getEventName().toString())
                .withDate(match.getEventDate().getDateWithInputFormat())
                .withPlayers(personSet)
                .build();

        expectedModel.addEvent(expectedMatch);

        String expectedMessage = String.format(
                MatchCommand.MESSAGE_SUCCESS,
                Messages.format(expectedMatch)
        );

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void equals() {
        Match match = new MatchBuilder().build();

        MatchCommand command = new MatchCommand(
                match.getEventName(),
                match.getEventDate(),
                null,
                null,
                null,
                List.of("Player A")
        );

        MatchCommand sameCommand = new MatchCommand(
                match.getEventName(),
                match.getEventDate(),
                null,
                null,
                null,
                List.of("Player A")
        );

        MatchCommand differentCommand = new MatchCommand(
                match.getEventName(),
                match.getEventDate(),
                null,
                null,
                null,
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
                null,
                null,
                null,
                List.of("Player A", "Player B")
        );

        String expected = MatchCommand.class.getCanonicalName()
                + "{Opponent Name: =" + match.getEventName()
                + ", Date: =" + match.getEventDate()
                + ", Player Names: =" + List.of("Player A", "Player B") + "}";

        assertEquals(expected, command.toString());
    }
}
