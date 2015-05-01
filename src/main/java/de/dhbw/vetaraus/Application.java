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
import norsys.netica.NeticaException;
import norsys.netica.Node;

import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

public class Application {

    public static void main(String[] args) throws Exception {
        new Application().run(args);
    }

    /**
     * Returns the most likely tariff name in the given net configuration.
     *
     * @throws NeticaException
     */
    private String getMostLikelyTariffName(Net net) throws NeticaException {
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

    /**
     * Apply a list of cases on a Netica net and write each classified output as semicolon-delimted CSV to a
     * PrintStream.
     *
     * @param net
     *         A trained net on which the cases should be applied.
     * @param cases
     *         A list of cases to classify.
     * @param out
     *         The PrintWriter on which the output will be printed.
     * @throws NeticaException
     * @throws IOException
     */
    private void processCaseList(Net net, List<Case> cases, PrintStream out) throws NeticaException, IOException {
        List<Case> sanitizedCases = SanitizeUtils.sanitizeCases(cases);

        for (int i = 0; i < cases.size(); i++) {
            Case currentCase = sanitizedCases.get(i);

            NeticaUtils.setNodeStatesInNet(net, currentCase);

            String tariffName = getMostLikelyTariffName(net);
            cases.get(i).setTariff(tariffName);

            net.retractFindings();
        }

        CSV.write(cases, ';', out);
    }

    /**
     * Execute the application with the given command line arguments.
     *
     * @param args
     *         Command line arguments - should be taken from the main method.
     * @throws Exception
     */
    private void run(String[] args) throws Exception {
        ApplicationConfiguration config = ApplicationConfiguration.parseCmd(args);
        Net net = NeticaUtils.getNetFromConfig(config);

        List<Case> cases = CSV.parse(config.getFile());

        processCaseList(net, cases, System.out);
        NeticaUtils.writeNet(net, config.getNetOut());
    }

}
