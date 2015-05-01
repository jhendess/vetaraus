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

    /**
     * Create a list of Case objects from a given filepath. The filepath must be a CSV file with semicolon-delimited
     * entries. The first line of the file (header record) will be ignored.
     * <p>
     * Each line of the file must have the following values:
     * ID, age, gender, married, children, degree, occupation, income, tariff.
     *
     * @param path
     *         Path to the CSV file.
     * @return a list of Case objects from the given input file.
     * @throws IOException
     */
    public static List<Case> parse(String path) throws IOException {
        try (FileInputStream fis = new FileInputStream(path);
             InputStreamReader isr = new InputStreamReader(fis);
             BufferedReader file = new BufferedReader(isr)) {

            final CSVParser parser = new CSVParser(file, CSVFormat.DEFAULT.withDelimiter(';').withHeader(
                    Constants.HEADER_NUMBER, Constants.HEADER_AGE, Constants.HEADER_GENDER, Constants.HEADER_MARRIED, Constants.HEADER_CHILDREN, Constants.HEADER_DEGREE,
                    Constants.HEADER_OCCUPATION, Constants.HEADER_INCOME, Constants.HEADER_TARIFF
            ).withSkipHeaderRecord(true));

            return parser.getRecords().stream().map(record -> new Case(
                    record.get(Constants.HEADER_NUMBER),
                    record.get(Constants.HEADER_AGE),
                    record.get(Constants.HEADER_GENDER),
                    record.get(Constants.HEADER_MARRIED),
                    record.get(Constants.HEADER_CHILDREN),
                    record.get(Constants.HEADER_DEGREE),
                    record.get(Constants.HEADER_OCCUPATION),
                    record.get(Constants.HEADER_INCOME),
                    record.get(Constants.HEADER_TARIFF)
            )).collect(Collectors.toList());
        }
    }

    /**
     * Write a given list of Case objects with a given CSV-delimiter to any Appendable output (i.e. printer).
     * <p>
     * The output file will include a header record and print the following values:
     * ID, age, gender, married, children, degree, occupation, income, tariff.
     *
     * @param cases
     *         The list of Case objects to write
     * @param delimiter
     *         The delimiter between each CSV column.
     * @param out
     *         The Appendable output to which the data should be written.
     * @throws IOException
     */
    public static void write(List<Case> cases, Character delimiter, Appendable out) throws IOException {
        try (CSVPrinter writer = new CSVPrinter(out, CSVFormat.DEFAULT.withDelimiter(delimiter))) {
            // print headers
            writer.printRecord(Constants.HEADER_NUMBER, Constants.HEADER_AGE, Constants.HEADER_GENDER, Constants.HEADER_MARRIED,
                    Constants.HEADER_CHILDREN, Constants.HEADER_DEGREE, Constants.HEADER_OCCUPATION, Constants.HEADER_INCOME, Constants.HEADER_TARIFF);
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
