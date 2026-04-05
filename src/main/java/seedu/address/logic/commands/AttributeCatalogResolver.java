package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.Model;
import seedu.address.model.person.Position;
import seedu.address.model.person.Status;
import seedu.address.model.person.Team;

/**
 * Resolves attribute values to the canonical instances from model catalogs.
 */
final class AttributeCatalogResolver {

    private AttributeCatalogResolver() {}

    static Team resolveTeam(Model model, Team requestedTeam) {
        requireNonNull(model);
        requireNonNull(requestedTeam);
        return model.getTeamList().stream()
                .filter(catalogTeam -> catalogTeam.equals(requestedTeam))
                .findFirst()
                .orElse(requestedTeam);
    }

    static Status resolveStatus(Model model, Status requestedStatus) {
        requireNonNull(model);
        requireNonNull(requestedStatus);
        return model.getStatusList().stream()
                .filter(catalogStatus -> catalogStatus.equals(requestedStatus))
                .findFirst()
                .orElse(requestedStatus);
    }

    static Position resolvePosition(Model model, Position requestedPosition) {
        requireNonNull(model);
        requireNonNull(requestedPosition);
        return model.getPositionList().stream()
                .filter(catalogPosition -> catalogPosition.equals(requestedPosition))
                .findFirst()
                .orElse(requestedPosition);
    }
}
