package com.allthingsfuchsia;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


public class DBClassInfo {
    
    private DBType dbType;
    private String className;
    private String jar;
    private String urlTemplate;
    private String passwordChangeSql;
    private int defaultPort;

    public static DBClassInfo getDBClassInfo(String dbtype) throws IOException
    {
        InputStream inputStream = DBClassInfo.class.getResourceAsStream("dbClassInfo.json");
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE);
        DBClassInfo dbClassInfo = new DBClassInfo();

            HashMap<String, DBClassInfo>dbClassMap = mapper.readValue(inputStream, new TypeReference<HashMap<String,DBClassInfo>>(){});
            System.out.println();
            dbClassInfo = dbClassMap.get(dbtype);
        return dbClassInfo;
    }

    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public DBType getDbType() {
        return dbType;
    }

    public String getClassName() {
        return className;
    }

    public String getJar() {
        return jar;
    }

    public String getUrlTemplate() {
        return urlTemplate;
    }

    public String getPasswordChangeSql() {
        return passwordChangeSql;
    }

    public int getDefaultPort() {
        return defaultPort;
    }


}