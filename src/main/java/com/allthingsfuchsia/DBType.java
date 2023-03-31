package com.allthingsfuchsia;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;

public enum DBType {
    MSSQL,
    ORACLE,
    DB2,
    MONGODB,
    POSTGRES,
    MYSQL,
    SYBASE,
    INFORMIX,
    CASSANDRA,
    MARIADB,
    @JsonEnumDefaultValue
    UNKNOWN;

    public static String getValues() {
        return Arrays.asList(DBType.values()).toString();
    }
}
