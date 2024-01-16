package org.acom.beans;

import java.util.Properties;

public class ConfigBean {


    private String serverIP;
    private String port;
    private String protocol;
    private boolean logger;
    private String username;
    private String password;

    public ConfigBean() {
    }

    public ConfigBean(String serverIP, String port, String protocol, boolean logger, String username, String password) {
        this.serverIP = serverIP;
        this.port = port;
        this.protocol = protocol;
        this.logger = logger;
        this.username = username;
        this.password = password;
    }

    public static ConfigBean fromProperties(Properties properties) {
        ConfigBean configBean = new ConfigBean();
        if (properties.getProperty("SERVER_IP") == null) {
            throw new IllegalStateException("Property 'SERVER_IP' is not set. Program terminated.");
        } else {
            configBean.setServerIP(properties.getProperty("SERVER_IP"));
        }

        if (properties.getProperty("PORT") == null) {
            throw new IllegalStateException("Property 'PORT' is not set. Program terminated.");
        } else {
            configBean.setPort(properties.getProperty("PORT"));
        }

        if (properties.getProperty("PROTOCOL") == null) {
            throw new IllegalStateException("Property 'PROTOCOL' is not set. Program terminated.");
        } else {
            configBean.setProtocol(properties.getProperty("PROTOCOL"));
        }

        if (properties.getProperty("USERNAME") == null) {
            throw new IllegalStateException("Property 'USERNAME' is not set. Program terminated.");
        } else {
            configBean.setUsername(properties.getProperty("USERNAME"));
        }
        if (properties.getProperty("PASSWORD") == null) {
            throw new IllegalStateException("Property 'PASSWORD' is not set. Program terminated.");
        } else {
            configBean.setPassword(properties.getProperty("PASSWORD"));
        }
        if (properties.getProperty("LOGGER") == null) {
            throw new IllegalStateException("Property 'LOGGER' is not set. Program terminated.");
        } else if (properties.getProperty("LOGGER").equals("0")) {
            configBean.setLogger(false);
        } else {
            configBean.setLogger(true);
        }
        return configBean;
    }

    public String getServerIP() {
        return serverIP;
    }

    public void setServerIP(String serverIP) {
        this.serverIP = serverIP;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public boolean isLogger() {
        return logger;
    }

    public void setLogger(boolean logger) {
        this.logger = logger;
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
                "serverIP='" + serverIP + '\'' +
                ", port='" + port + '\'' +
                ", protocol='" + protocol + '\'' +
                ", logger=" + logger +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
