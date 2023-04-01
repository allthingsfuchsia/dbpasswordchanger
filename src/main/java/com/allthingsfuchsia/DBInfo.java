package com.allthingsfuchsia;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.text.StringSubstitutor;
import java.util.Properties;

public class DBInfo {
    private DBClassInfo dbClassInfo;
    private String hostName;
    private int port;
    private String userName;
    private String password;
    private String serviceName;
    private String uRL;
    private String newPassword;
    private String databaseName;


    public DBInfo(DBClassInfo dbClassInfo, String hostName, int port, String userName, String password,
            String serviceName, String uRL, String newPassword, String databaseName) {
        this.dbClassInfo = dbClassInfo;
        this.hostName = hostName;
        this.port = port;
        this.userName = userName;
        this.password = password;
        this.serviceName = serviceName;
        this.uRL = uRL;
        this.newPassword = newPassword;
        this.databaseName = databaseName;
    }

    public String getURL() {
        if (this.uRL != null) {
            System.out.println("DBInfo.url: " + this.uRL.toString());
            return this.uRL;
        } else {
            System.out.println("DBInfo.url: " + this.dbClassInfo.getUrlTemplate());
            StringSubstitutor sub = new StringSubstitutor(this.toHashMap());
            String resolvedURL = sub.replace(this.dbClassInfo.getUrlTemplate());
            return resolvedURL.toString();
        }
    }

    public String getPasswordchangeSql() {
        StringSubstitutor sub = new StringSubstitutor(this.toHashMap());
        String resolvedPCS = sub.replace(this.dbClassInfo.getPasswordChangeSql());
        return resolvedPCS.toString();
    }

    public Map<String, String> toHashMap() {

        Map<String, String> valuesMap = new HashMap<>();
        valuesMap.put("hostName", this.hostName);
        valuesMap.put("port", String.format("%d", this.port));
        valuesMap.put("serviceName", this.serviceName);
        valuesMap.put("userName", this.userName);
        valuesMap.put("newPassword", this.newPassword);
        valuesMap.put("password", this.password);
        valuesMap.put("databaseName", this.databaseName);
        return valuesMap;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public String getClassName() {
        return this.dbClassInfo.getClassName();
    }

    public DBType getDBType() {
        return this.dbClassInfo.getDbType();

    }

    public Properties getProperties() {
        Properties props = new java.util.Properties();
        props.setProperty("password", this.password);
        props.setProperty("user", this.userName);
        if (this.serviceName != null) {
            props.setProperty("databaseName", this.serviceName);
        }
        return props;
    }

    public DBClassInfo getDbClassInfo() {
        return dbClassInfo;
    }

    public void setDbClassInfo(DBClassInfo dbClassInfo) {
        this.dbClassInfo = dbClassInfo;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getuRL() {
        return uRL;
    }

    public void setuRL(String uRL) {
        this.uRL = uRL;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getDatabaseName() {
        return databaseName;
    }

    public void setDatabaseName(String databaseName) {
        this.databaseName = databaseName;
    }
}
