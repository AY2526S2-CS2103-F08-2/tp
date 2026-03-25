package seedu.address.testutil;

import java.util.List;

import seedu.address.model.event.Date;
import seedu.address.model.event.EventName;
import seedu.address.model.event.EventPlayerList;
import seedu.address.model.event.match.Match;
import seedu.address.model.person.Person;
import seedu.address.model.person.Role;

/**
 * A utility class to help with building Match objects.
 */
public class MatchBuilder {

    public static final String DEFAULT_OPPONENT_NAME = "Manchester City";
    public static final String DEFAULT_DATE = "2026-05-15 1600";
    public static final Person DEFAULT_PERSON = new PersonBuilder().withRole(Role.PLAYER).build();
    public static final EventPlayerList DEFAULT_MATCH_PLAYER_LIST = new EventPlayerList(List.of(DEFAULT_PERSON));

    private EventName opponentName;
    private Date date;
    private EventPlayerList matchPlayerList;

    /**
     * Creates a {@code MatchBuilder} with the default details.
     */
    public MatchBuilder() {
        opponentName = new EventName(DEFAULT_OPPONENT_NAME);
        date = new Date(DEFAULT_DATE);
        matchPlayerList = DEFAULT_MATCH_PLAYER_LIST;
    }

    /**
     * Initializes the MatchBuilder with the data of {@code matchToCopy}.
     */
    public MatchBuilder(Match matchToCopy) {
        opponentName = matchToCopy.getEventName();
        date = matchToCopy.getEventDate();
        matchPlayerList = matchToCopy.getEventPlayerList();
    }

    /**
     * Sets the {@code EventName} of the {@code Match} that we are building.
     */
    public MatchBuilder withOpponentName(String name) {
        this.opponentName = new EventName(name);
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
     * Sets the {@code EventPlayerList} of the {@code Match} that we are building.
     */
    public MatchBuilder withPlayers(List<Person> persons) {
        this.matchPlayerList = new EventPlayerList(persons);
        return this;
    }

    /**
     * Builds and returns a {@code Match} with the current attributes.
     */
    public Match build() {
        return new Match(opponentName, date, matchPlayerList);
    }
}
