package de.dhbw.vetaraus;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

/**
 * Application configuration bean - fields are set through Args4j.
 */
public class ApplicationConfiguration {

    private static final ApplicationConfiguration instance = new ApplicationConfiguration();

    @Argument(metaVar = "[file]", usage = "input", index = 0)
    private String file = "";

    @Option(name = "--net")
    private String net = "";

    @Option(name = "--netOut")
    private String netOut = "";

    @Option(name = "--learn")
    private String learn = "";

    private ApplicationConfiguration() {

    }

    /**
     * Returns the singleton instance of the ApplicationConfiguration.
     *
     * @return the singleton instance of the ApplicationConfiguration.
     */
    public static ApplicationConfiguration getInstance() {
        return instance;
    }

    /**
     * Take a String array with Java argument parameters and return the according ApplicationConfiguration. This will
     * write to the singleton instance of the ApplicationConfiguration object.
     *
     * @param args
     *         The argument array - preferably direct from the main-method.
     * @return A The
     * @throws CmdLineException
     */
    public static ApplicationConfiguration parseCmd(String[] args) throws CmdLineException {
        ApplicationConfiguration config = getInstance();
        CmdLineParser parser = new CmdLineParser(config);
        parser.parseArgument(args);
        return config;
    }

    /**
     * @return The name of the input file that should be classified.
     */
    public String getFile() {
        return file;
    }

    /**
     * @return The name of the input file from which a new net should be learned.
     */
    public String getLearn() {
        return learn;
    }

    /**
     * @return The name of the input Netica net that should be used for classification.
     */
    public String getNet() {
        return net;
    }

    /**
     * @return The name of the output file to which the Netica net should be written.
     */
    public String getNetOut() {
        return netOut;
    }
}
