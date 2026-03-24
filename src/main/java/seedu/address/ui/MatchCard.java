package seedu.address.ui;

import java.util.Comparator;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.match.Match;

/**
 * An UI component that displays information of a {@code Match}.
 */
public class MatchCard extends UiPart<Region> {

    private static final String FXML = "MatchListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Match match;

    @FXML
    private Label id;
    @FXML
    private HBox cardPane;
    @FXML
    private Label opponentName;
    @FXML
    private Label date;
    @FXML
    private Label playerList;

    /**
     * Creates a {@code MatchCard} with the given {@code Match} and index to display.
     */
    public MatchCard(Match match, int displayedIndex) {
        super(FXML);
        this.match = match;
        id.setText(displayedIndex + ". ");
        date.setText("Match Date: " + match.getMatchDate().toString());
        opponentName.setText("vs " + match.getOpponentName().toString());

        String players = match.getMatchPlayerList()
                .asUnmodifiableObservableList()
                .stream()
                .sorted(Comparator.comparing(person -> person.getName().fullName))
                .map(person -> person.getName().fullName)
                .collect(Collectors.joining(", "));

        playerList.setText("Players: " + players);
    }
}
