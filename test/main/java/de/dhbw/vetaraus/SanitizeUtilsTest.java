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

import java.util.Hashtable;
import java.util.Map;

import static org.junit.Assert.*;

public class SanitizeUtilsTest {

    @org.junit.Test
    public void testSanitizeCases() throws Exception {
        Hashtable<String, String> tests = new Hashtable<String, String>() {{
            put("","");
            put("1","_1");
            put("36-49","_36_49");
            put("3000-3999","_3000_3999");
            put(" ","_");
            put("-","_");
            put("ä","ae");
            put("ö","oe");
            put("ü","ue");
            put("ß","ss");
            put("Ä","AE");
            put("Ö","OE");
            put("Ü","UE");
            put("<","LESS");
            put(">","GREATER");
            put("<18","LESS18");
            put("<1000","LESS1000");
            put("5000 und mehr","_5000_und_mehr");
        }};

        for(Map.Entry<String, String> kv : tests.entrySet()) {
            String got = SanitizeUtils.sanitizeRecordValue(kv.getKey());
            assertEquals(got, kv.getValue());
        }
    }
}