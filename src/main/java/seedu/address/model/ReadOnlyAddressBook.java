package seedu.address.model;

import javafx.collections.ObservableList;
import seedu.address.model.match.Match;
import seedu.address.model.person.Person;
import seedu.address.model.person.Position;
import seedu.address.model.person.Status;
import seedu.address.model.person.Team;

/**
 * Unmodifiable view of an address book
 */
public interface ReadOnlyAddressBook {

    /**
     * Returns an unmodifiable view of the persons list.
     * This list will not contain any duplicate persons.
     */
    ObservableList<Person> getPersonList();

    /**
     * Returns an unmodifiable view of the matches list.
     * This list will not contain any duplicate matches.
     */
    ObservableList<Match> getMatchList();

    /**
     * Returns an unmodifiable view of the teams catalog.
     */
    ObservableList<Team> getTeamList();

    /**
     * Returns an unmodifiable view of the positions catalog.
     */
    ObservableList<Position> getPositionList();

    /**
     * Returns an unmodifiable view of the statuses catalog.
     */
    ObservableList<Status> getStatusList();

}
