package org.acom.beans;

public class ConfigBean {



    private String ServerIP;
    private boolean logState;
    private String username;
    private String password;

    public ConfigBean() {
    }

    public ConfigBean(String serverIP, boolean logState, String username, String password) {
        ServerIP = serverIP;
        this.logState = logState;
        this.username = username;
        this.password = password;
    }

    public String getServerIP() {
        return ServerIP;
    }

    public void setServerIP(String serverIP) {
        ServerIP = serverIP;
    }

    public boolean isLogState() {
        return logState;
    }

    public void setLogState(boolean logState) {
        this.logState = logState;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "ConfigBean{" +
                "ServerIP='" + ServerIP + '\'' +
                ", logState=" + logState +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
