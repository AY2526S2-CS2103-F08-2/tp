package seedu.address.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Team;

public class JsonAdaptedTeamTest {

    private static final String VALID_TEAM = "First Team";
    private static final String INVALID_TEAM = "#Team";

    @Test
    // VALID_CASE + EP_VALID
    public void toModelType_validTeam_returnsTeam() throws Exception {
        JsonAdaptedTeam jsonAdaptedTeam = new JsonAdaptedTeam(VALID_TEAM);
        assertEquals(new Team(VALID_TEAM), jsonAdaptedTeam.toModelType());
    }

    @Test
    // INVALID_CASE + EP_INVALID (invalid token format)
    public void toModelType_invalidTeam_throwsIllegalValueException() {
        JsonAdaptedTeam jsonAdaptedTeam = new JsonAdaptedTeam(INVALID_TEAM);
        assertThrows(IllegalValueException.class, Team.MESSAGE_CONSTRAINTS, jsonAdaptedTeam::toModelType);
    }

    @Test
    // INVALID_CASE + EP_INVALID (null input)
    public void toModelType_nullTeam_throwsIllegalValueException() {
        JsonAdaptedTeam jsonAdaptedTeam = new JsonAdaptedTeam((String) null);
        assertThrows(IllegalValueException.class, Team.MESSAGE_CONSTRAINTS, jsonAdaptedTeam::toModelType);
    }
}
