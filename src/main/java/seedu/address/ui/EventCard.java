package seedu.address.ui;

import java.util.Comparator;
import java.util.stream.Collectors;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.event.Event;

/**
 * An UI component that displays information of a {@code Match}.
 */
public class EventCard extends UiPart<Region> {

    private static final String FXML = "EventListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final Event event;

    @FXML
    private Label id;
    @FXML
    private HBox cardPane;
    @FXML
    private Label eventName;
    @FXML
    private Label date;
    @FXML
    private Label playerList;

    /**
     * Creates a {@code EventCard} with the given {@code Event} and index to display.
     */
    public EventCard(Event event, int displayedIndex) {
        super(FXML);
        this.event = event;
        id.setText(displayedIndex + ". ");
        switch (event.getEventType()) {
        case MATCH -> {
            eventName.setText("Match: vs " + event.getEventName().toString());
            date.setText("Match Date: " + event.getEventDate().toString());
        }
        case TRAINING -> {
            eventName.setText("Training: " + event.getEventName().toString());
            date.setText("Training Date: " + event.getEventDate().toString());
        }
        default -> {
            eventName.setText("Event Name: " + event.getEventName().toString());
            date.setText("Event Date: " + event.getEventDate().toString());
        }
        }

        String players = event.getEventPlayerList()
                .asUnmodifiableObservableList()
                .stream()
                .sorted(Comparator.comparing(person -> person.getName().fullName))
                .map(person -> person.getName().fullName)
                .collect(Collectors.joining(", "));

        playerList.setText("Players: " + players);
    }
}
