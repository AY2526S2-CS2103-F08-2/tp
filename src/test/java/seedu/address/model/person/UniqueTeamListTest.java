package seedu.address.model.person;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.exceptions.DuplicateTeamException;
import seedu.address.model.person.exceptions.TeamNotFoundException;

public class UniqueTeamListTest {

    private final UniqueTeamList uniqueTeamList = new UniqueTeamList();

    @Test
    public void contains_nullTeam_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> uniqueTeamList.contains(null));
    }

    @Test
    public void contains_teamNotInList_returnsFalse() {
        assertFalse(uniqueTeamList.contains(new Team("First Team")));
    }

    @Test
    public void contains_teamInList_returnsTrue() {
        Team team = new Team("First Team");
        uniqueTeamList.add(team);
        assertTrue(uniqueTeamList.contains(new Team("first team")));
    }

    @Test
    public void add_duplicateTeam_throwsDuplicateTeamException() {
        uniqueTeamList.add(new Team("First Team"));
        assertThrows(DuplicateTeamException.class, () -> uniqueTeamList.add(new Team("first team")));
    }

    @Test
    public void setTeam_teamNotInList_throwsTeamNotFoundException() {
        assertThrows(TeamNotFoundException.class, () -> uniqueTeamList
                .setTeam(new Team("First Team"), new Team("Reserve Team")));
    }

    @Test
    public void setTeam_editedTeamDuplicatesOtherTeam_throwsDuplicateTeamException() {
        uniqueTeamList.add(new Team("First Team"));
        uniqueTeamList.add(new Team("Second Team"));

        assertThrows(DuplicateTeamException.class, () -> uniqueTeamList
                .setTeam(new Team("First Team"), new Team("second team")));
    }

    @Test
    public void setTeam_validRename_success() {
        uniqueTeamList.add(new Team("First Team"));

        uniqueTeamList.setTeam(new Team("First Team"), new Team("Reserve Team"));

        assertFalse(uniqueTeamList.contains(new Team("First Team")));
        assertTrue(uniqueTeamList.contains(new Team("Reserve Team")));
    }

    @Test
    public void remove_teamDoesNotExist_throwsTeamNotFoundException() {
        assertThrows(TeamNotFoundException.class, () -> uniqueTeamList.remove(new Team("First Team")));
    }

    @Test
    public void setTeams_list_replacesOwnListWithProvidedList() {
        uniqueTeamList.add(new Team("First Team"));
        List<Team> teamList = Collections.singletonList(new Team("Second Team"));
        uniqueTeamList.setTeams(teamList);

        UniqueTeamList expected = new UniqueTeamList();
        expected.add(new Team("Second Team"));
        assertEquals(expected, uniqueTeamList);
    }

    @Test
    public void setTeams_listWithDuplicateTeams_throwsDuplicateTeamException() {
        List<Team> duplicateTeams = Arrays.asList(new Team("First Team"), new Team("first team"));
        assertThrows(DuplicateTeamException.class, () -> uniqueTeamList.setTeams(duplicateTeams));
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () ->
                uniqueTeamList.asUnmodifiableObservableList().remove(0));
    }
}

