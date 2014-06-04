package net.anotheria.tmt.process;

import net.anotheria.tmt.Pinger;
import net.anotheria.tmt.State;
import net.anotheria.tmt.TMT;
import net.anotheria.tmt.config.Configuration;
import net.anotheria.tmt.utils.StringUtils;

import javax.swing.*;
import java.util.List;
import java.util.concurrent.Semaphore;

/**
 * @author VKoulakov
 * @since 04.06.14 15:08
 */
public class PingWorker extends SwingWorker<Boolean, State> {

    private Semaphore semaphore;
    private TMT tmt;
    private Pinger pinger;

    public PingWorker(Semaphore semaphore, TMT tmt, Pinger pinger) {
        this.semaphore = semaphore;
        this.tmt = tmt;
        this.pinger = pinger;
    }

    @Override
    protected Boolean doInBackground() throws Exception {
        boolean result = false;
        if (semaphore.tryAcquire()) {
            try {
                Configuration configuration = tmt.getConfiguration();
                if (configuration != null && StringUtils.notEmpty(configuration.getTargetIp())) {
                    publish(State.CONNECTED.equals(tmt.getState()) || State.NONE.equals(tmt.getState()) ? State.REFRESH_ON_SUCCESS : State.REFRESH_ON_FAILURE);
                    //ping
                    result = pinger.ping(configuration.getSourceIp(), configuration.getTargetIp(), configuration.getConnectionTimeout());
                }
            } finally {
                semaphore.release();
            }
        } else {
            cancel(true);
        }
        return result;
    }

    @Override
    protected void done() {
        boolean result = false;
        try {
            result = get();
        } catch (Exception ignored) {
        }
        tmt.changeState(result ? State.CONNECTED : State.DISCONNECTED);
    }

    @Override
    protected void process(List<State> chunks) {
        if (!chunks.isEmpty()) {
            tmt.changeState(chunks.iterator().next());
        }
    }
}
