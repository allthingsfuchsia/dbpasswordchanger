package com.allthingsfuchsia;

import java.net.InetSocketAddress;
import java.util.ArrayList;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.CqlSessionBuilder;
import com.datastax.oss.driver.api.core.cql.ResultSet;


public class CassandraDBUtil implements DBUtilInterface {

    private CqlSession session;
    private DBInfo dbInfo;

    public  CassandraDBUtil(DBInfo dbInfo) {
        this.dbInfo = dbInfo;
        CqlSessionBuilder builder = CqlSession.builder();
        builder.addContactPoint(new InetSocketAddress(dbInfo.getHostName(), dbInfo.getPort()));
        builder.withKeyspace(dbInfo.getServiceName());
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
    public void showTableMetaData(String Schema, String tableName) {
        // TODO Auto-generated method stub
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listAllTables'");
    }

    @Override
    public void checkHeartbeat() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'checkHeartbeat'");
    }
    
}
