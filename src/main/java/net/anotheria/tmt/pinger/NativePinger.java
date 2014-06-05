package net.anotheria.tmt.pinger;

import net.anotheria.tmt.Pinger;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

/**
 * @author VKoulakov
 * @since 04.06.14 15:34
 */
public class NativePinger implements Pinger {
    @Override
    public boolean ping(String sourceIp, String targetIp, int timeout) {
        try {
//            Thread.sleep(3000);
            boolean result = pingIcmp(sourceIp, targetIp, timeout);
            if (!result) {
                result = pingTcp(sourceIp, targetIp, timeout);
            }
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return false;
    }

    protected boolean pingIcmp(String sourceIp, String targetIp, int timeout) throws IOException {
        return InetAddress.getByName(targetIp).isReachable(timeout);
    }

    protected boolean pingTcp(String sourceIp, String targetIp, int timeout) throws IOException, InterruptedException {
        boolean result;SocketChannel sc = SocketChannel.open();
        sc.configureBlocking(false);
        sc.connect(new InetSocketAddress(targetIp, 445)); //SMB port by default
        try {
            int retries = 0;
            while (!sc.finishConnect() && retries++ < 3) {
                Thread.sleep(timeout * 1000);
            }
            result = sc.isConnected();
        } finally {
            sc.close();
        }
        return result;
    }
}
