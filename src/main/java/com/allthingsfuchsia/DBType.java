package com.allthingsfuchsia;

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
    UNKNOWN
}
