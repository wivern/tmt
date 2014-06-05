package net.anotheria.tmt.pinger;

import org.shortpasta.icmp2.IcmpPingRequest;
import org.shortpasta.icmp2.IcmpPingResponse;
import org.shortpasta.icmp2.IcmpPingUtil;

import java.io.IOException;

/**
 * @author VKoulakov
 * @since 05.06.14 14:45
 */
public class ShortPastaPinger extends NativePinger {

    public static final int PING_ATTEMPTS = 4;

    @Override
    protected boolean pingIcmp(String sourceIp, String targetIp, int timeout) throws IOException {
        boolean result = false;
        for(int i = 0; i < PING_ATTEMPTS; i++){
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
            }
            IcmpPingRequest request = IcmpPingUtil.createIcmpPingRequest();
            request.setHost(targetIp);
            request.setTimeout(timeout * 1000);
            final IcmpPingResponse response = IcmpPingUtil.executePingRequest(request);
            result |= response.getSuccessFlag();
        }
        return result;
    }
}
