package com.allthingsfuchsia;

import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.ParseException;

public class DBPasswordChanger {
    public static void main(String[] args) {
        System.out.println("Hello DB PasswordChanger");

        ArgumentParser aParser = new ArgumentParser();

        try {
            DBInfo dbInfo = aParser.parseArguments(args);
            DBUtilInterface dbutil;

            if (dbInfo.getDBType() == DBType.MONGODB) {
                dbutil = new MongoDBUtil(dbInfo);
            } else if (dbInfo.getDBType() == DBType.CASSANDRA) {
                dbutil = new CassandraDBUtil(dbInfo);
            } else {
                // System.out.println(dbInfo.getURL());
                dbutil = new DBUtil(dbInfo);
            }

            switch (aParser.getAction()) {
                case checkHeartbeat:
                    dbutil.checkHeartbeat();
                case changePassword:
                    if (dbInfo.getNewPassword() == null) {
                        System.exit(-1);
                    }
                    dbutil.changePassword();
                    break;
                case listAllTables:
                    dbutil.listAllTables();
                    break;
                case executeQuery:
                    dbutil.executeQuery(null);
                    break;
                case showTableMetaData:
                    dbutil.showTableMetaData("system_auth", "roles");
                    break;
                default:
                    break;

            }

        } catch (ParseException e) {
            System.out.println(e.getMessage());
            new HelpFormatter().printHelp("apache args...", aParser.getOptions());
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }
}
