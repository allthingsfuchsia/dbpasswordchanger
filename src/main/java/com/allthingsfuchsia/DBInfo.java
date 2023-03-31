package com.allthingsfuchsia;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.text.StringSubstitutor;
import java.util.Properties;

public class DBInfo {
    private DBClassInfo dbClassInfo;
    String hostName;
    int port;
    String userName;
    String password;
    String serviceName;
    String uRL;
    String newPassword;

    public DBInfo(DBClassInfo dbClassInfo, String hostName, int port, String userName, String password,
            String serviceName, String uRL, String newPassword) {
        this.dbClassInfo = dbClassInfo;
        this.hostName = hostName;
        this.port = port;
        this.userName = userName;
        this.password = password;
        this.serviceName = serviceName;
        this.uRL = uRL;
        this.newPassword = newPassword;
    }

    public String getURL() {
        if (this.uRL != null) {
            System.out.println("DBInfo.url: " + this.uRL.toString());
            return this.uRL;
        } else {
            System.out.println("DBInfo.url: " + this.dbClassInfo.urlTemplate);
            StringSubstitutor sub = new StringSubstitutor(this.toHashMap());
            String resolvedURL = sub.replace(this.dbClassInfo.urlTemplate);
            return resolvedURL.toString();
        }
    }

    public String getPasswordchangeSql() {
        StringSubstitutor sub = new StringSubstitutor(this.toHashMap());
        String resolvedPCS = sub.replace(this.dbClassInfo.passwordChangeSql);
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
        return valuesMap;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public String getClassName() {
        return this.dbClassInfo.className;
    }

    public DBType getDBType() {
        return this.dbClassInfo.dbType;

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

}
