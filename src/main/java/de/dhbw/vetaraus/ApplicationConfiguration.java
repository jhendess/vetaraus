package de.dhbw.vetaraus;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.Option;

/**
 * Application configuration bean - fields are set through Args4j.
 */
public class ApplicationConfiguration {

    private static final ApplicationConfiguration instance = new ApplicationConfiguration();

    @Argument(metaVar = "[file]", usage = "input", index = 0)
    private String file = "";

    @Option(name="--net")
    private String net = "";

    @Option(name = "--learn")
    private String learn = "";

    private ApplicationConfiguration() {

    }

    public static ApplicationConfiguration getInstance() {
        return instance;
    }

    public String getFile() {
        return file;
    }

    public String getLearn() {
        return learn;
    }

    public String getNet() {
        return net;
    }
}
