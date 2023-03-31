package com.allthingsfuchsia;

import java.io.IOException;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.MissingOptionException;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.lang3.math.NumberUtils;

public class ArgumentParser {
        private Options options;
        private AppActions action;
        private boolean isDebug = false; 

        public ArgumentParser() {
                this.options = new Options()
                                .addOption("v", "verbose", false, "Verbose")
                                .addOption(Option.builder("t")
                                                .longOpt("type")
                                                .hasArg(true)
                                                .desc("Database Types " + DBType.getValues())
                                                .argName("DBType")
                                                .required(true)
                                                .build())
                                .addOption(Option.builder("p")
                                                .longOpt("port")
                                                .hasArg(true)
                                                .desc("Database Port")
                                                .argName("Port")
                                                .required(false)
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
                                                .build())
                                .addOption(Option.builder("u")
                                                .longOpt("url")
                                                .hasArg(true)
                                                .desc("Jdbc Url")
                                                .argName("Url")
                                                .required(false)
                                                .build())
                                .addOption(Option.builder("n")
                                                .longOpt("newpassword")
                                                .hasArg(true)
                                                .desc("New Password")
                                                .argName("newPassword")
                                                .required(false)
                                                .build())
                                .addOption(Option.builder("d")
                                                .longOpt("database")
                                                .hasArg(true)
                                                .desc("Database Instance or Service Name or Database name")
                                                .argName("database")
                                                .required(false)
                                                .build())
                                .addOption(Option.builder("a")
                                                .longOpt("action")
                                                .hasArg(true)
                                                .desc("Possible Actions" + AppActions.getValues())
                                                .argName("action")
                                                .required(true)
                                                .build());

        }

        public DBInfo parseArguments(String[] args) throws MissingOptionException, ParseException, IOException {
                DefaultParser parser = new DefaultParser();
                DBInfo dbinfo;

                CommandLine cmdLine = parser.parse(this.options, args);
                if (cmdLine.hasOption('v')) {
                        this.isDebug = true;
                        System.err.println("Running in verbose mode");
                }

                DBClassInfo dbClassInfo;
                dbClassInfo = DBClassInfo.getDBClassInfo(cmdLine.getOptionValue('t').toUpperCase());
                this.action = AppActions.valueOf(cmdLine.getOptionValue('a'));
                dbinfo = new DBInfo(
                                dbClassInfo,
                                cmdLine.getOptionValue('H'),
                                NumberUtils.toInt(cmdLine.getOptionValue('p'),dbClassInfo.defaultPort),
                                cmdLine.getOptionValue('U'),
                                cmdLine.getOptionValue('P'),
                                cmdLine.getOptionValue('d'),
                                cmdLine.getOptionValue('u'),
                                cmdLine.getOptionValue('n'));

                return dbinfo;
        }


        public AppActions getAction(){
                return this.action;
        }

        public Options getOptions(){
                return this.options;          
        }

}
