package net.anotheria.tmt;

import net.anotheria.tmt.config.Configuration;
import net.anotheria.tmt.events.StateChangedEvent;
import net.anotheria.tmt.events.StateChangedEventListener;

import javax.swing.event.EventListenerList;

/**
 * @author VKoulakov
 * @since 04.06.14 13:05
 */
public class TMT {
    protected EventListenerList listenerList = new EventListenerList();
    private Configuration configuration;
    private State state;

    public TMT(Configuration configuration) {
        this.configuration = configuration;
    }

    public void start(){
       setState(State.NONE);
    }

    protected void setState(State state){
        this.state = state;
        fireStateChanged(new StateChangedEvent(this, state));
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void addStateChangedListener(StateChangedEventListener listener) {
        listenerList.add(StateChangedEventListener.class, listener);
    }

    public void removeStateChangedListener(StateChangedEventListener listener) {
        listenerList.remove(StateChangedEventListener.class, listener);
    }

    protected void fireStateChanged(StateChangedEvent event) {
        StateChangedEventListener[] listeners = listenerList.getListeners(StateChangedEventListener.class);
        for (StateChangedEventListener l : listeners) {
            l.stateChanged(event);
        }
    }
}
