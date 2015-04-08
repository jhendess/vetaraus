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
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;

import java.io.IOException;
import java.util.List;

public class Application {

    public static void main(String[] args) throws NeticaException, CmdLineException, IOException {
        ApplicationConfiguration config = parseCmd(args);

        //List<Case> cases = CSV.parse(config.getFile());

        //System.out.println(cases);

//        Environ env = new Environ("");
//        Net net = new Net(new Streamer(""));

        // TODO: Add states

        Environ env = new Environ("");
        Net net = new Net(env);

        Caseset caseset = new Caseset();
        caseset.addCases(new Streamer(config.getFile()), 1.0, null);

        NodeList nodeList = new NodeList(net);
        Node altersgruppe = new Node("Altersgruppe", 2, net);
        Node geschlecht = new Node("Geschlecht", 2, net);
        Node verheiratet = new Node("Verheiratet", 2, net);
        Node kinderzahl = new Node("Kinderzahl",2, net);
        Node abschluss = new Node("Abschluss", 2, net);
        Node beruf = new Node("Beruf", 2, net);
        Node familieneinkommen = new Node("Familieneinkommen", 2, net);
        Node versicherung = new Node("Versicherung", 2, net);

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

        System.out.println(nodeList);


    }

    private static ApplicationConfiguration parseCmd(String[] args) throws CmdLineException {
        ApplicationConfiguration config = ApplicationConfiguration.getInstance();
        CmdLineParser parser = new CmdLineParser(config);
        parser.parseArgument(args);
        return config;
    }
}
