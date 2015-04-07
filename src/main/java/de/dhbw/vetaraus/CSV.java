package de.dhbw.vetaraus;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by niklas on 07.04.15.
 */
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
            ));


            List<Case> records = new ArrayList<Case>();
            for (CSVRecord record : parser.getRecords()) {
                records.add(new Case(
                        record.get(HEADER_NUMBER),
                        record.get(HEADER_AGE),
                        record.get(HEADER_GENDER),
                        record.get(HEADER_MARRIED),
                        record.get(HEADER_CHILDREN),
                        record.get(HEADER_DEGREE),
                        record.get(HEADER_OCCUPATION),
                        record.get(HEADER_INCOME),
                        record.get(HEADER_TARIFF)
                ));
            }

            return records;
        }
    }


}
