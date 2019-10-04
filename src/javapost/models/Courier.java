package javapost.models;

import java.util.Random;
import javapost.UserType;
import org.bson.Document;

public class Courier extends User {

    private String regNumber;
    

    public Courier() {
        super();
        this.setType(UserType.COURIER);
    }

    public Courier(String firstName, String lastName, String userName, String password, String regNumber) {
        super(firstName, lastName, userName, password);
        this.regNumber = regNumber;
        this.setType(UserType.COURIER);
    }

    public Courier(Document jsonObj) {
        super(jsonObj);

        this.regNumber = jsonObj.get("regNumber", String.class);
        this.setType(UserType.COURIER);

    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-5s", UserType.COURIER));
        sb.append(String.format("%-20s", this.getUserName()));
        sb.append(String.format("%-20s", this.getFirstName()));
        sb.append(String.format("%-20s", this.getLastName()));
        sb.append(String.format("%-20s", "-"));
        sb.append(String.format("%-10s", this.getRegNumber()));

        return sb.toString();
    }

    public String getRegNumber() {
        return regNumber;
    }

    public void setRegNumber(String regNumber) {
        this.regNumber = regNumber;
    }

    public Integer getKilometers() {
        Random random = new Random();
        return random.nextInt(901) + 100;
    }
}
