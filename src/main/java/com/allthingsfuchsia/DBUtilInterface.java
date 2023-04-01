package com.allthingsfuchsia;

import java.util.ArrayList;

public interface DBUtilInterface {

    String changePassword();

    void showTableMetaData(String Schema, String tableName);

    ArrayList<String> getTablesList();

    void executeQuery(String query);

    void listAllTables();

    void checkHeartbeat();

}