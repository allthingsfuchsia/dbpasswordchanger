package com.allthingsfuchsia;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.ParseException;

public class DBPasswordChanger {
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );

        ArgumentParser aParser = new ArgumentParser();

        try {
            DBInfo dbInfo = aParser.parseArguments(args);

            if (dbInfo.getDBType()==DBType.MONGODB){
                MongoDBUtil mg = new MongoDBUtil(dbInfo);
            }
            else{

            DBUtil dbutil = new DBUtil(dbInfo);
            
            switch (aParser.getAction()) {
                case changePassword:
                    if (dbInfo.newPassword == null)
                    {
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
                    dbutil.showTableMetaData(null, null);
                    break;
                default:
                    break;
            }
        }
            
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            new HelpFormatter().printHelp("apache args...", aParser.getOptions());
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
