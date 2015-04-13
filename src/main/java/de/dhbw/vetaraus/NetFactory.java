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

import java.io.*;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class NetFactory {

    private NetFactory() {
    }

    /**
     * Create a new Netica net from an existing CSV file. Cases are learned through gradient algorithm.
     *
     * @param path
     *         Filepath of the CSV file
     * @return A Netica net.
     * @throws NeticaException
     *         Netica problems.
     * @throws IOException
     *         I/O problems.
     */
    public static Net fromCases(String path) throws NeticaException, IOException {
        List<Case> cases = SanitizeUtils.sanitizeCases(CSV.parse(path));

        Set<String> ageGroupSet = new TreeSet<>();
        Set<String> degreeSet = new TreeSet<>();
        Set<String> occupationSet = new TreeSet<>();
        Set<String> incomeSet = new TreeSet<>();
        Set<String> tariffSet = new TreeSet<>();

        // Find all states
        for (Case c : cases) {
            ageGroupSet.add(c.getAge());
            degreeSet.add(c.getDegree());
            occupationSet.add(c.getOccupation());
            incomeSet.add(c.getIncome());
            tariffSet.add(c.getTariff());
        }

        Net net = new Net(Application.getEnvironment());

        Caseset caseset = getCaseset(cases);

        // Create nodes in net:
        NodeList nodeList = new NodeList(net);
        Node ageGroupNode = new Node(Constants.NODE_AGE, StringUtils.join(ageGroupSet, ','), net);
        Node genderNode = new Node(Constants.NODE_GENDER, "m,w", net);
        Node marriedNode = new Node(Constants.NODE_MARRIED, "ja,nein", net);
        Node childCountNode = new Node(Constants.NODE_CHILDCOUNT, "_0,_1,_2,_3,_4", net);
        Node degreeNode = new Node(Constants.NODE_DEGREE, StringUtils.join(degreeSet, ','), net);
        Node occupationNode = new Node(Constants.NODE_OCCUPATION, StringUtils.join(occupationSet, ','), net);
        Node incomeNode = new Node(Constants.NODE_INCOME, StringUtils.join(incomeSet, ','), net);
        Node tariffNode = new Node(Constants.NODE_INSURANCE, StringUtils.join(tariffSet, ','), net);

        // Link nodes:
        tariffNode.addLink(ageGroupNode);
        tariffNode.addLink(genderNode);
        tariffNode.addLink(marriedNode);
        tariffNode.addLink(childCountNode);
        tariffNode.addLink(degreeNode);
        tariffNode.addLink(occupationNode);
        tariffNode.addLink(incomeNode);

        nodeList.add(ageGroupNode);
        nodeList.add(genderNode);
        nodeList.add(marriedNode);
        nodeList.add(childCountNode);
        nodeList.add(degreeNode);
        nodeList.add(occupationNode);
        nodeList.add(incomeNode);
        nodeList.add(tariffNode);

        Learner learner = new Learner(Learner.EM_LEARNING);
        learner.learnCPTs(nodeList, caseset, 1.0);

        return net;
    }

    private static Caseset getCaseset(List<Case> cases) throws IOException, NeticaException {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        CSV.write(cases, new PrintWriter(out));
        InputStream in = new ByteArrayInputStream(out.toByteArray());
        Caseset caseset = new Caseset();
        caseset.addCases(new Streamer(in, "Cases", Application.getEnvironment()), 1.0, null);
        return caseset;
    }

    public static Net fromExisting(String path) throws NeticaException {
        Environ env = Application.getEnvironment();
        return new Net(new Streamer(path));
    }
}
