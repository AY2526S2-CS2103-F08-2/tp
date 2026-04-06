package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import seedu.address.model.person.CalculatedStatField;
import seedu.address.model.person.Person;
import seedu.address.model.person.Player;
import seedu.address.model.person.PlayerStats;
import seedu.address.model.person.Position;
import seedu.address.model.person.Role;
import seedu.address.model.person.StatField;
import seedu.address.model.person.Status;
import seedu.address.model.person.Team;

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
    private Label team;
    @FXML
    private Label position;
    @FXML
    private Label status;
    @FXML
    private FlowPane tags;
    @FXML
    private FlowPane staff;
    @FXML
    private VBox statsBox;
    @FXML
    private Separator statsSeparatorTop;
    @FXML
    private Separator statsSeparatorBottom;

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
        setAttributeLabel(team, "Team", person.getTeam(), person.getTeam().isDefaultUnassignedTeam());
        setAttributeLabel(position, "Position", person.getPosition(),
                person.getPosition().isDefaultUnassignedPosition());
        setAttributeLabel(status, "Status", person.getStatus(), person.getStatus().isDefaultUnknownStatus());
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

            for (StatField stat : StatField.values()) {
                String name = stat.name().replaceAll("_", " ");
                Label label = new Label(name + ": " + stat.getValue(stats));
                label.getStyleClass().add("stat-label");
                statsBox.getChildren().add(label);
            }
            for (CalculatedStatField stat : CalculatedStatField.values()) {
                String name = stat.name().replaceAll("_", " ");
                double value = stat.getValue(stats);
                Label nameLabel = new Label(name + ": ");
                nameLabel.getStyleClass().add("stat-label");
                Label valueLabel = new Label(String.format("%.2f", value));
                valueLabel.getStyleClass().add("stat-label");

                // colour for winrate
                if (stat == CalculatedStatField.WIN_RATE) {
                    valueLabel.setText(valueLabel.getText() + "%");
                    boolean hasPlayed = stats.getMatchesWon() + stats.getMatchesLost() != 0;
                    if (!hasPlayed) {
                        valueLabel.setStyle("-fx-text-fill: gray;");
                    } else if (value >= 60) {
                        valueLabel.setStyle("-fx-text-fill: green;");
                    } else if (value >= 40) {
                        valueLabel.setStyle("-fx-text-fill: orange;");
                    } else {
                        valueLabel.setStyle("-fx-text-fill: red;");
                    }
                }

                HBox row = new HBox(nameLabel, valueLabel);
                statsBox.getChildren().add(row);
            }

            showElement(statsBox);
            showElement(statsSeparatorTop);
            showElement(statsSeparatorBottom);
        }
    }


    /** Helper to show element, assumes default is hidden */
    private void showElement(Node node) {
        node.setManaged(true);
        node.setVisible(true);
    }

    private void setAttributeLabel(Label label, String fieldName, Team teamValue, boolean isDefault) {
        setAttributeLabel(label, fieldName, teamValue.toString(), isDefault);
    }

    private void setAttributeLabel(Label label, String fieldName, Status statusValue, boolean isDefault) {
        setAttributeLabel(label, fieldName, statusValue.toString(), isDefault);
    }

    private void setAttributeLabel(Label label, String fieldName, Position positionValue, boolean isDefault) {
        setAttributeLabel(label, fieldName, positionValue.toString(), isDefault);
    }

    private void setAttributeLabel(Label label, String fieldName, String value, boolean isDefault) {
        if (isDefault) {
            label.setManaged(false);
            label.setVisible(false);
            return;
        }
        label.setText(fieldName + ": " + value);
        showElement(label);
    }
}
