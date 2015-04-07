package de.dhbw.vetaraus;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineParser;

/**
 * Created by niklas on 07.04.15.
 */
public class ApplicationConfiguration {
    private static final ApplicationConfiguration instance = new ApplicationConfiguration();
    public static synchronized ApplicationConfiguration getInstance() {
        return instance;
    }

    @Argument(metaVar = "[file]", usage = "the first file that should be interpreted")
    private String file;

    private ApplicationConfiguration() {

    }

    public String getFile() {
        return file;
    }
}
