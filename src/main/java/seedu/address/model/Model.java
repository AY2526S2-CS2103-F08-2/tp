package seedu.address.model;

import java.nio.file.Path;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.match.Match;
import seedu.address.model.person.Person;
import seedu.address.model.person.Team;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Person> PREDICATE_SHOW_ALL_PERSONS = unused -> true;

    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a person with the same identity as {@code person} exists in the address book.
     */
    boolean hasPerson(Person person);

    /**
     * Deletes the given person.
     * The person must exist in the address book.
     */
    void deletePerson(Person target);

    /**
     * Adds the given person.
     * {@code person} must not already exist in the address book.
     */
    void addPerson(Person person);

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     * {@code target} must exist in the address book.
     * The person identity of {@code editedPerson} must not be the same as another existing person in the address book.
     */
    void setPerson(Person target, Person editedPerson);

    /**
     * Returns true if a team with the same identity as {@code team} exists in the team catalog.
     */
    boolean hasTeam(Team team);

    /**
     * Adds the given team to the team catalog.
     * {@code team} must not already exist in the team catalog.
     */
    void addTeam(Team team);

    /**
     * Deletes the given team from the team catalog.
     * {@code team} must exist in the team catalog.
     */
    void deleteTeam(Team team);

    /**
     * Replaces {@code oldTeam} with {@code newTeam} in the team catalog.
     */
    void setTeam(Team oldTeam, Team newTeam);

    /**
     * Returns true if a match with the same identity as {@code match} exists in the address book.
     */
    boolean hasMatch(Match match);

    /**
     * Deletes the given match.
     * The match must exist in the address book.
     */
    void deleteMatch(Match target);

    /**
     * Adds the given match.
     * {@code match} must not already exist in the address book.
     */
    void addMatch(Match match);

    /**
     * Replaces the given match {@code target} with {@code editedMatch}.
     * {@code target} must exist in the address book.
     * The match identity of {@code editedMatch} must not be the same as another existing match in the address book.
     */
    void setMatch(Match target, Match editedMatch);


    /** Returns an unmodifiable view of the filtered person list */
    ObservableList<Person> getFilteredPersonList();

    /** Returns an unmodifiable view of the team catalog list. */
    ObservableList<Team> getTeamList();

    /** Returns an unmodifiable view of the filtered match list */
    ObservableList<Match> getMatchList();

    /**
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredPersonList(Predicate<Person> predicate);
}
