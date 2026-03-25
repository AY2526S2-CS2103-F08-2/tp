package seedu.address.model.event;

/**
 * Represents the type of event in the address book.
 */
public enum EventType {
    MATCH;

    public static final String MESSAGE_CONSTRAINTS = "EventType must be MATCH";

    /**
     * Returns if a given string is a valid event type.
     */
    public static boolean isValidEventType(String test) {
        for (EventType eventType : EventType.values()) {
            if (eventType.name().equals(test)) {
                return true;
            }
        }
        return false;
    }
}
