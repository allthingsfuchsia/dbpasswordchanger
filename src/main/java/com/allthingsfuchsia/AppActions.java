package com.allthingsfuchsia;

import java.util.Arrays;

public enum AppActions {
    changePassword,
    listAllTables,
    executeQuery,
    showTableMetaData;

    public static String getValues() {
        return Arrays.asList(AppActions.values()).toString();
    }
    
}


