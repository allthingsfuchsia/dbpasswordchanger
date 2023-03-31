package com.allthingsfuchsia;

public class DBInfo {
    DBClassInfo dbClassInfo;
    String hostName;
    int port;
    String userName;
    String password;
    String serviceName;
    String URL;

    public String getURL() {
        if (!this.URL.isEmpty()) {
            return this.URL;
        } else {
            StringBuilder url = new StringBuilder(dbClassInfo.urlTemplate);
            return url.toString();
        }
    }

    public String getPasswordchangeSql() {
        StringBuilder pcs = new StringBuilder(dbClassInfo.passwordChangeSql);
        return pcs.toString();

    }
}
