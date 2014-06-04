package net.anotheria.tmt.pinger;

import net.anotheria.tmt.Pinger;

import java.io.IOException;
import java.net.InetAddress;

/**
 * @author VKoulakov
 * @since 04.06.14 15:34
 */
public class NativePinger implements Pinger {
    @Override
    public boolean ping(String sourceIp, String targetIp, int timeout) {
        try {
            Thread.sleep(3000);
            boolean result = InetAddress.getByName(targetIp).isReachable(timeout);
            if (!result){

            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }
}
