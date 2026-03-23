package seedu.address.model.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Position;
import seedu.address.model.person.Status;
import seedu.address.model.person.Team;

public class SampleDataUtilTest {

    @Test
    public void getDefaultTeams_expectedDefaultsInOrder() {
        Team[] teams = SampleDataUtil.getDefaultTeams();
        assertEquals(3, teams.length);
        assertEquals(new Team("Unassigned Team"), teams[0]);
        assertEquals(new Team("First Team"), teams[1]);
        assertEquals(new Team("Second Team"), teams[2]);
    }

    @Test
    public void getDefaultPositions_expectedDefaultsInOrder() {
        Position[] positions = SampleDataUtil.getDefaultPositions();
        assertEquals(5, positions.length);
        assertEquals(new Position("Unassigned Position"), positions[0]);
        assertEquals(new Position("Goalkeeper"), positions[1]);
        assertEquals(new Position("Defender"), positions[2]);
        assertEquals(new Position("Midfielder"), positions[3]);
        assertEquals(new Position("Forward"), positions[4]);
    }

    @Test
    public void getDefaultStatuses_expectedDefaultsInOrder() {
        Status[] statuses = SampleDataUtil.getDefaultStatuses();
        assertEquals(3, statuses.length);
        assertEquals(new Status("Unknown"), statuses[0]);
        assertEquals(new Status("Active"), statuses[1]);
        assertEquals(new Status("Unavailable"), statuses[2]);
    }

    @Test
    public void getSampleAddressBook_includesPersonsAndCatalogs() {
        ReadOnlyAddressBook sample = SampleDataUtil.getSampleAddressBook();

        assertTrue(sample.getPersonList().size() > 0);
        assertEquals(3, sample.getTeamList().size());
        assertEquals(5, sample.getPositionList().size());
        assertEquals(3, sample.getStatusList().size());
    }
}

