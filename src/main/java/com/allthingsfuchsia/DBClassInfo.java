package com.allthingsfuchsia;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DBClassInfo {
    public DBType dbType;
    public String className;
    public String jar;
    public String urlTemplate;
    public String passwordChangeSql;

    public static DBClassInfo getDBClassInfo(String dbtype)
    {
        InputStream inputStream = DBClassInfo.class.getResourceAsStream("dbClassInfo.json");
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE);
        DBClassInfo dbClassInfo = new DBClassInfo();
        try {
            HashMap<String, DBClassInfo>dbClassMap1 = mapper.readValue(inputStream, new TypeReference<HashMap<String,DBClassInfo>>(){});
            System.out.println();
            System.out.println(dbClassMap1);
            dbClassInfo = dbClassMap1.get(dbtype);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return dbClassInfo;
    }

}