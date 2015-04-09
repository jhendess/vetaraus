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

public class Case {
    private String number;
    private String age;
    private String gender;
    private String married;
    private String children;
    private String degree;
    private String occupation;
    private String income;
    private String tariff;

    public Case() {

    }

    public Case(String number, String age, String gender, String married, String children, String degree, String occupation, String income, String tariff) {
        this.number = number;
        this.age = age;
        this.gender = gender;
        this.married = married;
        this.children = children;
        this.degree = degree;
        this.occupation = occupation;
        this.income = income;
        this.tariff = tariff;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMarried() {
        return married;
    }

    public void setMarried(String married) {
        this.married = married;
    }

    public String getChildren() {
        return children;
    }

    public void setChildren(String children) {
        this.children = children;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
        this.income = income;
    }

    public String getTariff() {
        return tariff;
    }

    public void setTariff(String tariff) {
        this.tariff = tariff;
    }

    @Override
    public String toString() {
        return "Case{" +
                "number='" + number + '\'' +
                ", age='" + age + '\'' +
                ", gender='" + gender + '\'' +
                ", married='" + married + '\'' +
                ", children='" + children + '\'' +
                ", degree='" + degree + '\'' +
                ", occupation='" + occupation + '\'' +
                ", income='" + income + '\'' +
                ", tariff='" + tariff + '\'' +
                '}';
    }
}
