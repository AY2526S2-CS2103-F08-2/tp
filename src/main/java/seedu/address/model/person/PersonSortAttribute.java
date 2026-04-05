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
     * Returns the sort attribute matching the given keyword.
     */
    public static PersonSortAttribute fromKeyword(String keyword) {
        return Stream.of(values())
                .filter(attribute -> attribute.keyword.equalsIgnoreCase(keyword))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Unsupported sort attribute: " + keyword));
    }
}
