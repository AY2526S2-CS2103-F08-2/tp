package seedu.address.testutil;

import java.util.List;

import seedu.address.model.match.Date;
import seedu.address.model.match.Match;
import seedu.address.model.match.MatchPlayerList;
import seedu.address.model.match.OpponentName;
import seedu.address.model.person.Person;
import seedu.address.model.person.Role;

/**
 * A utility class to help with building Match objects.
 */
public class MatchBuilder {

    public static final String DEFAULT_OPPONENT_NAME = "Manchester City";
    public static final String DEFAULT_DATE = "2026-05-15 1600";
    public static final Person DEFAULT_PERSON = new PersonBuilder().withRole(Role.PLAYER).build();
    public static final MatchPlayerList DEFAULT_MATCH_PLAYER_LIST = new MatchPlayerList(List.of(DEFAULT_PERSON));

    private OpponentName opponentName;
    private Date date;
    private MatchPlayerList matchPlayerList;

    /**
     * Creates a {@code MatchBuilder} with the default details.
     */
    public MatchBuilder() {
        opponentName = new OpponentName(DEFAULT_OPPONENT_NAME);
        date = new Date(DEFAULT_DATE);
        matchPlayerList = DEFAULT_MATCH_PLAYER_LIST;
    }

    /**
     * Initializes the MatchBuilder with the data of {@code matchToCopy}.
     */
    public MatchBuilder(Match matchToCopy) {
        opponentName = matchToCopy.getOpponentName();
        date = matchToCopy.getMatchDate();
        matchPlayerList = matchToCopy.getMatchPlayerList();
    }

    /**
     * Sets the {@code OpponentName} of the {@code Match} that we are building.
     */
    public MatchBuilder withOpponentName(String name) {
        this.opponentName = new OpponentName(name);
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code Match} that we are building.
     */
    public MatchBuilder withDate(String date) {
        this.date = new Date(date);
        return this;
    }

    /**
     * Sets the {@code MatchPlayerList} of the {@code Match} that we are building.
     */
    public MatchBuilder withPlayers(List<Person> persons) {
        this.matchPlayerList = new MatchPlayerList(persons);
        return this;
    }

    /**
     * Builds and returns a {@code Match} with the current attributes.
     */
    public Match build() {
        return new Match(opponentName, date, matchPlayerList);
    }
}
