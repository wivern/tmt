package net.anotheria.tmt;

import net.anotheria.tmt.config.Configuration;
import net.anotheria.tmt.events.*;
import net.anotheria.tmt.pinger.NativePinger;
import net.anotheria.tmt.process.ConfigWorker;
import net.anotheria.tmt.process.PingWorker;
import net.anotheria.tmt.utils.StringUtils;

import javax.swing.*;
import javax.swing.event.EventListenerList;
import java.awt.event.ActionEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.EventListener;
import java.util.EventObject;
import java.util.Locale;
import java.util.concurrent.Semaphore;

/**
 * @author VKoulakov
 * @since 04.06.14 13:05
 */
public class TMT {
    public final AbstractAction CONFIG_REFRESH_ACTION = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            new ConfigWorker(TMT.this).execute();
        }
    };
    public final AbstractAction PING_ACTION = new AbstractAction() {
        @Override
        public void actionPerformed(ActionEvent e) {
            new PingWorker(semaphore, TMT.this, new NativePinger()).execute();
        }
    };
    protected EventListenerList listenerList = new EventListenerList();
    private Configuration configuration;
    private State state;
    private Timer confTimer;
    private Timer pingTimer;
    private Semaphore semaphore = new Semaphore(1);

    public TMT(Configuration configuration) {
        this.configuration = configuration;
    }

    public void start() {
        setState(State.NONE);
        fireConfigurationChanged(new ConfigurationChangedEvent(this, configuration));
        setConfiguration(configuration);
        restartPingWorker(configuration);
    }

    public void setLocale(Locale locale) {
        Resources.setLocalization(locale);
        fireLocaleChanged(new LocaleChangedEvent(this, locale));
    }

    public Configuration getConfiguration() {
        return configuration;
    }

    public void setConfiguration(Configuration configuration) {
        fireConfigurationChanged(new ConfigurationChangedEvent(this, configuration));
        //start config reload
        restartConfigReload(configuration);
        //start ping worker
        if (configuration != null && !configuration.equals(this.configuration)) {
            restartPingWorker(configuration);
        }
        this.configuration = configuration;
    }

    public void addEventListener(EventListener listener, Class clazz) {
        listenerList.add(clazz, listener);
    }

    public void removeEventListener(EventListener listener, Class clazz) {
        listenerList.remove(clazz, listener);
    }

    protected void fireStateChanged(StateChangedEvent event) {
        fireEvent(event, StateChangedEventListener.class, new FireEventCallback<StateChangedEvent, StateChangedEventListener>() {
            @Override
            public void onEvent(StateChangedEvent event, StateChangedEventListener listener) {
                listener.stateChanged(event);
            }
        });
    }

    protected void fireConfigurationChanged(ConfigurationChangedEvent event) {
        fireEvent(event, ConfigurationChangedEventListener.class, new FireEventCallback<ConfigurationChangedEvent, ConfigurationChangedEventListener>() {
            @Override
            public void onEvent(ConfigurationChangedEvent event, ConfigurationChangedEventListener listener) {
                listener.configurationChanged(event);
            }
        });
    }

    protected void fireLocaleChanged(LocaleChangedEvent event) {
        fireEvent(event, LocaleChangedEventListener.class, new FireEventCallback<LocaleChangedEvent, LocaleChangedEventListener>() {
            @Override
            public void onEvent(LocaleChangedEvent event, LocaleChangedEventListener listener) {
                listener.localeChanged(event);
            }
        });
    }

    protected <E extends EventObject, L extends EventListener> void fireEvent(E event, Class<L> listenerClass, FireEventCallback<E, L> callback) {
        L[] listeners = listenerList.getListeners(listenerClass);
        for (L l : listeners) {
            callback.onEvent(event, l);
        }
    }

    private void restartConfigReload(Configuration configuration) {
        if (confTimer != null && confTimer.isRunning()) {
            confTimer.stop();
        }
        confTimer = new Timer(configuration.getRefreshConfig() * 1000, CONFIG_REFRESH_ACTION);
        confTimer.start();
    }

    private void restartPingWorker(Configuration configuration) {
        String targetIp = configuration.getTargetIp();
        if (StringUtils.notEmpty(targetIp) && validateAddress(targetIp)) {
            int refresh = State.DISCONNECTED.equals(state) ? configuration.getRefreshOnFailure() : configuration.getRefreshOnSuccess();
            pingTimer = new Timer(refresh * 1000, PING_ACTION);
            pingTimer.setInitialDelay(0);
            pingTimer.setRepeats(true);
            pingTimer.start();
        } else {
            setState(State.NONE);
        }
    }

    private boolean validateAddress(String address) {
        try {
            InetAddress.getByName(address);
            return true;
        } catch (UnknownHostException e) {
            return false;
        }
    }

    public State getState() {
        return state;
    }

    protected void setState(State state) {
        this.state = state;
        fireStateChanged(new StateChangedEvent(this, state));
    }

    public void changeState(State next) {
        setState(next);
    }

    interface FireEventCallback<E extends EventObject, L extends EventListener> {
        void onEvent(E event, L listener);
    }
}
