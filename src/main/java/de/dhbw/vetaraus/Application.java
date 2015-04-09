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

import java.io.IOException;
import java.util.*;

public class Application {

    public static void main(String[] args) throws NeticaException, CmdLineException, IOException {
        ApplicationConfiguration config = parseCmd(args);

        List<Case> cases = CSV.parse(config.getFile());

        Set<String> altersgruppen = new TreeSet<>();
        Set<String> degrees = new TreeSet<>();
        Set<String> jobs = new TreeSet<>();
        Set<String> incomes = new TreeSet<>();
        Set<String> tariffs = new TreeSet<>();


        // Find all states
        for (Case c : cases) {
            altersgruppen.add(c.getAge());
            degrees.add(c.getDegree());
            jobs.add(c.getOccupation());
            incomes.add(c.getIncome());
            tariffs.add(c.getTariff());
        }

        //System.out.println(cases);

//        Environ env = new Environ("");
//        Net net = new Net(new Streamer(""));


        Environ env = new Environ("");
        Net net = new Net(env);

        Caseset caseset = new Caseset();
        caseset.addCases(new Streamer(config.getFile()), 1.0, null);

        NodeList nodeList = new NodeList(net);
        Node altersgruppe = new Node("Altersgruppe", StringUtils.join(altersgruppen, ','), net);
        Node geschlecht = new Node("Geschlecht", "m,w", net);
        Node verheiratet = new Node("Verheiratet", "Ja,Nein", net);
        Node kinderzahl = new Node("Kinderzahl", "_0,_1,_2,_3,_4", net);
        Node abschluss = new Node("Abschluss", StringUtils.join(degrees, ','), net);
        Node beruf = new Node("Beruf", StringUtils.join(jobs, ','), net);
        Node familieneinkommen = new Node("Familieneinkommen", StringUtils.join(incomes, ','), net);
        Node versicherung = new Node("Versicherung", StringUtils.join(tariffs, ','), net);

        versicherung.addLink(altersgruppe);
        versicherung.addLink(geschlecht);
        versicherung.addLink(verheiratet);
        versicherung.addLink(kinderzahl);
        versicherung.addLink(abschluss);
        versicherung.addLink(beruf);
        versicherung.addLink(familieneinkommen);

        nodeList.add(altersgruppe);
        nodeList.add(geschlecht);
        nodeList.add(verheiratet);
        nodeList.add(kinderzahl);
        nodeList.add(abschluss);
        nodeList.add(beruf);
        nodeList.add(familieneinkommen);
        nodeList.add(versicherung);


        Learner learner = new Learner(Learner.EM_LEARNING);
        learner.learnCPTs(nodeList, caseset, 1.0);

        for (Node n : (Vector<Node>) nodeList) {
            System.out.println(Arrays.toString(n.getBeliefs()));
        }

        System.out.println(nodeList);


    }

    private static ApplicationConfiguration parseCmd(String[] args) throws CmdLineException {
        ApplicationConfiguration config = ApplicationConfiguration.getInstance();
        CmdLineParser parser = new CmdLineParser(config);
        parser.parseArgument(args);
        return config;
    }
}
