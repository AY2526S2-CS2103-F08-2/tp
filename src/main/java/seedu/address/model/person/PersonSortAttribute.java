package seedu.address.model.person;

import java.util.Comparator;
import java.util.stream.Stream;

/**
 * Supported sortable person attributes.
 */
public enum PersonSortAttribute {
    NAME("name", Comparator
            .comparing((Person person) -> person.getName().fullName, String.CASE_INSENSITIVE_ORDER)
            .thenComparing(person -> person.getEmail().value, String.CASE_INSENSITIVE_ORDER)),
    EMAIL("email", Comparator
            .comparing((Person person) -> person.getEmail().value, String.CASE_INSENSITIVE_ORDER)
            .thenComparing(person -> person.getName().fullName, String.CASE_INSENSITIVE_ORDER)),
    TEAM("team", Comparator
            .comparing((Person person) -> person.getTeam().value, String.CASE_INSENSITIVE_ORDER)
            .thenComparing(person -> person.getName().fullName, String.CASE_INSENSITIVE_ORDER)),
    STATUS("status", Comparator
            .comparing((Person person) -> person.getStatus().value, String.CASE_INSENSITIVE_ORDER)
            .thenComparing(person -> person.getName().fullName, String.CASE_INSENSITIVE_ORDER)),
    POSITION("position", Comparator
            .comparing((Person person) -> person.getPosition().value, String.CASE_INSENSITIVE_ORDER)
            .thenComparing(person -> person.getName().fullName, String.CASE_INSENSITIVE_ORDER)),
    GOALS("goals", Comparator
            .comparingInt((Person person) -> getStatValue(person, StatField.GOALS))
            .thenComparing(person -> person.getName().fullName, String.CASE_INSENSITIVE_ORDER)),
    WINS("wins", Comparator
            .comparingInt((Person person) -> getStatValue(person, StatField.WINS))
            .thenComparing(person -> person.getName().fullName, String.CASE_INSENSITIVE_ORDER)),
    LOSSES("losses", Comparator
            .comparingInt((Person person) -> getStatValue(person, StatField.LOSSES))
            .thenComparing(person -> person.getName().fullName, String.CASE_INSENSITIVE_ORDER));

    private final String keyword;
    private final Comparator<Person> comparator;

    PersonSortAttribute(String keyword, Comparator<Person> comparator) {
        this.keyword = keyword;
        this.comparator = comparator;
    }

    public String getKeyword() {
        return keyword;
    }

    public Comparator<Person> getComparator() {
        return comparator;
    }

    /**
     * Returns whether this sort attribute is based on player-only stats.
     */
    public boolean isPlayerStatAttribute() {
        return this == GOALS || this == WINS || this == LOSSES;
    }

    /**
     * Returns the sort attribute matching the given keyword.
     */
    public static PersonSortAttribute fromKeyword(String keyword) {
        return Stream.of(values())
                .filter(attribute -> attribute.keyword.equalsIgnoreCase(keyword))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unsupported sort attribute: " + keyword));
    }

    private static int getStatValue(Person person, StatField statField) {
        if (!(person instanceof Player)) {
            return 0;
        }

        return statField.getValue(((Player) person).getStats());
    }
}
