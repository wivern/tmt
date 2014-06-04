package net.anotheria.tmt.process;

import net.anotheria.tmt.TMT;
import net.anotheria.tmt.config.Configuration;
import net.anotheria.tmt.config.ConfigurationManager;

import javax.swing.*;
import java.util.concurrent.ExecutionException;

/**
 * @author VKoulakov
 * @since 04.06.14 14:33
 */
public class ConfigWorker extends SwingWorker<Configuration,Void> {

    private TMT tmt;

    public ConfigWorker(TMT tmt) {
        this.tmt = tmt;
    }

    @Override
    protected Configuration doInBackground() throws Exception {
        return ConfigurationManager.reread();
    }

    @Override
    protected void done() {
        try {
            tmt.setConfiguration(get());
        } catch (InterruptedException e) {
        } catch (ExecutionException e) {
        }
    }
}
