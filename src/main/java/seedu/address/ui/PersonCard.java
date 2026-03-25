package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.person.Person;
import seedu.address.model.person.Player;
import seedu.address.model.person.PlayerStats;
import seedu.address.model.person.Role;
import seedu.address.model.person.StatField;

/**
 * An UI component that displays information of a {@code Person}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Person person;

    @FXML
    private HBox cardPane;
    @FXML
    private Label name;
    @FXML
    private Label id;
    @FXML
    private Label phone;
    @FXML
    private Label address;
    @FXML
    private Label email;
    @FXML
    private FlowPane tags;
    @FXML
    private FlowPane staff;
    @FXML
    private VBox statsBox;
    @FXML
    private Label statGoals;
    @FXML
    private Label statWins;
    @FXML
    private Label statLosses;

    /**
     * Creates a {@code PersonCode} with the given {@code Person} and index to display.
     */
    public PersonCard(Person person, int displayedIndex) {
        super(FXML);
        this.person = person;
        id.setText(displayedIndex + ". ");
        name.setText(person.getName().fullName);
        phone.setText(person.getPhone().value);
        address.setText(person.getAddress().value);
        email.setText(person.getEmail().value);
        person.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new Label(tag.tagName)));

        Role role = person.getRole();
        if (role != null && role.equals(Role.STAFF)) {
            staff.getChildren().add(new Label("staff"));
        }

        // stats for players
        if (person instanceof Player) {
            Player player = (Player) person;
            PlayerStats stats = player.getStats();

            statGoals.setText("Goals: " + StatField.GOALS.getValue(stats));
            statWins.setText("Wins: " + StatField.WINS.getValue(stats));
            statLosses.setText("Losses: " + StatField.LOSSES.getValue(stats));

            statsBox.setManaged(true);
            statsBox.setVisible(true);
        }
    }
}
