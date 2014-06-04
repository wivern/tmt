package net.anotheria.tmt.events;

import java.util.EventListener;

/**
 * @author VKoulakov
 * @since 04.06.14 13:08
 */
public interface StateChangedEventListener extends EventListener {
    public void stateChanged(StateChangedEvent event);
}
