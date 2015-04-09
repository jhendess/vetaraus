package de.dhbw.vetaraus;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import norsys.netica.*;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by niklas on 09.04.15.
 */
public class NetFactory {

    private NetFactory() {
    }

    public static Net fromCases(String path) throws NeticaException, IOException {
        List<Case> cases = CSV.parse(path);

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

        Environ env = new Environ("");
        Net net = new Net(env);

        // write out sanitized csv file
        ByteOutputStream out = new ByteOutputStream();
        CSV.write(cases, new PrintWriter(out));

        Caseset caseset = new Caseset();
        caseset.addCases(new Streamer(out, "Cases", env), 1.0, null);

        NodeList nodeList = new NodeList(net);
        Node altersgruppe = new Node(Constants.NODE_AGE, StringUtils.join(altersgruppen, ','), net);
        Node geschlecht = new Node(Constants.NODE_GENDER, "m,w", net);
        Node verheiratet = new Node(Constants.NODE_MARRIED, "ja,nein", net);
        Node kinderzahl = new Node(Constants.NODE_CHILDREN, "_0,_1,_2,_3,_4", net);
        Node abschluss = new Node(Constants.NODE_DEGREE, StringUtils.join(degrees, ','), net);
        Node beruf = new Node(Constants.NODE_OCCUPATION, StringUtils.join(jobs, ','), net);
        Node familieneinkommen = new Node(Constants.NODE_INCOME, StringUtils.join(incomes, ','), net);
        Node versicherung = new Node(Constants.NODE_INSURANCE, StringUtils.join(tariffs, ','), net);

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

        return net;
    }

    public static Net fromExisting(String path) throws NeticaException {
        // unused, but needed by Net constructor, otherwise Environ.getDefaultEnviron() returns NULL and a
        // NullPointerException is thrown
        Environ env = new Environ("");
        Net net = new Net(new Streamer(path));
        return net;
    }
}
