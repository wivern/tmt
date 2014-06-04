package net.anotheria.tmt.config;

/**
 * @author VKoulakov
 * @since 03.06.14 18:50
 */
public class ConfigurationImpl implements Configuration {
    private int refreshOnSuccess = 30;
    private int refreshOnFailure = 10;
    private int refreshConfig = 10;
    private String debugMessage;
    private int connectionTimeout = 30;
    private String targetIp;
    private String sourceIp;

    @Override
    public int getRefreshOnSuccess() {
        return refreshOnSuccess;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ConfigurationImpl that = (ConfigurationImpl) o;

        if (connectionTimeout != that.connectionTimeout) return false;
        if (refreshOnFailure != that.refreshOnFailure) return false;
        if (refreshOnSuccess != that.refreshOnSuccess) return false;
        if (sourceIp != null ? !sourceIp.equals(that.sourceIp) : that.sourceIp != null) return false;
        if (targetIp != null ? !targetIp.equals(that.targetIp) : that.targetIp != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = refreshOnSuccess;
        result = 31 * result + refreshOnFailure;
        result = 31 * result + connectionTimeout;
        result = 31 * result + (targetIp != null ? targetIp.hashCode() : 0);
        result = 31 * result + (sourceIp != null ? sourceIp.hashCode() : 0);
        return result;
    }

    public void setRefreshOnSuccess(int refreshOnSuccess) {
        this.refreshOnSuccess = refreshOnSuccess;
    }

    @Override
    public int getRefreshOnFailure() {
        return refreshOnFailure;
    }

    public void setRefreshOnFailure(int refreshOnFailure) {
        this.refreshOnFailure = refreshOnFailure;
    }

    @Override
    public int getRefreshConfig() {
        return refreshConfig;
    }

    public void setRefreshConfig(int refreshConfig) {
        this.refreshConfig = refreshConfig;
    }

    @Override
    public String getDebugMessage() {
        return debugMessage;
    }

    public void setDebugMessage(String debugMessage) {
        this.debugMessage = debugMessage;
    }

    @Override
    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    @Override
    public String getTargetIp() {
        return targetIp;
    }

    public void setTargetIp(String targetIp) {
        this.targetIp = targetIp;
    }

    @Override
    public String getSourceIp() {
        return sourceIp;
    }

    public void setSourceIp(String sourceIp) {
        this.sourceIp = sourceIp;
    }
}
