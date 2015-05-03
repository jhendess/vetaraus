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

import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Static helper methods for sanitizing record values. This is necessary because of Netica's internal string
 * handling.
 */
public class SanitizeUtils {

    private static final String[] REPLACE_SEARCH_LIST = new String[]{" ", "-", "ä", "ö", "ü", "Ä", "Ö", "Ü", "ß", "<", ">"};

    private static final String[] REPLACE_REPLACEMENT_LIST = new String[]{"_", "_", "ae", "oe", "ue", "AE", "OE", "UE", "ss", "LESS", "GREATER"};

    public static List<Case> sanitizeCases(List<Case> cases) {
        return cases.stream().map(c -> new Case(
                c.getNumber(),
                sanitizeRecordValue(c.getAge()),
                sanitizeRecordValue(c.getGender()),
                sanitizeRecordValue(c.getMarried()),
                sanitizeRecordValue(c.getChildCount()),
                sanitizeRecordValue(c.getDegree()),
                sanitizeRecordValue(c.getOccupation()),
                sanitizeRecordValue(c.getIncome()),
                c.getTariff()
        )).collect(Collectors.toList());
    }

    static String sanitizeRecordValue(String input) {
        String output = input;

        // Append underscore before leading digit
        if (StringUtils.startsWithAny(output, "0", "1", "2", "3", "4", "5", "6", "7", "8", "9"))
            output = "_" + output;

        return StringUtils.replaceEach(output, REPLACE_SEARCH_LIST, REPLACE_REPLACEMENT_LIST);
    }

}
