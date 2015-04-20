/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 jhendess, nwolber
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package de.dhbw.vetaraus;

import norsys.netica.*;
import org.apache.commons.lang3.StringUtils;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

import java.util.List;

public class Application {

    private static Environ environment = null;

    public static void main(String[] args) throws Exception {
        ApplicationConfiguration config = parseCmd(args);

        Net net = getNetFromConfig(config);

        List<Case> cases = CSV.parse(config.getFile());
        List<Case> sanitizedCases = SanitizeUtils.sanitizeCases(cases);

        for (int i = 0; i < cases.size(); i++) {
            Case currentCase = sanitizedCases.get(i);

            setNodeStatesInNet(net, currentCase);

            String tariffName = getMostLikelyTariffName(net);
            cases.get(i).setTariff(tariffName);

            net.retractFindings();
        }

        CSV.write(cases, ';', System.out);

        writeNet(net);
    }

    private static void writeNet(Net net) throws NeticaException {
        String netOut = ApplicationConfiguration.getInstance().getNetOut();
        if (StringUtils.isNotEmpty(netOut)) {
            net.write(new Streamer(netOut));
        }
    }

    static void setNodeState(Node node, String value) throws NeticaException {
        if (StringUtils.isNotEmpty(value)) {
            node.finding().enterState(value);
        }
    }

    private static String getMostLikelyTariffName(Net net) throws NeticaException {
        Node insurance = net.getNode(Constants.NODE_INSURANCE);
        float[] bs = insurance.getBeliefs();
        float highest = 0.0f;
        int highestIndex = 0;

        for (int j = 0; j < bs.length; j++) {
            if (bs[j] > highest) {
                highest = bs[j];
                highestIndex = j;
            }
        }

        // set tariff on unsanitized case!
        return insurance.state(highestIndex).getName();
    }

    private static void setNodeStatesInNet(Net net, Case c) throws NeticaException {
        setNodeState(net.getNode(Constants.NODE_AGE), c.getAge());
        setNodeState(net.getNode(Constants.NODE_GENDER), c.getGender());
        setNodeState(net.getNode(Constants.NODE_MARRIED), c.getMarried());
        setNodeState(net.getNode(Constants.NODE_CHILDCOUNT), c.getChildCount());
        setNodeState(net.getNode(Constants.NODE_DEGREE), c.getDegree());
        setNodeState(net.getNode(Constants.NODE_OCCUPATION), c.getOccupation());
        setNodeState(net.getNode(Constants.NODE_INCOME), c.getIncome());
    }

    private static Net getNetFromConfig(ApplicationConfiguration config) throws Exception {
        Net net;
        if (!StringUtils.isEmpty(config.getLearn())) {
            net = NetFactory.fromCases(config.getLearn());
        } else if (!StringUtils.isEmpty(config.getNet())) {
            net = NetFactory.fromExisting(config.getNet());
        } else {
            throw new Exception("--net oder --learn muss angegeben werden.");
        }
        return net;
    }

    private static ApplicationConfiguration parseCmd(String[] args) throws CmdLineException {
        ApplicationConfiguration config = ApplicationConfiguration.getInstance();
        CmdLineParser parser = new CmdLineParser(config);
        parser.parseArgument(args);
        return config;
    }

    /**
     * Returns the global environment for Netica.
     *
     * @return the global environment for Netica.
     */
    public static Environ getEnvironment() {
        if (environment == null) {
            try {
                environment = new Environ("");
            } catch (NeticaException e) {
                // Don't ever try this at home:
                throw new RuntimeException(e);
            }
        }
        return environment;
    }
}
