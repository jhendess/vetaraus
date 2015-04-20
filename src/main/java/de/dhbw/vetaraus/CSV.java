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

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

public class CSV {

    private static final String HEADER_NUMBER = "Nr";

    private static final String HEADER_AGE = "Altersgruppe";

    private static final String HEADER_GENDER = "Geschlecht";

    private static final String HEADER_MARRIED = "Verheiratet";

    private static final String HEADER_CHILDREN = "Kinderzahl";

    private static final String HEADER_DEGREE = "Abschluss";

    private static final String HEADER_OCCUPATION = "Beruf";

    private static final String HEADER_INCOME = "Familieneinkommen";

    private static final String HEADER_TARIFF = "Versicherung";

    public static List<Case> parse(String path) throws IOException {
        try (FileInputStream fis = new FileInputStream(path);
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader file = new BufferedReader(isr)) {

            final CSVParser parser = new CSVParser(file, CSVFormat.DEFAULT.withDelimiter(';').withHeader(
                    HEADER_NUMBER, HEADER_AGE, HEADER_GENDER, HEADER_MARRIED, HEADER_CHILDREN, HEADER_DEGREE,
                    HEADER_OCCUPATION, HEADER_INCOME, HEADER_TARIFF
            ).withSkipHeaderRecord(true));

            return parser.getRecords().stream().map(record -> new Case(
                    record.get(HEADER_NUMBER),
                    record.get(HEADER_AGE),
                    record.get(HEADER_GENDER),
                    record.get(HEADER_MARRIED),
                    record.get(HEADER_CHILDREN),
                    record.get(HEADER_DEGREE),
                    record.get(HEADER_OCCUPATION),
                    record.get(HEADER_INCOME),
                    record.get(HEADER_TARIFF)
            )).collect(Collectors.toList());
        }
    }

    public static void write(List<Case> cases, Appendable out) throws IOException {
        try (CSVPrinter writer = new CSVPrinter(out, CSVFormat.DEFAULT.withDelimiter(';'))) {
            // print headers
            writer.printRecord(HEADER_NUMBER, HEADER_AGE, HEADER_GENDER, HEADER_MARRIED,
                    HEADER_CHILDREN, HEADER_DEGREE, HEADER_OCCUPATION, HEADER_INCOME, HEADER_TARIFF);
            for (Case c : cases) {
                writer.printRecord(c.getNumber(),
                        c.getAge(),
                        c.getGender(),
                        c.getMarried(),
                        c.getChildCount(),
                        c.getDegree(),
                        c.getOccupation(),
                        c.getIncome(),
                        c.getTariff()
                );
            }
            writer.flush();
        }
    }
}
