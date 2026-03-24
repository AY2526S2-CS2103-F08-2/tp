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

    private EventName eventName;
    private Date date;
    private EventPlayerList eventPlayerList;

    /**
     * Creates a {@code MatchBuilder} with the default details.
     */
    public MatchBuilder() {
        eventName = new EventName(DEFAULT_OPPONENT_NAME);
        date = new Date(DEFAULT_DATE);
        eventPlayerList = DEFAULT_MATCH_PLAYER_LIST;
    }

    /**
     * Initializes the MatchBuilder with the data of {@code matchToCopy}.
     */
    public MatchBuilder(Match matchToCopy) {
        eventName = matchToCopy.getOpponentName();
        date = matchToCopy.getMatchDate();
        eventPlayerList = matchToCopy.getMatchPlayerList();
    }

    /**
     * Sets the {@code EventName} of the {@code Match} that we are building.
     */
    public MatchBuilder withOpponentName(String name) {
        this.eventName = new EventName(name);
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
        this.eventPlayerList = new EventPlayerList(persons);
        return this;
    }

    /**
     * Builds and returns a {@code Match} with the current attributes.
     */
    public Match build() {
        return new Match(eventName, date, eventPlayerList);
    }
}
