package seedu.address.model.person;

import java.util.Set;

import seedu.address.model.tag.Tag;

/**
 * Represents a Staff member.
 */
public class Staff extends Person {

    /**
     * Constructs a {@code Staff} with the given data.
     *
     * @param name the staff name
     * @param phone the staff phone number
     * @param email the staff email
     * @param address the staff address
     * @param tags the staff tags
     */
    public Staff(Name name, Phone phone, Email email, Address address, Set<Tag> tags) {
        super(name, phone, email, address, tags, Role.STAFF);
    }
}
