package com.allthingsfuchsia;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.ibm.db2.jcc.DB2Connection;

public class DBUtil {
    private Connection connection;
    private DBInfo dbInfo;

    public DBUtil(DBInfo dbInfo) {

        this.dbInfo = dbInfo;
        // Load the driver class
        // try {
        // Class.forName(dbInfo.getClassName());
        // } catch (ClassNotFoundException ex) {
        // System.out.println("Unable to load the class. Terminating the program");
        // System.exit(-1);
        // }

        // get the connection
        try {

            this.connection = DriverManager.getConnection(dbInfo.getURL(), dbInfo.getProperties());

        } catch (SQLException ex) {
            System.out.println("Error getting connection: " + ex.getMessage());
            System.exit(-1);
        } catch (Exception ex) {
            System.out.println("Error: " + ex.getMessage());
            System.exit(-1);
        }

        if (connection != null) {
            System.out.println("Connected Successfully!");
        }

    }

    public String changePassword() {
        if (this.dbInfo.getDBType() == DBType.DB2) {
            DB2Connection conn = (DB2Connection) this.connection;
            try {
                conn.changeDB2Password(this.dbInfo.password, this.dbInfo.newPassword);
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return this.dbInfo.newPassword;
        } else {
            String query = this.dbInfo.getPasswordchangeSql();
            System.out.println(query);
            try (Statement stmt = this.connection.createStatement()) {
                if (stmt.execute(query)) {
                    ResultSet resultSet = stmt.getResultSet();
                    System.out.println("resultset:" + resultSet.toString());
                } else {
                    System.out.println("UpdateCount:  " + stmt.getUpdateCount());
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return this.dbInfo.newPassword;
        }
    }

    public void showTableMetaData(String Schema, String tableName) {
        try {
            DatabaseMetaData databaseMetaData = this.connection.getMetaData();

            // get columns
            ResultSet columns = databaseMetaData.getColumns(null, Schema, tableName, null);
            while (columns.next()) {
                String columnName = columns.getString("COLUMN_NAME");
                String datatype = columns.getString("DATA_TYPE");
                String columnsize = columns.getString("COLUMN_SIZE");
                String decimaldigits = columns.getString("DECIMAL_DIGITS");
                String isNullable = columns.getString("IS_NULLABLE");
                String is_autoIncrment = columns.getString("IS_AUTOINCREMENT");

                // Printing results
                System.out.println(columnName + "---" + datatype + "---" + columnsize + "---" + decimaldigits + "---"
                        + isNullable + "---" + is_autoIncrment);

            }

            // GetPrimarykeys
            ResultSet PK = databaseMetaData.getPrimaryKeys(null, null, tableName);
            System.out.println("------------PRIMARY KEYS-------------");
            while (PK.next()) {
                System.out.println(PK.getString("COLUMN_NAME") + "===" + PK.getString("PK_NAME"));
            }

            // Get Foreign Keys
            ResultSet FK = databaseMetaData.getImportedKeys(null, null, tableName);
            System.out.println("------------FOREIGN KEYS-------------");
            while (FK.next()) {
                System.out.println(FK.getString("PKTABLE_NAME") + "---" + FK.getString("PKCOLUMN_NAME") + "==="
                        + FK.getString("FKTABLE_NAME") + "---" + FK.getString("FKCOLUMN_NAME"));
            }

        } catch (SQLException ex) {
            System.out.println("Error while fetching metadata. Terminating program.. " + ex.getMessage());
            System.exit(-1);
        } catch (Exception ex) {
            System.out.println("Error while fetching metadata. Terminating program.. " + ex.getMessage());
            System.exit(-1);
        }
    }

    public ArrayList<String> getTablesList() {
        DatabaseMetaData databaseMetaData = null;
        ArrayList<String> tableList = new ArrayList<>();

        // Fetching Database Metadata from connection
        try {
            databaseMetaData = this.connection.getMetaData();
            ResultSet resultSet = databaseMetaData.getTables(null, null, null, null);
            while (resultSet.next()) {
                tableList.add(resultSet.getString("TABLE_NAME"));
            }
        } catch (SQLException ex) {
            System.out.println("Error while fetching metadata. Terminating program.. " + ex.getMessage());
            System.exit(-1);
        } catch (Exception ex) {
            System.out.println("Error while fetching metadata. Terminating program.. " + ex.getMessage());
            System.exit(-1);
        }

        return tableList;
    }

    public void executeQuery(String query) {
        ResultSet resultSet = null;
        try {
            // executing query
            Statement stmt = this.connection.createStatement();
            resultSet = stmt.executeQuery(query);

            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnsNumber = metaData.getColumnCount();

            // Printing the results
            while (resultSet.next()) {

                for (int i = 1; i <= columnsNumber; i++) {
                    System.out.printf("%-25s",
                            (resultSet.getObject(i) != null) ? resultSet.getObject(i).toString() : null);
                }
                System.out.printf("\n");
            }
        } catch (SQLException ex) {
            System.out.println("Exception while executing statement. Terminating program... " + ex.getMessage());
        } catch (Exception ex) {
            System.out.println("General exception while executing query. Terminating the program..." + ex.getMessage());
        }

    }

    public void listAllTables() {
        DatabaseMetaData databaseMetaData = null;

        // Fetching Database Metadata from connection
        try {
            databaseMetaData = connection.getMetaData();

            // Print TABLE_TYPE "TABLE"
            ResultSet resultSet = databaseMetaData.getTables(null, null, null, new String[] { "TABLE" });
            System.out.println("Printing TABLE_TYPE \"TABLE\" ");
            System.out.println("----------------------------------");
            while (resultSet.next()) {
                // Print
                System.out.println(resultSet.getString("TABLE_SCHEM") + "." + resultSet.getString("TABLE_NAME"));
            }

            /*
             * Print TABLE_TYPE "SYSTEM TABLE"
             * resultSet = databaseMetaData.getTables(null, null, null, new
             * String[]{"SYSTEM TABLE"});
             * System.out.println("Printing TABLE_TYPE \"SYSTEM TABLE\" ");
             * System.out.println("----------------------------------");
             * while(resultSet.next())
             * {
             * //Print
             * System.out.println(resultSet.getString("TABLE_NAME"));
             * }
             * 
             * //Print TABLE_TYPE "VIEW"
             * resultSet = databaseMetaData.getTables(null, null, null, new
             * String[]{"VIEW"});
             * System.out.println("Printing TABLE_TYPE \"VIEW\" ");
             * System.out.println("----------------------------------");
             * while(resultSet.next())
             * {
             * //Print
             * System.out.println(resultSet.getString("TABLE_NAME"));
             * }
             * 
             */
        } catch (SQLException ex) {
            System.out.println("Error while fetching metadata. Terminating program.. " + ex.getMessage());
            System.exit(-1);
        } catch (Exception ex) {
            System.out.println("Error while fetching metadata. Terminating program.. " + ex.getMessage());
            System.exit(-1);
        }

    }

}
