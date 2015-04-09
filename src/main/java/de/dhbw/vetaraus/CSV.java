package de.dhbw.vetaraus;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

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

    private static final String[] REPLACE_SEARCH_LIST = new String[]{" ", "-", "ä", "ö", "ü", "Ä", "Ö", "Ü", "ß", "<", ">"};

    private static final String[] REPLACE_REPLACEMENT_LIST = new String[]{"_", "_", "ae", "oe", "ue", "AE", "OE", "UE", "ß", "LESS", "GREATER"};

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
                    sanitizeRecordValue(record.get(HEADER_AGE)),
                    sanitizeRecordValue(record.get(HEADER_GENDER)),
                    sanitizeRecordValue(record.get(HEADER_MARRIED)),
                    sanitizeRecordValue(record.get(HEADER_CHILDREN)),
                    sanitizeRecordValue(record.get(HEADER_DEGREE)),
                    sanitizeRecordValue(record.get(HEADER_OCCUPATION)),
                    sanitizeRecordValue(record.get(HEADER_INCOME)),
                    record.get(HEADER_TARIFF)
            )).collect(Collectors.toList());
        }
    }

    private static String sanitizeRecordValue(String input) {
        String output = input;

        // Append underscore before leading digit
        if (StringUtils.startsWithAny(output, "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"))
            output = "_" + output;

        return StringUtils.replaceEach(output, REPLACE_SEARCH_LIST, REPLACE_REPLACEMENT_LIST);
    }

    public static void write(List<Case> cases, Appendable out) throws IOException {
        CSVPrinter writer = new CSVPrinter(out, CSVFormat.DEFAULT);
        // print headers
        writer.print(new Case(HEADER_NUMBER, HEADER_AGE, HEADER_GENDER, HEADER_MARRIED,
                HEADER_CHILDREN, HEADER_DEGREE, HEADER_OCCUPATION, HEADER_INCOME, HEADER_TARIFF));
        writer.printRecords(cases);
    }
}
