package net.anotheria.tmt.events;

import java.util.EventListener;

/**
 * User: AAbushkevich
 * Date: 04.06.14
 * Time: 15:45
 */
public interface LocaleChangedEventListener extends EventListener {
    public void localeChanged(LocaleChangedEvent event);
}
