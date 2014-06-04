package net.anotheria.tmt.config;

/**
 * @author VKoulakov
 * @since 04.06.14 13:17
 */
public interface Configuration {

    int getRefreshOnSuccess();

    int getRefreshOnFailure();

    int getRefreshConfig();

    String getDebugMessage();

    int getConnectionTimeout();

    String getTargetIp();

    String getSourceIp();
}
