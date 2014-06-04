package net.anotheria.tmt.events;

import net.anotheria.tmt.State;

import java.util.EventObject;

/**
 * @author VKoulakov
 * @since 04.06.14 13:07
 */
public class StateChangedEvent extends EventObject {

    private State state;

    /**
     * Constructs a prototypical Event.
     *
     * @param source The object on which the Event initially occurred.
     * @throws IllegalArgumentException if source is null.
     */
    public StateChangedEvent(Object source, State state) {
        super(source);
        this.state = state;

    }

    public State getState() {
        return state;
    }
}
