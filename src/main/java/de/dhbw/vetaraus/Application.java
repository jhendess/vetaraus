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

import norsys.netica.Net;
import norsys.netica.Node;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

import java.util.List;

public class Application {

    public static void main(String[] args) throws Exception {
        ApplicationConfiguration config = parseCmd(args);

        Net net;

        if (!StringUtils.isEmpty(config.getLearn())) {
            net = NetFactory.fromCases(config.getLearn());
        } else if (!StringUtils.isEmpty(config.getNet())) {
            net = NetFactory.fromExisting(config.getNet());
        } else {
            throw new Exception("--net oder --learn muss angegeben werden.");
        }

        List<Case> cases = CSV.parse(config.getFile());

        for (Case c : cases) {
            if (!StringUtils.isEmpty(c.getAge())) {
                net.getNode(Constants.NODE_AGE).finding().enterState(c.getAge());
            }

            if (!StringUtils.isEmpty(c.getGender())) {
                net.getNode(Constants.NODE_GENDER).finding().enterState(c.getGender());
            }

            if (!StringUtils.isEmpty(c.getMarried())) {
                net.getNode(Constants.NODE_MARRIED).finding().enterState(c.getMarried());
            }

            if (!StringUtils.isEmpty(c.getChildren())) {
                net.getNode(Constants.NODE_CHILDREN).finding().enterState(c.getChildren());
            }

            if (!StringUtils.isEmpty(c.getDegree())) {
                net.getNode(Constants.NODE_DEGREE).finding().enterState(c.getDegree());
            }

            if (!StringUtils.isEmpty(c.getOccupation())) {
                net.getNode(Constants.NODE_OCCUPATION).finding().enterState(c.getOccupation());
            }

            if (!StringUtils.isEmpty(c.getIncome())) {
                net.getNode(Constants.NODE_INCOME).finding().enterState(c.getIncome());
            }

            Node insurance = net.getNode(Constants.NODE_INSURANCE);
            float[] bs = insurance.getBeliefs();
            float highest = 0.0f;
            int highestIndex = 0;

            for (int i = 0; i < bs.length; i++) {
                if (bs[i]> highest) {
                    highest = bs[i];
                    highestIndex = i;
                }
            }
            
            System.out.println(insurance.state(highestIndex));

            net.retractFindings();
        }
    }

    private static ApplicationConfiguration parseCmd(String[] args) throws CmdLineException {
        ApplicationConfiguration config = ApplicationConfiguration.getInstance();
        CmdLineParser parser = new CmdLineParser(config);
        parser.parseArgument(args);
        return config;
    }
}
