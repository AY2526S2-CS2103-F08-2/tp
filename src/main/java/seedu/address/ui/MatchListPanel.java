package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.match.Match;
import seedu.address.model.person.Person;

/**
 * Panel containing the list of persons.
 */
public class MatchListPanel extends UiPart<Region> {
    private static final String FXML = "MatchListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(MatchListPanel.class);

    @FXML
    private ListView<Match> matchListView;

    /**
     * Creates a {@code MatchListPanel} with the given {@code ObservableList}.
     */
    public MatchListPanel(ObservableList<Match> matchList) {
        super(FXML);
        matchListView.setItems(matchList);
        matchListView.setCellFactory(listView -> new MatchListViewCell());
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Match} using a {@code MatchCard}.
     */
    class MatchListViewCell extends ListCell<Match> {
        @Override
        protected void updateItem(Match match, boolean empty) {
            super.updateItem(match, empty);

            if (empty || match == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(new MatchCard(match, getIndex() + 1).getRoot());
            }
        }
    }

}
