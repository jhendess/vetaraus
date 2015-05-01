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

/**
 * Static helper methods for accessing and manipulating Netica components.
 */
public class NeticaUtils {

    private static Environ environment = null;

    /**
     * Write a given Netica net to a output file.
     *
     * @param net
     *         The Netica net.
     * @param filename
     *         The output file.
     * @throws NeticaException
     */
    static void writeNet(Net net, String filename) throws NeticaException {
        if (StringUtils.isNotEmpty(filename)) {
            net.write(new Streamer(filename));
            System.out.println("Wrote netica net to file " + filename);
        }
    }

    /**
     * Set the state of a single node to a given string value.
     *
     * @param node
     *         The given node.
     * @param value
     *         The new node state.
     * @throws NeticaException
     */
    public static void setNodeState(Node node, String value) throws NeticaException {
        if (StringUtils.isNotEmpty(value)) {
            node.finding().enterState(value);
        }
    }

    /**
     * Set all node states in a given Netica net according to the given Case object. This will not set the tariff
     * state.
     *
     * @param net
     *         The Netica net where the states should be set.
     * @param c
     *         The input case.
     * @throws NeticaException
     */
    public static void setNodeStatesInNet(Net net, Case c) throws NeticaException {
        setNodeState(net.getNode(Constants.NODE_AGE), c.getAge());
        setNodeState(net.getNode(Constants.NODE_GENDER), c.getGender());
        setNodeState(net.getNode(Constants.NODE_MARRIED), c.getMarried());
        setNodeState(net.getNode(Constants.NODE_CHILDCOUNT), c.getChildCount());
        setNodeState(net.getNode(Constants.NODE_DEGREE), c.getDegree());
        setNodeState(net.getNode(Constants.NODE_OCCUPATION), c.getOccupation());
        setNodeState(net.getNode(Constants.NODE_INCOME), c.getIncome());
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

    /**
     * Create a new Netica net according to the given ApplicationConfiguration.
     *
     * @param config
     *         The configuration bean.
     * @return A new Netica net - never null.
     * @throws Exception
     */
    public static Net getNetFromConfig(ApplicationConfiguration config) throws Exception {
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
}
