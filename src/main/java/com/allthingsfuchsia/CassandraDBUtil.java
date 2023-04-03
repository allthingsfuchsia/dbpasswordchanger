package com.allthingsfuchsia;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Arrays;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.CqlSessionBuilder;
import com.datastax.oss.driver.api.core.cql.ColumnDefinition;
import com.datastax.oss.driver.api.core.cql.ColumnDefinitions;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;

public class CassandraDBUtil implements DBUtilInterface {

    private CqlSession session;
    private DBInfo dbInfo;

    public CassandraDBUtil(DBInfo dbInfo) {
        this.dbInfo = dbInfo;
        CqlSessionBuilder builder = CqlSession.builder();
        builder.addContactPoint(new InetSocketAddress(dbInfo.getHostName(), dbInfo.getPort()));
        builder.withKeyspace(dbInfo.getServiceName());
        builder.withLocalDatacenter(dbInfo.getDatabaseName());
        builder.withAuthCredentials(dbInfo.getUserName(), dbInfo.getPassword());
        session = builder.build();
    }

    @Override
    public String changePassword() {
        String query = this.dbInfo.getPasswordchangeSql();
        ResultSet resultSet = session.execute(query);
        System.out.println(resultSet.all().toString());
        session.close();
        return this.dbInfo.getNewPassword();
    }

    @Override
    public void showTableMetaData(String keyspaceName, String tableName) {
        String query = String.format(
                "SELECT keyspace_name,table_name,column_name,kind,type FROM system_schema.columns WHERE keyspace_name = '%s' AND table_name = '%s'",
                keyspaceName, tableName);
        ResultSet resultSet = session.execute(query);
        for (Row row : resultSet) {
            System.out.println(row.getString("keyspace_name") + "," + row.getString("table_name") + ","
                    + row.getString("column_name") + "," + row.getString("kind") + "," + row.getString("type") );
        }
        session.close();
        throw new UnsupportedOperationException("Unimplemented method 'showTableMetaData'");
    }

    @Override
    public ArrayList<String> getTablesList() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getTablesList'");
    }

    @Override
    public void executeQuery(String query) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'executeQuery'");
    }

    @Override
    public void listAllTables() {
        ResultSet resultSet = session.execute("SELECT keyspace_name,table_name FROM system_schema.tables");
        for (Row row : resultSet) {
            System.out.println(row.getString("keyspace_name") + "," + row.getString("table_name"));
        }
        session.close();
    }

    @Override
    public void checkHeartbeat() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'checkHeartbeat'");
    }

}
