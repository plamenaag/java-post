package javapost.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import javapost.ScannerFactory;
import javapost.repositories.UserRepository;
import javapost.UserType;
import javapost.models.Courier;
import javapost.models.OfficeEmployee;
import javapost.models.User;
import org.bson.Document;

public class UserUtils {

    private static Scanner sc = ScannerFactory.getInstance();

    public static List<User> deserialize(String fileContent) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        List<Document> rawUsers = mapper.readValue(fileContent,
                TypeFactory.defaultInstance().constructCollectionType(List.class, Document.class));

        List<User> users = new ArrayList<>();
        for (Document item : rawUsers) {
            User user = null;
            if (UserType.COURIER.equals(item.get("type", String.class))) {
                user = new Courier(item);

            } else if (UserType.OFFICE_EMPLOYEE.equals(item.get("type", String.class))) {
                user = new OfficeEmployee(item);

            }
            if (user != null) {
                users.add(user);
            }
        }
        return users;
    }

    public static void printAll(List<User> users) {

        if(users == null || users.size() == 0){
            System.err.println("No users found!");
            return;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%-5s", ""));
        sb.append(String.format("%-5s", "Type"));
        sb.append(String.format("%-20s", "Username"));
        sb.append(String.format("%-20s", "First name"));
        sb.append(String.format("%-20s", "Last name"));
        sb.append(String.format("%-20s", "Email"));
        sb.append(String.format("%-10s", "Registration number"));

        System.out.println(sb.toString());

        Integer index = 1;
        for (User user : users) {
            System.out.println(String.format("%-5s", index++) + user.toString());
        }
    }

    public static List<User> findCourierByLastName(String lastName) {
        List<User> foundUsers = UserRepository.getUsers().stream()
                .filter(user -> user.getLastName().equalsIgnoreCase(lastName) && user instanceof Courier).collect(Collectors.toList());

        return foundUsers;
    }
    
    

    public static List<User> findByUserName(String userName) {
        List<User> foundUsers = UserRepository.getUsers().stream()
                .filter(user -> user.getUserName().equalsIgnoreCase(userName)).collect(Collectors.toList());

        return foundUsers;
    }

    public static User createUser(String userType) {

        System.out.print("Input first name: ");
        String firstName = sc.nextLine();
        System.out.print("Input last name: ");
        String lastName = sc.nextLine();
        System.out.print("Input username: ");
        String userName = sc.nextLine();
        System.out.print("Input password: ");
        String password = sc.nextLine();

        if (userType.equals(UserType.COURIER)) {
            System.out.print("Input registration number: ");
        } else {
            System.out.print("Input email: ");
        }

        String extraInfo = sc.nextLine();

        Boolean userExist = UserRepository.checkUserExist(userName, extraInfo, userType);
        if (userExist) {
            System.out.println("User already exists!");
            return null;
        }

        User user = null;

        if (userType.equals(UserType.COURIER)) {
            user = new Courier(firstName, lastName, userName, password, extraInfo);
        } else {
            user = new OfficeEmployee(firstName, lastName, userName, password, extraInfo);
        }

        return user;
    }

    public static User logInUser(String userType) throws Exception {
        System.out.print("Input username: ");
        String userName = sc.nextLine();

        if (userType.equals(UserType.COURIER)) {
            System.out.print("Input registration number: ");
        } else {
            System.out.print("Input email: ");
        }
        String extraInfo = sc.nextLine();

        System.out.print("Input password: ");
        String password = sc.nextLine();

        List<User> foundUsers = UserRepository.getUsers(userName, extraInfo, password, userType);

        if (foundUsers != null && foundUsers.size() == 0) {
            return null;
        } else if (foundUsers != null && foundUsers.size() == 1) {
            return foundUsers.get(0);
        }

        throw new Exception("Fatal error! Multiple users with the same credentials!");
    }

}
