package net.anotheria.tmt.config;

/**
 * @author VKoulakov
 * @since 03.06.14 18:50
 */
public class Configuration {
    private int refreshOnSuccess = 30;
    private int refreshOnFailure = 10;
    private int refreshConfig = 10;
    private String debugMessage;
    private int connectionTimeout = 30;
    private String targetIp;
    private String sourceIp;

    public int getRefreshOnSuccess() {
        return refreshOnSuccess;
    }

    public void setRefreshOnSuccess(int refreshOnSuccess) {
        this.refreshOnSuccess = refreshOnSuccess;
    }

    public int getRefreshOnFailure() {
        return refreshOnFailure;
    }

    public void setRefreshOnFailure(int refreshOnFailure) {
        this.refreshOnFailure = refreshOnFailure;
    }

    public int getRefreshConfig() {
        return refreshConfig;
    }

    public void setRefreshConfig(int refreshConfig) {
        this.refreshConfig = refreshConfig;
    }

    public String getDebugMessage() {
        return debugMessage;
    }

    public void setDebugMessage(String debugMessage) {
        this.debugMessage = debugMessage;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    public String getTargetIp() {
        return targetIp;
    }

    public void setTargetIp(String targetIp) {
        this.targetIp = targetIp;
    }

    public String getSourceIp() {
        return sourceIp;
    }

    public void setSourceIp(String sourceIp) {
        this.sourceIp = sourceIp;
    }
}
