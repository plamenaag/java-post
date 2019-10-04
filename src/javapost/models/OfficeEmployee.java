package javapost.models;

import javapost.UserType;
import javapost.models.User;
import org.bson.Document;

public class OfficeEmployee extends User {

    private String email;
    
    public OfficeEmployee(){
        super();
        this.setType(UserType.OFFICE_EMPLOYEE);
    }
    
     public OfficeEmployee(String firstName, String lastName, String userName, String password, String email){
         super(firstName, lastName, userName, password);
         this.email = email;
         this.setType(UserType.OFFICE_EMPLOYEE);
     }

    public OfficeEmployee(Document jsonObj) {
        super(jsonObj);

        this.email = jsonObj.get("email", String.class);
        this.setType(UserType.OFFICE_EMPLOYEE);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-5s", UserType.OFFICE_EMPLOYEE));
        sb.append(String.format("%-20s", this.getUserName()));
        sb.append(String.format("%-20s", this.getFirstName()));
        sb.append(String.format("%-20s", this.getLastName()));
        sb.append(String.format("%-20s", this.getEmail()));
        sb.append(String.format("%-10s", "-"));

        return sb.toString();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
