package com.allthingsfuchsia;

public class DBPasswordChanger {
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );

        ArgumentParser aParser = new ArgumentParser();

        try {
            DBInfo dbInfo = aParser.parseArguments(args);

            if (dbInfo.getDBType()==DBType.MONGODB){
                MongoDBUtil mg = new MongoDBUtil(dbInfo);
            }else{

            DBUtil dbutil = new DBUtil(dbInfo);

            dbutil.listAllTables();
        }
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
