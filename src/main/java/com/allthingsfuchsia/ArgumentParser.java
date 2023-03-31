package com.allthingsfuchsia;

import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class ArgumentParser {
    private Options options;

    public ArgumentParser() {
        this.options = new Options()
                .addOption("v", "verbose", false, "Verbose")
                .addOption(Option.builder("t")
                        .longOpt("type")
                        .hasArg(true)
                        .desc("Database Type")
                        .argName("DBType")
                        .required(true)
                        .build())
                .addOption(Option.builder("p")
                        .longOpt("port")
                        .hasArg(true)
                        .desc("Database Port")
                        .argName("Port")
                        .required(true)
                        .build())
                .addOption(Option.builder("H")
                        .longOpt("hostname")
                        .hasArg(true)
                        .desc("Database Hostname")
                        .argName("Hostname")
                        .required(true)
                        .build())
                .addOption(Option.builder("U")
                        .longOpt("user")
                        .hasArg(true)
                        .desc("Database User")
                        .argName("User")
                        .required(true)
                        .build())
                .addOption(Option.builder("P")
                        .longOpt("password")
                        .hasArg(true)
                        .desc("Database Password")
                        .argName("Password")
                        .required(true)
                        .build());

    }

    public DBInfo parseArguments( String[] args) {
        var parser = new DefaultParser();
        DBInfo dbinfo = new DBInfo();

        try {
            var cmdLine = parser.parse(this.options, args);
            if (cmdLine.hasOption('v')) {
                System.err.println("Running in verbose mode");
            }
 
            DBClassInfo dbClassInfo = DBClassInfo.getDBClassInfo(cmdLine.getOptionValue('t'));   
            dbinfo.dbClassInfo = dbClassInfo;
            dbinfo.hostName = cmdLine.getOptionValue('H');
            dbinfo.port = Integer.parseInt(cmdLine.getOptionValue('p'));
            dbinfo.userName = cmdLine.getOptionValue('U');
            dbinfo.password = cmdLine.getOptionValue('P');
            System.out.println(dbinfo.dbClassInfo.dbType);

        } catch (ParseException e) {
            e.printStackTrace();
            new HelpFormatter().printHelp("apache args...", options);
        }

        return dbinfo;

    }

}
