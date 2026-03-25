package seedu.address.testutil;

import java.util.Set;

import seedu.address.logic.commands.EventEditCommand.EditEventDescriptor;
import seedu.address.model.event.Date;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventName;
import seedu.address.model.event.EventType;

/**
 * A utility class to help with building EditEventDescriptor objects.
 */
public class EditEventDescriptorBuilder {

    private EditEventDescriptor descriptor;

    public EditEventDescriptorBuilder() {
        descriptor = new EditEventDescriptor();
    }

    public EditEventDescriptorBuilder(EditEventDescriptor descriptor) {
        this.descriptor = new EditEventDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditEventDescriptor} with fields containing {@code event}'s details
     */
    public EditEventDescriptorBuilder(Event event) {
        descriptor = new EditEventDescriptor();
        descriptor.setEventName(event.getEventName());
        descriptor.setEventType(event.getEventType());
        descriptor.setEventDate(event.getEventDate());
        descriptor.setEventPlayerNames(event.getEventPlayerList().getPlayerNames());
    }

    /**
     * Sets the {@code EventName} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withEventName(String name) {
        descriptor.setEventName(new EventName(name));
        return this;
    }

    /**
     * Sets the {@code EventType} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withEventType(String type) {
        descriptor.setEventType(EventType.valueOf(type));
        return this;
    }

    /**
     * Sets the {@code EventDate} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withEventDate(String date) {
        descriptor.setEventDate(new Date(date));
        return this;
    }

    /**
     * Sets the {@code EventPlayerNames} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withEventPlayerNames(Set<String> eventPlayerNames) {
        descriptor.setEventPlayerNames(eventPlayerNames);
        return this;
    }

    public EditEventDescriptor build() {
        return descriptor;
    }
}
