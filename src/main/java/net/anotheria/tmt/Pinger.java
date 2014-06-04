package net.anotheria.tmt;

/**
 * @author VKoulakov
 * @since 04.06.14 15:31
 */
public interface Pinger {
    boolean ping(String sourceIp, String targetIp, int timeout);
}
