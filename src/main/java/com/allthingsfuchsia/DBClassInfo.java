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
    public DBType dbType;
    public String className;
    public String jar;
    public String urlTemplate;
    public String passwordChangeSql;
    public int defaultPort;

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

}