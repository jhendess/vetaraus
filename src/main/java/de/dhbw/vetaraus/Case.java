package de.dhbw.vetaraus;

/**
 * Created by niklas on 07.04.15.
 */
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
