package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.FilterCommand;
import seedu.address.model.person.PersonMatchesFilterPredicate;
import seedu.address.model.person.PersonMatchesFilterPredicate.NumericComparison;
import seedu.address.model.person.PersonMatchesFilterPredicate.NumericComparison.Operator;
import seedu.address.model.person.Position;
import seedu.address.model.person.Role;
import seedu.address.model.person.Status;
import seedu.address.model.person.Team;

public class FilterCommandParserTest {

    private final FilterCommandParser parser = new FilterCommandParser();

    @Test
    public void parse_validAttributeAndStatArgs_returnsFilterCommand() {
        PersonMatchesFilterPredicate predicate = new PersonMatchesFilterPredicate(
                Optional.of(Role.PLAYER),
                Optional.of(new Team("First Team")),
                Optional.of(new Status("Active")),
                Optional.of(new Position("Forward")),
                Optional.of(new NumericComparison(Operator.GREATER_THAN, 10)),
                Optional.of(new NumericComparison(Operator.LESS_THAN, 3)),
                Optional.of(new NumericComparison(Operator.EQUALS, 0)));

        assertParseSuccess(parser, " r/player tm/First Team st/Active pos/Forward goals/>10 wins/<3 losses/=0",
                new FilterCommand(predicate));
    }

    @Test
    public void parse_validSingleStatArg_returnsFilterCommand() {
        PersonMatchesFilterPredicate predicate = new PersonMatchesFilterPredicate(
                Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(),
                Optional.of(new NumericComparison(Operator.GREATER_THAN, 5)),
                Optional.empty(), Optional.empty());

        assertParseSuccess(parser, " goals/>5", new FilterCommand(predicate));
    }

    @Test
    public void parse_validSingleAttributeArg_returnsFilterCommand() {
        PersonMatchesFilterPredicate predicate = new PersonMatchesFilterPredicate(
                Optional.empty(), Optional.of(new Team("First Team")), Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty(), Optional.empty());

        assertParseSuccess(parser, " tm/First Team", new FilterCommand(predicate));
    }

    @Test
    public void parse_validStatusArg_returnsFilterCommand() {
        PersonMatchesFilterPredicate predicate = new PersonMatchesFilterPredicate(
                Optional.empty(), Optional.empty(), Optional.of(new Status("Active")), Optional.empty(),
                Optional.empty(), Optional.empty(), Optional.empty());

        assertParseSuccess(parser, " st/Active", new FilterCommand(predicate));
    }

    @Test
    public void parse_validPositionArg_returnsFilterCommand() {
        PersonMatchesFilterPredicate predicate = new PersonMatchesFilterPredicate(
                Optional.empty(), Optional.empty(), Optional.empty(), Optional.of(new Position("Forward")),
                Optional.empty(), Optional.empty(), Optional.empty());

        assertParseSuccess(parser, " pos/Forward", new FilterCommand(predicate));
    }

    @Test
    public void parse_validWinsArg_returnsFilterCommand() {
        PersonMatchesFilterPredicate predicate = new PersonMatchesFilterPredicate(
                Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.of(new NumericComparison(Operator.LESS_THAN, 3)), Optional.empty());

        assertParseSuccess(parser, " wins/<3", new FilterCommand(predicate));
    }

    @Test
    public void parse_validLossesArg_returnsFilterCommand() {
        PersonMatchesFilterPredicate predicate = new PersonMatchesFilterPredicate(
                Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(),
                Optional.empty(), Optional.empty(), Optional.of(new NumericComparison(Operator.EQUALS, 0)));

        assertParseSuccess(parser, " losses/=0", new FilterCommand(predicate));
    }

    @Test
    public void parse_invalidArgs_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "", expectedMessage);
        assertParseFailure(parser, " players", expectedMessage);
        assertParseFailure(parser, " goals/10", expectedMessage);
        assertParseFailure(parser, " goals/>", expectedMessage);
        assertParseFailure(parser, " goals/>-1", expectedMessage);
        assertParseFailure(parser, " goals/>abc", expectedMessage);
        assertParseFailure(parser, " wins/>1 wins/<3",
                Messages.getErrorMessageForDuplicatePrefixes(CliSyntax.PREFIX_WINS));
        assertParseFailure(parser, " maybe/thing", expectedMessage);
    }
}
